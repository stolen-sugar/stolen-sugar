package com.stolensugar.web.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@DynamoDBTable(tableName = "users")
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserModel {
    @Setter @Getter(onMethod_={@DynamoDBHashKey}) @EqualsAndHashCode.Include private String id;
    @Setter @Getter(onMethod_={@DynamoDBAttribute}) private String name;
    @Setter @Getter(onMethod_={@DynamoDBAttribute}) private String imageavatarUrl;
    @Getter @Setter(onMethod_={@DynamoDBAttribute}) private String talonUrl;
    @Getter @Setter(onMethod_={@DynamoDBAttribute}) private String cursorlessUrl;
    @Getter @Setter(onMethod_={@DynamoDBAttribute}) private String talonLastPush;
    @Getter @Setter(onMethod_={@DynamoDBAttribute}) private String cursorlessLastPush;
}