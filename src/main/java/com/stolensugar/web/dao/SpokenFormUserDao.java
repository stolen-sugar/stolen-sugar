package com.stolensugar.web.dao;
import javax.inject.Inject;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.kms.model.AlreadyExistsException;
import com.amazonaws.services.kms.model.NotFoundException;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.requests.CreateSpokenFormUserRequest;

public class SpokenFormUserDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a new SpokenForUser object.
     *
     * @param dynamoDbMapper The {@link DynamoDBMapper} used to interact with the spokenFormUser table.
     */
    @Inject
    public SpokenFormUserDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the SpokenForUser corresponding to the specified user id and the spokenForm id.
     * Throws a NotFoundException if the SpokenForUser is not found.
     * @param userId user Id associated with the spokenFormUser.
     * @param spokenFormId spoken form Id associated with the spokenFormUser
     * @return The corresponding spokenFormUser.
     */
    public SpokenFormUserModel getSpokenFormUser(String userId, String spokenFormId) {
        SpokenFormUserModel spokenFormUser = loadSpokenFormUser(userId, userId);

        if (spokenFormUser == null) {
            throw new NotFoundException("SpokenFormUserModel with user id "  + userId + 
            " and the spokenform id " + spokenFormId +  " not found.");
        }

        return spokenFormUser;
    }

    /**
     * Loads spokenFormUser from database.
     * @param userId user Id associated with the spokenFormUser.
     * @param spokenFormId spoken form Id associated with the spokenFormUser
     * @return spokenFormUser from database.
     */
    public SpokenFormUserModel loadSpokenFormUser(String userId, String spokenFormId) {
        SpokenFormUserModel spokenFormUser = dynamoDbMapper.load(SpokenFormUserModel.class, userId, spokenFormId);

        return spokenFormUser;
    }

    /**
     * Creates a new spokenFormUser in database.
     * @param List<SpokenFormUserModel> list of models associated with the spokenFormUsers.
     */
    public void saveSpokenFormUser(List<SpokenFormUserModel> spokenFormUserModels) {
        dynamoDbMapper.batchSave(spokenFormUserModels);
    }    
}
