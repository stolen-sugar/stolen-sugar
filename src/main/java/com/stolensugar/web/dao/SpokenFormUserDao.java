package com.stolensugar.web.dao;
import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.kms.model.NotFoundException;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpokenFormUserDao {
    private final DynamoDBMapper dynamoDbMapper;

    private static final Logger LOG = LogManager.getLogger(SpokenFormUserDao.class);

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
     * Returns the PaginatedQueryList SpokenFormUserModel corresponding to the specified user id and
     * app
     * @param userId user id associated with the spokenFormUser.
     * @param app app associated with the spokenFormUser
     * @return The corresponding PaginatedQueryList of SpokenFormUserModel
     */
    public List<SpokenFormUserModel> getByUser(String userId, String app) {

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":app", new AttributeValue().withS(app));
        valueMap.put(":userId", new AttributeValue().withS(userId));
        DynamoDBQueryExpression<SpokenFormUserModel> queryExpression = new DynamoDBQueryExpression<SpokenFormUserModel>()
                .withIndexName(SpokenFormUserModel.USER_ID_APP_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("userId = :userId and app = :app")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<SpokenFormUserModel> spokenFormUsers =
                dynamoDbMapper.query(SpokenFormUserModel.class,
                        queryExpression);

        return spokenFormUsers;
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

        for (var failedBatch : failedBatches) {
            LOG.error("Failed to save batch " + failedBatch);
        }
    }

    /**
     * Retrieves query results for a given choice and fullName. Returns a list which
     * should contain at most 1 element.
     * @param choice The choice of the SpokenFormUserModel to be retrieved.
     * @param fullName The fullName associated with this choice.
     * @return List of matching SpokenFormUserModel (should contain at most 1 element)
     */
    public List<SpokenFormUserModel> querySpokenFormUsersByChoice(String choice, String fullName) {

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":choice", new AttributeValue().withS(choice));
        valueMap.put(":fullName", new AttributeValue().withS(fullName));

        DynamoDBQueryExpression<SpokenFormUserModel> queryExpression = new DynamoDBQueryExpression<SpokenFormUserModel>()
                .withIndexName(SpokenFormUserModel.CHOICE_FULL_NAME_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("choice = :choice and fullName = :fullName")
                .withExpressionAttributeValues(valueMap);

        return dynamoDbMapper.query(SpokenFormUserModel.class, queryExpression);
    }
}
