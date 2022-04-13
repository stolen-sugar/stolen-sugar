package com.stolensugar.web.update;

import com.stolensugar.web.dao.SpokenFormDao;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dao.UserDao;
import org.kohsuke.github.*;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class UpdateTask implements Runnable {

    private final GitHub github;
    private final SpokenFormDao spokenFormDao;
    private final SpokenFormUserDao spokenFormUserDao;
    private final UserDao userDao;

    private static Date lastUpdated;

    @Inject
    public UpdateTask(final GitHub github, final SpokenFormDao spokenFormDao, final SpokenFormUserDao spokenFormUserDao, final UserDao userDao) {
        this.github = github;
        this.spokenFormDao = spokenFormDao;
        this.spokenFormUserDao = spokenFormUserDao;
        this.userDao = userDao;
        lastUpdated = new Date();
    }


    @Override
    public void run() {

        try {
            updateRepos();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public void updateRepos() throws IOException {
        GHRepository baseRepo = github.getRepository("knausj85/knausj_talon");
        Set<String> baseCommitShas = getCommitShas(baseRepo);
        List<GHRepository> forks = baseRepo.listForks().toList();

        for (var fork : forks) {
            List<GHCommit> commits = getCommits(fork, lastUpdated);
            for (var commit : commits) {
                if (!baseCommitShas.contains(commit.getSHA1())) {

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

    public List<GHCommit> getCommits(GHRepository repo, Date date) throws IOException {
        List<GHCommit> commits =  repo.queryCommits()
                .pageSize(100)
                .since(date)
                .list()
                .toList();

        Collections.reverse(commits);

        return commits;
    }

}
