package com.stolensugar.web.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.*;

@DynamoDBTable(tableName = "spokenFormUsers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SpokenFormUserModel {
    public static final String USER_ID_APP_INDEX = "userId-app-index";
    public static final String CHOICE_FULL_NAME_INDEX = "choice-fullName-index";
    

    @Getter(onMethod_={@DynamoDBHashKey}) @Setter @EqualsAndHashCode.Include  private String action;
    @Getter(onMethod_={@DynamoDBRangeKey, @DynamoDBIndexRangeKey(globalSecondaryIndexName =
            CHOICE_FULL_NAME_INDEX, attributeName = "fullName")}) @Setter @EqualsAndHashCode.Include private String fullName;
    @Getter(onMethod_={@DynamoDBIndexRangeKey(globalSecondaryIndexName =
            USER_ID_APP_INDEX, attributeName = "app")}) @Setter
    private String app;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String repo;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String branch;
    @Getter(onMethod_={@DynamoDBAttribute, @DynamoDBIndexHashKey(globalSecondaryIndexName =
            CHOICE_FULL_NAME_INDEX, attributeName = "choice")}) @Setter private String choice;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String lastUpdated;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String file;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String context;
    @Getter(onMethod_={@DynamoDBIndexHashKey(globalSecondaryIndexName =
            USER_ID_APP_INDEX, attributeName = "userId")}) @Setter
    private String userId;
}