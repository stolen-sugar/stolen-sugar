package com.stolensugar.github;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.stolensugar.web.dao.UserDao;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.SimpleDateFormat;

public class TestDriver {
    public static void main(String[] args) {

        AmazonDynamoDB amazonDynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withRegion(Regions.US_EAST_2)
                .build();
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDBClient);

        UserDao userDao = new UserDao(dynamoDBMapper);
        DateParse dateParse = new DateParse(userDao);


        try {
            DateTimeFormatter parser = ISODateTimeFormat.dateTimeNoMillis();
            DateTime jodaDateTime = parser.parseDateTime("2022-04-05T17:11:57Z");
            System.out.println(parser.print(jodaDateTime.withZone(DateTimeZone.UTC)));
//            System.out.println(dateParse.parseDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
