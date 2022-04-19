package com.stolensugar.github;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.stolensugar.web.dao.SpokenFormDao;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dao.UserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormModel;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.dynamodb.models.UserModel;
import com.stolensugar.web.update.UpdateTask;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TestDriver {
    public static void main(String[] args) {

        AmazonDynamoDB amazonDynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withRegion(Regions.US_EAST_2)
                .build();
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDBClient);

        UserDao userDao = new UserDao(dynamoDBMapper);
        SpokenFormDao spokenFormDao = new SpokenFormDao(dynamoDBMapper);
        SpokenFormUserDao spokenFormUserDao = new SpokenFormUserDao(dynamoDBMapper);

        DateParse dateParse = new DateParse(userDao);


        try {
//            DateTimeFormatter parser = ISODateTimeFormat.dateTimeNoMillis();
//            DateTime jodaDateTime = parser.parseDateTime("2022-04-05T17:11:57Z");
//            System.out.println(parser.print(jodaDateTime.withZone(DateTimeZone.UTC)));
//            System.out.println(dateParse.parseDate());

//            SpokenFormUserModel spokenFormUserModel = spokenFormUserDao.getSpokenFormUser("w", "code/keys.py" + "::s::" + "18250094");
//            System.out.println(spokenFormUserModel);
//
//            UserModel user = userDao.getUser("15005956");
//            System.out.println(user);
//            user.setTalonLastPush("2022-04-05T17:11:57Z");
//            userDao.saveUser(List.of(user));
//            UserModel updatedUser = userDao.getUser("15005956");
//            System.out.println(updatedUser);

            UpdateTask updateTask = new UpdateTask(spokenFormDao, spokenFormUserDao, userDao);
            updateTask.run();
//            GitHub github = GitHubBuilder.fromEnvironment().build();
//            GHRepository baseRepo = github.getRepository("knausj85/knausj_talon");
//            System.out.println(baseRepo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
