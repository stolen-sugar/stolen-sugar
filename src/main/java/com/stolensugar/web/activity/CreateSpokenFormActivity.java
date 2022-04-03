package com.stolensugar.web.activity;

import com.stolensugar.web.converters.ModelConverter;
import com.stolensugar.web.dao.SpokenFormDao;
import com.stolensugar.web.dynamodb.models.SpokenFormModel;
import com.stolensugar.web.model.SpokenForm;
import com.stolensugar.web.model.requests.CreateSpokenFormRequest;
import com.stolensugar.web.model.response.CreateSpokenFormResponse;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class CreateSpokenFormActivity {
    private SpokenFormDao spokenFormDao;

    /**
     * Instantiates a new CreateSpokenFormActivity object.
     *
     * @param spokenFormDao Dao to access the spokenForm table.
     */
    @Inject
    public CreateSpokenFormActivity(SpokenFormDao spokenFormDao) {
        this.spokenFormDao = spokenFormDao;
    }

    /**
     * Creates a new spokenForm associated with the provided fullName, command, fileName, context, appName, defaultName
     *
     * @param request Request object containing the above attributes
     * @return CreateSpokenFormResponse Response object containing the newly created spokenForm.
     */
    public CreateSpokenFormResponse execute(final CreateSpokenFormRequest request) {
        List<SpokenFormModel> newSpokenFormModels = new ArrayList<>();
        List<SpokenForm> newSpokenForms = new ArrayList<>();

        int size = request.getSpokenForms().size();

        for(int i = 0; i < size; i++) {
            SpokenFormModel spokenFormModel = SpokenFormModel.builder()
                    .fullName(request.getSpokenForms().get(i).getFullName())
                    .command(request.getSpokenForms().get(i).getCommand())
                    .defaultName(request.getSpokenForms().get(i).getDefaultName())
                    .appName(request.getSpokenForms().get(i).getAppName())
                    .context(request.getSpokenForms().get(i).getContext())
                    .fileName(request.getSpokenForms().get(i).getFileName())
                    .build();
            newSpokenFormModels.add(spokenFormModel);
            newSpokenForms.add(ModelConverter.toSpokenForm(spokenFormModel));
        }

        spokenFormDao.saveSpokenForm(newSpokenFormModels);
        return CreateSpokenFormResponse.builder()
                .spokenForms(newSpokenForms)
                .build();
    }
}
