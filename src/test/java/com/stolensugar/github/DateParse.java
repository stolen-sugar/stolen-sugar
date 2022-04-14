package com.stolensugar.github;

import com.stolensugar.web.dao.UserDao;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParse {

    private UserDao userDao;
    private SimpleDateFormat dateFormat;
    private final String ID = "18250094";

    public DateParse(UserDao userDao) {
        this.userDao = userDao;
        dateFormat = new SimpleDateFormat();
    }

    public Date parseDate() throws ParseException {
        return dateFormat.parse(userDao.getUser(ID).getTalonLastPush());
    }
}
