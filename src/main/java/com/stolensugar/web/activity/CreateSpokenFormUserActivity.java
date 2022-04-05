package com.stolensugar.web.activity;

import javax.inject.Inject;

import com.stolensugar.web.converters.ModelConverter;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.SpokenFormUser;
import com.stolensugar.web.model.requests.CreateSpokenFormUserRequest;
import com.stolensugar.web.model.response.CreateSpokenFormUserResponse;

import java.util.ArrayList;
import java.util.List;

public class CreateSpokenFormUserActivity {
    private SpokenFormUserDao spokenFormUserDao;

    /**
     * Instantiates a new CreateSpokenFormUserActivity object.
     *
     * @param spokenFormUserDao Dao to access the spokenFormUser table.
     */
    @Inject
    public CreateSpokenFormUserActivity(SpokenFormUserDao spokenFormUserDao) {
        this.spokenFormUserDao = spokenFormUserDao;
    }
    
    /**
     * Creates a new spokenFormUser associated with the provided user id, spokenForm id,
     * spoken form choice.
     *
     * @param request Request object containing the user id, spokenForm id, and choice.
     * @return CreateSpokenFormUserResponse Response object containing the requested spokenFormUser.
     */
    public CreateSpokenFormUserResponse execute(final CreateSpokenFormUserRequest request) {
        List<SpokenFormUserModel> newSpokenFormUserModels = new ArrayList<>();
        List<SpokenFormUser> newSpokenFormUsers = new ArrayList<>();

        int size = request.getSpokenFormUsers().size();

        for(int i = 0; i < size; i++) {
            SpokenFormUserModel spokenFormUserModel = SpokenFormUserModel.builder()
                .userId(request.getSpokenFormUsers().get(i).getUserId())
                .spokenFormFullName(request.getSpokenFormUsers().get(i).getSpokenFormFullName())
                .lastUpdated(request.getSpokenFormUsers().get(i).getLastUpdated())
                .app(request.getSpokenFormUsers().get(i).getApp())
                .repo(request.getSpokenFormUsers().get(i).getRepo())
                .branch(request.getSpokenFormUsers().get(i).getBranch())
                .choice(request.getSpokenFormUsers().get(i).getChoice())
                .file(request.getSpokenFormUsers().get(i).getFile())
                .context(request.getSpokenFormUsers().get(i).getContext())
                .build();
            newSpokenFormUserModels.add(spokenFormUserModel);
            newSpokenFormUsers.add(ModelConverter.toSpokenFormUser(spokenFormUserModel));
        }

        spokenFormUserDao.saveSpokenFormUser(newSpokenFormUserModels);
        return CreateSpokenFormUserResponse.builder()
            .spokenFormUsers(newSpokenFormUsers)
            .build();
    }
}
