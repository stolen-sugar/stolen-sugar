package com.stolensugar.web.update;

import com.stolensugar.web.Command;
import com.stolensugar.web.CommandGroupMappings;
import com.stolensugar.web.dao.SpokenFormDao;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dao.UserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormModel;
import com.stolensugar.web.dynamodb.models.UserModel;
import com.stolensugar.web.model.SpokenForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.*;

import javax.inject.Inject;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class UpdateTask implements Runnable {

    private final GitHub github;
    private final SpokenFormDao spokenFormDao;
    private final SpokenFormUserDao spokenFormUserDao;
    private final UserDao userDao;
    private Map<String, CommandGroupMappings> baseCommands;

    private static Date lastUpdated;
    private static final String BASE_USER_ID = "15005956";
    private static final Logger LOG = LogManager.getLogger(UpdateTask.class);

    @Inject
    public UpdateTask(final GitHub github, final SpokenFormDao spokenFormDao, final SpokenFormUserDao spokenFormUserDao, final UserDao userDao) {
        this.github = github;
        this.spokenFormDao = spokenFormDao;
        this.spokenFormUserDao = spokenFormUserDao;
        this.userDao = userDao;
        this.baseCommands = new HashMap<>();

        lastUpdated = new Date();
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
            UserModel user;
            try {
                user = userDao.getUser(String.valueOf(fork.getOwner().getId()));
            } catch (Exception e) {
                LOG.debug(e.getMessage());
                continue;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat();
            Date lastPush;
            try {
                lastPush = dateFormat.parse(user.getTalonLastPush());
            } catch (ParseException e) {
                LOG.error(e.getMessage());
                continue;
            }

            List<GHCommit> commits;
            try {
                commits = getCommits(fork, lastPush, "stolen-sugar");
            } catch (GHFileNotFoundException e) {
                LOG.debug(e.getMessage());
                commits = getCommits(fork, lastPush, "master");
            }

            for (var commit : commits) {
                if (!baseCommitShas.contains(commit.getSHA1())) {
                    for (var commitFile : commit.getFiles()) {

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



}
