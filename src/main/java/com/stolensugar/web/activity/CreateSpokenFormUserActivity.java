package com.stolensugar.web.activity;

import javax.inject.Inject;

import com.stolensugar.web.converters.ModelConverter;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.requests.CreateSpokenFormUserRequest;
import com.stolensugar.web.model.response.CreateSpokenFormUserResponse;

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
        SpokenFormUserModel spokenFormUserModel = spokenFormUserDao.createSpokenFormUser(
            request.getSpokenFormId(), request.getUserId(), request.getChoice()
        );
        return CreateSpokenFormUserResponse.builder()
            .withSpokenFormUser(ModelConverter.toSpokenFormUser(spokenFormUserModel))
            .build();
    }
}
