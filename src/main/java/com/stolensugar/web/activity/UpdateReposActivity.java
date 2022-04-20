package com.stolensugar.web.activity;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.stolensugar.web.CommandGroupMappings;
import com.stolensugar.web.FileProcessingKt;
import com.stolensugar.web.UserChanges;
import com.stolensugar.web.dao.SpokenFormDao;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dao.UserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormModel;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.dynamodb.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
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

public class UpdateReposActivity implements RequestHandler<ScheduledEvent, String> {

    private final SpokenFormDao spokenFormDao;
    private final SpokenFormUserDao spokenFormUserDao;
    private final UserDao userDao;
    private final Map<String, CommandGroupMappings> baseCommands;
    private final Map<String, SpokenFormModel> spokenFormMap;

    private static final String BASE_USER_ID = "15005956";
    private static final String BASE_REPO_NAME = "knausj85/knausj_talon";
    private static final Logger LOG = LogManager.getLogger(UpdateReposActivity.class);

    @Inject
    public UpdateReposActivity(final SpokenFormDao spokenFormDao, final SpokenFormUserDao spokenFormUserDao, final UserDao userDao) {
        this.spokenFormDao = spokenFormDao;
        this.spokenFormUserDao = spokenFormUserDao;
        this.userDao = userDao;
        this.baseCommands = new HashMap<>();
        this.spokenFormMap = new HashMap<>();
    }

    @Override
    public String handleRequest(final ScheduledEvent input, Context context) {
        LOG.info("Executing UpdateTask");
        populateBaseCommands();
        try {
            updateRepos();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return "Failed with exception";
        }

        return "Success";
    }


    private void updateRepos() throws IOException {
        final GitHub github = GitHubBuilder.fromEnvironment().build();
        final GHRepository baseRepo = github.getRepository(BASE_REPO_NAME);
        final Set<String> baseCommitShas = getCommitShas(baseRepo);
        final List<GHRepository> forks = baseRepo.listForks().toList();

        try {
            updateRepo(baseRepo, Collections.emptySet());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        for (var fork : forks) {
            try {
                updateRepo(fork, baseCommitShas);
            } catch (Exception e) {
                LOG.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void updateRepo(GHRepository fork, Set<String> baseCommitShas) throws IOException {
        // Retrieve user from our database
        UserModel user;
        try {
            user = userDao.getUser(String.valueOf(fork.getOwner().getId()));
        } catch (NotFoundException e) {
            LOG.error(e.getMessage());
            return;
        }


        // Convert timestamp string into Java Date
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis();
        Date lastPush = dateTimeFormatter.parseDateTime(user.getTalonLastPush()).toDate();

        // Update the user's lastPush in the database to the current time
        String currentTimestamp = dateTimeFormatter.print(new DateTime(DateTimeZone.UTC));
        user.setTalonLastPush(currentTimestamp);
        LOG.info("Saving " + user);
        userDao.saveUser(List.of(user));

        // Try to retrieve commits from stolen-sugar branch, or master if that fails
        List<GHCommit> commits;
        String branch;
        try {
            branch = "stolen-sugar";
            commits = getCommits(fork, lastPush, branch);
        } catch (GHFileNotFoundException e1) {
            LOG.debug(e1.getMessage());
            branch = "master";
            try {
                commits = getCommits(fork, lastPush, branch);
            } catch (GHFileNotFoundException e2) {
                LOG.error(e2.getMessage());
                return;
            }

        }

        UserChanges userChanges = new UserChanges(user.getId(), branch, new HashMap<>(), new HashMap<>());

        for (var commit : commits) {
            if (!baseCommitShas.contains(commit.getSHA1())) {
                for (var commitFile : commit.getFiles()) {
                    String fileName = commitFile.getFileName();
                    if (baseCommands.containsKey(fileName)) {
                        if (fileName.endsWith(".talon")) {
                            GHContent talonFile;
                            try {
                                talonFile = fork.getFileContent(fileName, branch);
                            } catch (GHFileNotFoundException e) {
                                LOG.error(e.getMessage());
                                continue;
                            }
                            userChanges.getBaseChanges().put(fileName, getTalonChanges(talonFile));
                        } else if (fileName.endsWith(".py")) {
                            processPython(commitFile, userChanges);
                        }
                    }
                }
            }
        }

        if (userChanges.getUserId().equals(BASE_USER_ID)) {
            List<SpokenFormModel> spokenForms = new ArrayList<>();
            for (String fileName : userChanges.getBaseChanges().keySet()) {
                Map<String, String> changedWords = userChanges.getBaseChanges().get(fileName);
                for (String oldWord : changedWords.keySet()) {
                    String newWord = changedWords.get(oldWord);
                    String action = baseCommands.get(fileName).getInvocationMap().get(oldWord);
                    SpokenFormModel spokenForm = spokenFormMap.get(fileName + action);
                    spokenForm.setDefaultName(newWord);
                    spokenForms.add(spokenForm);

                    baseCommands.get(fileName).getInvocationMap().remove(oldWord);
                    baseCommands.get(fileName).getInvocationMap().put(newWord, action);
                    baseCommands.get(fileName).getTargetMap().put(action, newWord);
                }
            }

            for (var spokenForm : spokenForms) {
                LOG.info("Saving " + spokenForm);
            }
            spokenFormDao.saveSpokenForm(spokenForms);

            return;
        }

        List<SpokenFormUserModel> spokenFormUsers = new ArrayList<>();
        for (String fileName : userChanges.getBaseChanges().keySet()) {
            Map<String, String> changedWords = userChanges.getBaseChanges().get(fileName);
            for (String oldWord : changedWords.keySet()) {
                String newWord = changedWords.get(oldWord);
                String action = baseCommands.get(fileName).getInvocationMap().get(oldWord);
                SpokenFormModel spokenForm = spokenFormMap.get(fileName + action);
                if (spokenForm == null) {
                    continue;
                }
                SpokenFormUserModel spokenFormUser = SpokenFormUserModel.builder()
                        .action(action)
                        .fullName(fileName + "::s::" + userChanges.getUserId())
                        .app("talon")
                        .repo(fork.getName())
                        .branch(branch)
                        .choice(newWord)
                        .file(fileName)
                        .userId(userChanges.getUserId())
                        .context(spokenForm.getContext())
                        .lastUpdated(currentTimestamp)
                        .build();

                spokenFormUsers.add(spokenFormUser);

                Set<String> alternatives = spokenForm.getAlternatives();
                if (alternatives == null) {
                    alternatives = new HashSet<>();
                }
                if (alternatives.add(newWord)) {
                    LOG.info(String.format("Updating alternatives -> File name: %s  Action: %s  Alternatives: %s", fileName, action, alternatives));
                    spokenFormDao.updateItemAlternatives(fileName, action, alternatives);
                }
            }

            spokenFormUsers.addAll(userChanges.getSpokenFormUsers().getOrDefault(fileName, Collections.emptyMap()).values());
        }

        for (var spokenFormUser : spokenFormUsers) {
            LOG.info("Saving " + spokenFormUser);
        }
        spokenFormUserDao.saveSpokenFormUser(spokenFormUsers);
    }

    private Set<String> getCommitShas(GHRepository repo) throws IOException {
        return repo.queryCommits()
                .pageSize(100)
                .list()
                .toSet()
                .stream()
                .map(GHCommit::getSHA1)
                .collect(Collectors.toSet());
    }

    private List<GHCommit> getCommits(GHRepository repo, Date startDate, String branch) throws IOException {
        List<GHCommit> commits =  repo.queryCommits()
                .pageSize(100)
                .since(startDate)
                .from(branch)
                .list()
                .toList()
                .stream()
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.reverse(commits);

        return commits;
    }

    private void populateBaseCommands() {
        List<SpokenFormModel> spokenForms = spokenFormDao.getAllSpokenForms();

        Map<String, List<SpokenFormModel>> spokenFormMap = new HashMap<>();
        for (var spokenForm : spokenForms) {
            String fileName = spokenForm.getFileName();
            if (spokenFormMap.containsKey(fileName)) {
                spokenFormMap.get(fileName).add(spokenForm);
            } else {
                spokenFormMap.put(fileName, new ArrayList<>(List.of(spokenForm)));
            }

            this.spokenFormMap.put(fileName + spokenForm.getAction(), spokenForm);
        }

        spokenFormMap.forEach((fileName, spokenFormList) -> baseCommands.put(fileName, new CommandGroupMappings(
                spokenFormList.stream()
                        .collect(Collectors.toMap(SpokenFormModel::getDefaultName, SpokenFormModel::getAction)),
                spokenFormList.stream()
                        .collect(Collectors.toMap(SpokenFormModel::getAction, SpokenFormModel::getDefaultName)))));
    }

    private Map<String, String> getTalonChanges(GHContent talon) {
        String talonString;
        try {
            talonString = new String(talon.read().readAllBytes());
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return Collections.emptyMap();
        }

        Map<String, String> talonCommands = FileProcessingKt.processTalon(talonString);
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

    private void processPython(GHCommit.File commitFile, @NotNull UserChanges userChanges) {
        boolean isBaseUser = userChanges.getUserId().equals(BASE_USER_ID);
        Map<String, String> baseChanges = userChanges.getBaseChanges().getOrDefault(commitFile.getFileName(), new HashMap<>());
        Map<String, SpokenFormUserModel> spokenFormUsers = userChanges.getSpokenFormUsers().getOrDefault(commitFile.getFileName(), new HashMap<>());

        String patch = commitFile.getPatch();
        List<String> lines = Stream.of(patch.split("\\n"))
                .filter(x -> x.startsWith("+") || x.startsWith("-"))  // Only include changed lines
                .filter(x -> !x.matches("[+-]\\s*#.*"))  // Removes comments
                .collect(Collectors.toList());

        String fileName = commitFile.getFileName();

        Map<String, String> localChanges;
        if (fileName.equals("code/formatters.py")) {
            localChanges = FileProcessingKt.processFormatters(lines);
        } else if (fileName.equals("code/keys.py")) {
            localChanges = FileProcessingKt.processKeys(lines);
        } else {
            localChanges = new HashMap<>();
        }

        Map<String, String> baseChangesReversed = new HashMap<>();
        if (!localChanges.isEmpty() && !baseChanges.isEmpty()) {
            for (var entry : baseChanges.entrySet()) {
                baseChangesReversed.put(entry.getValue(), entry.getKey());
            }
        }

        for (var entry : localChanges.entrySet()) {
            String oldWord = entry.getKey();
            String newWord = entry.getValue();

            if (baseCommands.get(fileName).getInvocationMap().containsKey(oldWord)) {
                baseChanges.put(oldWord, newWord);
                baseChangesReversed.put(newWord, oldWord);
            } else if (baseChanges.containsKey(oldWord)) {
                String oldestWord = baseChangesReversed.get(oldWord);
                baseChangesReversed.put(newWord, oldestWord);
                baseChanges.put(oldestWord, newWord);
            } else if (spokenFormUsers.containsKey(oldWord)) {
                SpokenFormUserModel spokenFormUserModel = spokenFormUsers.get(oldWord);
                spokenFormUserModel.setChoice(newWord);
                spokenFormUsers.remove(oldWord);
                spokenFormUsers.put(newWord, spokenFormUserModel);
            } else if (!isBaseUser) {
                String fullName = fileName + "::s::" + userChanges.getUserId();
                List<SpokenFormUserModel> queryResult = spokenFormUserDao.querySpokenFormUsersByChoice(oldWord, fullName)
                        .stream()
                        .filter(x -> Objects.equals(x.getBranch(), userChanges.getBranch()))
                        .collect(Collectors.toList());
                if (queryResult.size() == 1) {
                    SpokenFormUserModel spokenFormUserModel = queryResult.get(0);
                    spokenFormUserModel.setChoice(newWord);
                    spokenFormUsers.put(newWord, spokenFormUserModel);
                }
            }
        }

        userChanges.getBaseChanges().put(fileName, baseChanges);

        if (!isBaseUser) {
            userChanges.getSpokenFormUsers().put(fileName, spokenFormUsers);
        }
    }
}
