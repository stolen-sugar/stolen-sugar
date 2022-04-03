package com.stolensugar.web.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.kms.model.NotFoundException;
import com.stolensugar.web.dynamodb.models.SpokenFormModel;

import javax.inject.Inject;

import java.util.List;

public class SpokenFormDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a new SpokenForm object.
     *
     * @param dynamoDbMapper The {@link DynamoDBMapper} used to interact with the spokenForm table.
     */
    @Inject
    public SpokenFormDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the SpokenForm corresponding to the specified  fullName.
     * Throws a NotFoundException if the SpokenFor is not found.
     * @param fullName  FullName associated with the spokenForm.
     * @return The corresponding spokenForm.
     */
    public SpokenFormModel getSpokenForm( String fullName) {
        SpokenFormModel spokenForm = loadSpokenForm(fullName);

        if (spokenForm == null) {
            throw new NotFoundException("SpokenFormModel with fullName "  + fullName +
                    " not found.");
        }

        return spokenForm;
    }

    /**
     * Loads spokenForm from database.
     * @param fullName  fullName associated with the spokenForm.
     * @return spokenForm from database.
     */
    public SpokenFormModel loadSpokenForm(String fullName) {
        SpokenFormModel spokenForm = dynamoDbMapper.load(SpokenFormModel.class, fullName);

        return spokenForm;
    }

    /**
     * Creates new or update existing spokenForms in database.
     * @param spokenFormModels list of models associated with the spokenForms.
     */
    public void saveSpokenForm(List<SpokenFormModel> spokenFormModels) {
        dynamoDbMapper.batchSave(spokenFormModels);
    }    
}
