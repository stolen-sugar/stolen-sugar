package com.stolensugar.web.dao;
import javax.inject.Inject;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.kms.model.NotFoundException;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;

public class SpokenFormUserDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a new SpokenFormUser object.
     *
     * @param dynamoDbMapper The {@link DynamoDBMapper} used to interact with the spokenFormUser table.
     */
    @Inject
    public SpokenFormUserDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the SpokenFormUser corresponding to the specified user id and the spokenForm id.
     * Throws a NotFoundException if the SpokenForUser is not found.
     * @param action action associated with the spokenFormUser.
     * @param fullName fullName associated with the spokenFormUser
     * @return The corresponding spokenFormUser.
     */
    public SpokenFormUserModel getSpokenFormUser(String action, String fullName) {
        SpokenFormUserModel spokenFormUser = loadSpokenFormUser(action, fullName);

        if (spokenFormUser == null) {
            throw new NotFoundException("SpokenFormUserModel with action "  + action + 
            " and the fullName " + fullName +  " not found.");
        }

        return spokenFormUser;
    }

    /**
     * Loads spokenFormUser from database.
     * @param action action associated with the spokenFormUser.
     * @param fullName fullName associated with the spokenFormUser.
     * @return spokenFormUser from database.
     */
    public SpokenFormUserModel loadSpokenFormUser(String action, String fullName) {
        SpokenFormUserModel spokenFormUser = dynamoDbMapper.load(SpokenFormUserModel.class, action, fullName);

        return spokenFormUser;
    }

    /**
     * Creates a new spokenFormUser in database.
     * @param spokenFormUserModels list of models associated with the spokenFormUsers.
     */
    public void saveSpokenFormUser(List<SpokenFormUserModel> spokenFormUserModels) {
        List<DynamoDBMapper.FailedBatch> failedBatches =
                dynamoDbMapper.batchSave(spokenFormUserModels);
        System.out.println(failedBatches);
    }    
}
