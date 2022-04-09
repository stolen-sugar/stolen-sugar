package com.stolensugar.web.dao;
import javax.inject.Inject;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.kms.model.NotFoundException;
import com.stolensugar.web.dynamodb.models.UserModel;

import java.util.List;


public class UserDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a new UserDao object.
     *
     * @param dynamoDbMapper The {@link DynamoDBMapper} used to interact with the user table.
     */
    @Inject
    public UserDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the user corresponding to the specified user id.
     * Throws a NotFoundException if the user is not found.
     * @param userId Id associated with the user.
     * @return The corresponding User from the user table.
     */
    public UserModel getUser(String userId) {
        UserModel user = dynamoDbMapper.load(UserModel.class, userId);

        if (user == null) {
            throw new NotFoundException("User with id"  + userId +  " not found.");
        }

        return user;
    }

    /**
     * Gets a list of all users in the users table.
     * @return The corresponding User list from the user table.
     */
    public List<UserModel> getAllUsers() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

        List<UserModel> scanResult = dynamoDbMapper.scan(UserModel.class,
                scanExpression);

        return scanResult;
    }

    /**
     * Creates new Users in database.
     * @param userModels list of models associated with the spokenFormUsers.
     */
    public void saveUser(List<UserModel> userModels) {
        dynamoDbMapper.batchSave(userModels);
    }
}
