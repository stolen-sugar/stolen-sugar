package com.stolensugar.web.activity;

import com.stolensugar.web.converters.ModelConverter;
import com.stolensugar.web.dao.SpokenFormDao;
import com.stolensugar.web.dao.UserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormModel;
import com.stolensugar.web.dynamodb.models.UserModel;
import com.stolensugar.web.model.SpokenForm;
import com.stolensugar.web.model.User;
import com.stolensugar.web.model.requests.GetSpokenFormRequest;
import com.stolensugar.web.model.response.GetAllUsersResponse;
import com.stolensugar.web.model.response.GetSpokenFormResponse;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetSpokenFormActivity {
    private SpokenFormDao spokenFormDao;

    /**
     * Instantiates a new GetSpokenFormActivity object.
     *
     * @param spokenFormDao spokenFormDao to access the spokenForm table.
     */
    @Inject
    public GetSpokenFormActivity(SpokenFormDao spokenFormDao) {
        this.spokenFormDao = spokenFormDao;
    }

    /**
     * Retrieves a spokenForm.
     * 
     * @return GetAllUsersResponse object containing all users.
     */
    public GetSpokenFormResponse execute(GetSpokenFormRequest getSpokenFormRequest) {

        List<SpokenFormModel> spokenFormModels = new ArrayList<>();
        List<SpokenForm> spokenForms = new ArrayList<>();
        if (getSpokenFormRequest.getDefaultName() != null) {
            spokenFormModels = spokenFormDao.getSpokenFormByName(getSpokenFormRequest.getDefaultName());
            spokenFormModels.forEach(spokenForm -> spokenForms.add(ModelConverter.toSpokenForm(spokenForm)));

            return GetSpokenFormResponse.builder()
                    .spokenForms(spokenForms)
                    .build();
        } else if (getSpokenFormRequest.getFile() != null) {
            spokenFormModels = spokenFormDao.getSpokenForm(getSpokenFormRequest.getFile());
            spokenFormModels.forEach(spokenForm -> spokenForms.add(ModelConverter.toSpokenForm(spokenForm)));

            return GetSpokenFormResponse.builder()
                    .spokenFormGroups(groupSpokenForms(removeEmptySpokenForms(spokenForms)))
                    .build();
        }
        return null;
    }

    private List<SpokenForm> removeEmptySpokenForms(List<SpokenForm> spokenForms) {
        spokenForms.removeIf(x -> x.getAlternatives() == null || x.getAlternatives().size() == 0);

        return spokenForms;
    }

    private Map<String, List<SpokenForm>> groupSpokenForms(List<SpokenForm> spokenForms) {
        Map<String, List<SpokenForm>> groups = new HashMap<>();

        spokenForms.forEach(spokenForm -> {
            if (groups.get(spokenForm.getContext()) != null) {
                groups.get(spokenForm.getContext()).add(spokenForm);
            } else {
                List<SpokenForm> list = new ArrayList<>();
                list.add(spokenForm);
                groups.put(spokenForm.getContext(), list);
            }
        });

        return groups;
    }
}
