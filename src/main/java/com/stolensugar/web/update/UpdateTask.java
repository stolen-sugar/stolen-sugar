package com.stolensugar.web.update;

import com.stolensugar.web.dao.SpokenFormDao;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dao.UserDao;

import javax.inject.Inject;

public class Updater implements Runnable {

    private SpokenFormDao spokenFormDao;
    private SpokenFormUserDao spokenFormUserDao;
    private UserDao userDao;

    @Inject
    public Updater(SpokenFormDao spokenFormDao, SpokenFormUserDao spokenFormUserDao, UserDao userDao) {
        this.spokenFormDao = spokenFormDao;
        this.spokenFormUserDao = spokenFormUserDao;
        this.userDao = userDao;
    }


    @Override
    public void run() {

    }
}
