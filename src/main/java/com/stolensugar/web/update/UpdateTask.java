package com.stolensugar.web.update;

import com.stolensugar.web.Command;
import com.stolensugar.web.CommandGroupMappings;
import com.stolensugar.web.FileProcessingKt;
import com.stolensugar.web.dao.SpokenFormDao;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dao.UserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormModel;
import com.stolensugar.web.dynamodb.models.UserModel;
import com.stolensugar.web.model.SpokenForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.kohsuke.github.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.stolensugar.web.FileProcessingKt.processFormatters;

public class UpdateTask implements Runnable {

    private final GitHub github;
    private final SpokenFormDao spokenFormDao;
    private final SpokenFormUserDao spokenFormUserDao;
    private final UserDao userDao;
    private Map<String, CommandGroupMappings> baseCommands;

    private static final String BASE_USER_ID = "15005956";
    private static final Logger LOG = LogManager.getLogger(UpdateTask.class);

    @Inject
    public UpdateTask(final GitHub github, final SpokenFormDao spokenFormDao, final SpokenFormUserDao spokenFormUserDao, final UserDao userDao) {
        this.github = github;
        this.spokenFormDao = spokenFormDao;
        this.spokenFormUserDao = spokenFormUserDao;
        this.userDao = userDao;
        this.baseCommands = new HashMap<>();
    }


    @Override
    public void run() {

        populateBaseCommands();

        try {
            updateRepos();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }


    public void updateRepos() throws IOException {
        GHRepository baseRepo = github.getRepository("knausj85/knausj_talon");
        Set<String> baseCommitShas = getCommitShas(baseRepo);
        List<GHRepository> forks = baseRepo.listForks().toList();


        for (var fork : forks.subList(1, forks.size())) {
            GHUser forkOwner = fork.getOwner();

            // Retrieve user from our database
            UserModel user;
            try {
                user = userDao.getUser(String.valueOf(forkOwner.getId()));
            } catch (Exception e) {
                LOG.debug(e.getMessage());
                continue;
            }

            // Convert timestamp string into Java Date
            DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis();
            Date lastPush = dateTimeFormatter.parseDateTime(user.getTalonLastPush()).toDate();

            // Update the user's lastPush in the database to the current time
            user.setTalonLastPush(dateTimeFormatter.print(new DateTime(DateTimeZone.UTC)));
            userDao.saveUser(List.of(user));

            // Try to retrieve commits from stolen-sugar branch, or master if that fails
            List<GHCommit> commits;
            String branch;
            try {
                branch = "stolen-sugar";
                commits = getCommits(fork, lastPush, branch);
            } catch (GHFileNotFoundException e) {
                LOG.debug(e.getMessage());
                branch = "master";
                commits = getCommits(fork, lastPush, branch);
            }

            Map<String, Map<String, String>> changedWords = new HashMap<>();

            for (var commit : commits) {
                if (!baseCommitShas.contains(commit.getSHA1())) {
                    for (var commitFile : commit.getFiles()) {
                        String fileName = commitFile.getFileName();
                        if (baseCommands.containsKey(fileName)) {
                            if (fileName.endsWith(".talon")) {
                                GHContent talonFile = fork.getFileContent(fileName, branch);
                                changedWords.put(fileName, getTalonChanges(talonFile));
                            } else if (fileName.endsWith(".py")) {
                                Map<String, String> changesForFile = changedWords.containsKey(fileName) ?
                                        changedWords.get(fileName) : new HashMap<>();
                                changedWords.put(fileName, getPythonChanges(commitFile, changesForFile));
                            }
                        }
                    }
                }
            }
        }
    }

    public Set<String> getCommitShas(GHRepository repo) throws IOException {
        return repo.queryCommits()
                .pageSize(100)
                .list()
                .toSet()
                .stream()
                .map(commit -> commit.getSHA1())
                .collect(Collectors.toSet());
    }

    public List<GHCommit> getCommits(GHRepository repo, Date startingDate, String branch) throws IOException {
        List<GHCommit> commits =  repo.queryCommits()
                .pageSize(100)
                .since(startingDate)
                .from(branch)
                .list()
                .toList();

        Collections.reverse(commits);

        return commits;
    }

    private void populateBaseCommands() {

        List<SpokenFormModel> spokenForms = spokenFormDao.getAllSpokenForms();
        Map<String, List<SpokenFormModel>> spokenFormMap = spokenForms.stream()
                .collect(Collectors.groupingBy(SpokenFormModel::getFileName));

        spokenFormMap.entrySet().stream()
                .forEach(entry -> baseCommands.put(entry.getKey(), new CommandGroupMappings(
                        entry.getValue().stream()
                                .collect(Collectors.toMap(SpokenFormModel::getDefaultName, SpokenFormModel::getAction)),
                        entry.getValue().stream()
                                .collect(Collectors.toMap(SpokenFormModel::getAction, SpokenFormModel::getDefaultName)))));
    }

    private Map<String, String> getTalonChanges(GHContent talon) throws IOException {

        Map<String, String> talonCommands = FileProcessingKt.processTalon(new String(talon.read().readAllBytes()));
        Map<String, String> baseTargets = baseCommands.get(talon.getPath()).getTargetMap();
        Map<String, String> baseInvocations = baseCommands.get(talon.getPath()).getInvocationMap();
        Map<String, String> changedWords = new HashMap<>();

        for (var entry : talonCommands.entrySet()) {
            if (!baseInvocations.containsKey(entry.getKey())) {
                if (baseTargets.containsKey(entry.getValue())) {
                    String oldWord = baseTargets.get(entry.getValue());
                    String newWord = entry.getKey();
                    if (oldWord != null) {
                        changedWords.put(oldWord, newWord);
                    }
                }
            }
        }

        return changedWords;
    }

    private Map<String, String> getPythonChanges(GHCommit.File commitFile, Map<String, String> changedWords) {

        String patch = commitFile.getPatch();
        List<String> lines = Stream.of(patch.split("\\n"))
                .filter(x -> x.startsWith("+") || x.startsWith("-"))  // Only include changed lines
                .filter(x -> !x.matches("[+-]\\s*#.*"))  // Removes comments
                .collect(Collectors.toList());

        String fileName = commitFile.getFileName();
        if (fileName == "code/formatters.py") {
            return processChanges(changedWords, FileProcessingKt.processFormatters(lines));
        } else if (fileName == "code/keys.py") {
            return processChanges(changedWords, FileProcessingKt.processKeys(lines));
        } else {
            return new HashMap<>();
        }
    }

    private Map<String, String> processChanges(Map<String, String> changedWords, Map<String, String> localChanges) {

        Map<String, String> changedWordsReversed = new HashMap<>();
        if (!localChanges.isEmpty() && !changedWords.isEmpty()) {
            for (var entry : changedWords.entrySet()) {
                changedWordsReversed.put(entry.getValue(), entry.getKey());
            }
        }

        for (var entry : localChanges.entrySet()) {
            String oldWord = entry.getKey();
            String newWord = entry.getValue();

            if (changedWords.containsKey(oldWord)) {
                String oldestWord = changedWordsReversed.get(oldWord);
                changedWordsReversed.put(newWord, oldestWord);
                changedWords.put(oldestWord, newWord);
            } else {
                changedWords.put(oldWord, newWord);
                changedWordsReversed.put(newWord, oldWord);
            }
        }

        return changedWords;
    }
}
