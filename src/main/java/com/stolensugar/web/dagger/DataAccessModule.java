package com.stolensugar.web.dagger;

import javax.inject.Singleton;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import dagger.Module;
import dagger.Provides;

@Module
public class DataAccessModule {
    @Singleton
    @Provides
    public DynamoDBMapper provideDynamoDBMapper() {
        AWSCredentialsProvider awsCredentialsProvider = awsCredentialsProvider();

        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(Regions.US_EAST_2)
                .build();

        return new DynamoDBMapper(amazonDynamoDB);
    }

    public AWSCredentialsProvider awsCredentialsProvider() {
        return new EnvironmentVariableCredentialsProvider();
    }
}
