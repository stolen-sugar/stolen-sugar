package com.stolensugar.web.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import lombok.*;
import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName = "spokenFormUsers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class SpokenFormUserModel {
    @Getter(onMethod_={@DynamoDBHashKey}) @Setter @EqualsAndHashCode.Include  private String id;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String spokenFormId;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String userId;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String choice;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private String branchId;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private List<String> choiceHistory;
    @Getter(onMethod_={@DynamoDBAttribute}) @Setter private Map<String, String> details;
    @Getter(onMethod_={@DynamoDBAttribute, @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)}) @Setter private Boolean pullRequestAvailable;
}