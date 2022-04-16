package com.stolensugar.github;

import com.stolensugar.web.dao.UserDao;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.ParseException;
import java.util.Date;

public class DateParse {

    private UserDao userDao;
    DateTimeFormatter parser;
    private final String ID = "18250094";

    public DateParse(UserDao userDao) {
        this.userDao = userDao;
        parser = ISODateTimeFormat.dateTimeNoMillis();
    }

    public Date parseDate() {
        DateTime jodaDateTime = parser.parseDateTime(userDao.getUser(ID).getTalonLastPush());
        return jodaDateTime.toDate();
    }
}
