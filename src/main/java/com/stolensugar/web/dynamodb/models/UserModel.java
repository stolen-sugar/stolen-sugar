package com.stolensugar.web.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "users")
public class UserModel {
    private String id;
    private String name;
    private String imageavatarUrl;
    private String reposUrl;

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "imageavatarUrl")
    public String getImageavatarUrl() {
        return imageavatarUrl;
    }

    public void setImageavatarUrl(String imageavatarUrl) {
        this.imageavatarUrl = imageavatarUrl;
    }

    @DynamoDBAttribute(attributeName = "reposUrl")
    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", imageavatarUrl=" + imageavatarUrl + ", name=" + name + ", reposUrl=" + reposUrl
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((imageavatarUrl == null) ? 0 : imageavatarUrl.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((reposUrl == null) ? 0 : reposUrl.hashCode());
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
        UserModel other = (UserModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (imageavatarUrl == null) {
            if (other.imageavatarUrl != null)
                return false;
        } else if (!imageavatarUrl.equals(other.imageavatarUrl))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (reposUrl == null) {
            if (other.reposUrl != null)
                return false;
        } else if (!reposUrl.equals(other.reposUrl))
            return false;
        return true;
    }

}