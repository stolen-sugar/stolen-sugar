package com.stolensugar.web.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.kms.model.NotFoundException;
import com.stolensugar.web.dynamodb.models.SpokenFormModel;

import javax.inject.Inject;

import java.util.ArrayList;
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
     * Returns the SpokenForm corresponding to the specified  fileName and action.
     * Throws a NotFoundException if the SpokenForm is not found.
     * @param fileName  fileName associated with the spokenForm.
     * @param action  action associated with the spokenForm.
     * @return The corresponding spokenForm.
     */
    public SpokenFormModel getSpokenForm(String fileName, String action) {
        SpokenFormModel spokenForm = loadSpokenForm(fileName, action);

        if (spokenForm == null) {
            throw new NotFoundException("SpokenFormModel with fullName "  + fileName +
                    " not found.");
        }

        return spokenForm;
    }

    /**
     * Loads spokenForm from database.
     * @param action  action associated with the spokenForm.
     * @param fileName  fullName associated with the spokenForm.
     * @return spokenForm from database.
     */
    public SpokenFormModel loadSpokenForm(String fileName, String action) {
        SpokenFormModel spokenForm =
                dynamoDbMapper.load(SpokenFormModel.class,fileName, action);

        return spokenForm;
    }

    /**
     * Creates new or update existing spokenForms in database.
     * @param spokenFormModels list of models associated with the spokenForms.
     */
    public void saveSpokenForm(List<SpokenFormModel> spokenFormModels) {
        dynamoDbMapper.batchSave(spokenFormModels);
    }

    /**
     * Creates new or update existing spokenForms in database.
     * @param spokenFormModels list of models associated with the spokenForms.
     */
    public void saveSingleSpokenForm(SpokenFormModel spokenFormModels) {
        dynamoDbMapper.save(spokenFormModels);
    }

    /**
     * Add additional vocabulary to the spokenForm in the database.
     * @param action Partition key associated with the spokenForm.
     * @param fileName Range key associated with the spokenForm.
     * @param alternatives list of alternate terms for spokenForm command.
     */
    public String updateItemAlternatives(String fileName,String action,
                                   List<String> alternatives) {

//        should use UpdateItem() instead

        SpokenFormModel spokenForm = getSpokenForm(fileName, action);

        List<String> currentAlternatives;

        if (spokenForm.getAlternatives() != null) {
            currentAlternatives = spokenForm.getAlternatives();
        } else {
            currentAlternatives = new ArrayList<>();
        }
        
        alternatives.forEach(alternative -> currentAlternatives.add(alternative));

        spokenForm.setAlternatives(currentAlternatives);

        saveSingleSpokenForm(spokenForm);

        return "success";
    }
}
