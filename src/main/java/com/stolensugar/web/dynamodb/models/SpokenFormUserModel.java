package com.stolensugar.web.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.*;
import java.util.Map;

@DynamoDBTable(tableName = "spokenFormUsers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class SpokenFormUserModel {
    @Getter(onMethod_={@DynamoDBHashKey}) @Setter @EqualsAndHashCode.Include  private String userId;
    @Getter(onMethod_={@DynamoDBRangeKey}) @Setter @EqualsAndHashCode.Include private String spokenFormFullName;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String app;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String repo;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String branch;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String choice;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String lastUpdated;
}