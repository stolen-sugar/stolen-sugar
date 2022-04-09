package com.stolensugar.web.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Set;

@DynamoDBTable(tableName = "spokenForms")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SpokenFormModel {
    public static final String DEFAULT_NAME_FILE_NAME_INDEX = "defaultName-fileName-index";
    public static final String FILE_NAME_INDEX = "fileName-index";

    @Getter(onMethod_ = {@DynamoDBHashKey,
                        @DynamoDBIndexRangeKey(globalSecondaryIndexName =
                                DEFAULT_NAME_FILE_NAME_INDEX, attributeName = "fileName"),
                        @DynamoDBIndexHashKey(globalSecondaryIndexName = FILE_NAME_INDEX, attributeName = "fileName")})
    @Setter
    @EqualsAndHashCode.Include
    private String fileName;
    @Getter(onMethod_ = {@DynamoDBRangeKey})
    @Setter
    @EqualsAndHashCode.Include
    private String action;
    @Getter(onMethod_ = {@DynamoDBAttribute})
    @Setter
    @EqualsAndHashCode.Include
    private String context;
    @Getter(onMethod_ = {@DynamoDBAttribute})
    @Setter
    private String appName;
    @Getter(onMethod_ = {@DynamoDBIndexHashKey(globalSecondaryIndexName = DEFAULT_NAME_FILE_NAME_INDEX, attributeName = "defaultName")})
    @Setter
    private String defaultName;
    @Getter(onMethod_ = {@DynamoDBAttribute})
    @Setter
    private Set<String> alternatives;
}