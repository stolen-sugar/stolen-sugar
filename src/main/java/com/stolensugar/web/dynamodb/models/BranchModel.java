package com.stolensugar.web.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.time.OffsetDateTime;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@DynamoDBTable(tableName = "branches")
public class BranchModel {
    @Setter @Getter(onMethod_={@DynamoDBHashKey}) @EqualsAndHashCode.Include private String id;
    @Setter @Getter(onMethod_={@DynamoDBAttribute}) private String name;
    @Setter @Getter(onMethod_={@DynamoDBAttribute}) private String url;
    @Setter @Getter(onMethod_={@DynamoDBAttribute}) private String repoId;
    @Setter @Getter(onMethod_={@DynamoDBAttribute}) private String primary;
    @Setter @Getter(onMethod_={@DynamoDBAttribute}) private OffsetDateTime lastUpdate;
}
