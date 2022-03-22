package com.stolensugar.web.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;

@DynamoDBTable(tableName = "spokenFormUsers")
public class SpokenFormUserModel {

    @NotBlank
    private String spokenFormId;

    @NotBlank
    private String userId;

    @NotBlank
    private String choice;

    private List<String> choiceHistory;
    private Map<String, String> details;
    private Boolean updatedBranch;

    public SpokenFormUserModel(String spokenFormId, String userId, String choice) {
        this.spokenFormId = spokenFormId;
        this.userId = userId;
        this.choice = choice;
    }

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setSpokenFormId(String spokenFormId) {
        this.spokenFormId = spokenFormId;
    }

    @DynamoDBRangeKey(attributeName = "spokenFormId")
    public String getSpokenFormId() {
        return spokenFormId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBAttribute(attributeName = "imageavatarUrl")
    public Boolean getUpdatedBranch() {
        return updatedBranch;
    }

    public void setUpdatedBranch(Boolean updatedBranch) {
        this.updatedBranch = updatedBranch;
    }

    @DynamoDBAttribute(attributeName = "choice")
    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    @DynamoDBAttribute(attributeName = "choiceHistory")
    public List<String> getChoiceHistory() {
        return choiceHistory;
    }

    public void setChoiceHistory(List<String> choiceHistory) {
        this.choiceHistory = choiceHistory;
    }

    @DynamoDBAttribute(attributeName = "details")
    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((spokenFormId == null) ? 0 : spokenFormId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpokenFormUserModel other = (SpokenFormUserModel) obj;
        if (spokenFormId == null) {
            if (other.spokenFormId != null)
                return false;
        } else if (!spokenFormId.equals(other.spokenFormId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SpokenFormUserModel{" +
                "spokenFormId='" + spokenFormId + '\'' +
                ", userId='" + userId + '\'' +
                ", choice='" + choice + '\'' +
                ", choiceHistory=" + choiceHistory +
                ", details=" + details +
                ", updatedBranch=" + updatedBranch +
                '}';
    }
}