package com.stolensugar.web.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@DynamoDBTable(tableName = "users")
public class UserModel {
    @Setter @Getter(onMethod_={@DynamoDBHashKey}) @EqualsAndHashCode.Include private String id;
    @Setter @Getter(onMethod_={@DynamoDBAttribute}) private String name;
    @Setter @Getter(onMethod_={@DynamoDBAttribute}) private String imageavatarUrl;
    @Setter @Getter(onMethod_={@DynamoDBAttribute}) private String reposUrl;
}