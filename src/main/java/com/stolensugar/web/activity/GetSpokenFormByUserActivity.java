package com.stolensugar.web.activity;

import com.stolensugar.web.converters.ModelConverter;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.SpokenFormUser;
import com.stolensugar.web.model.response.GetSpokenFormByUserResponse;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class GetSpokenFormByUserActivity {

    private SpokenFormUserDao spokenFormUserDao;

    /**
     * Instantiates a new GetSpokenFormByUserActivity object.
     *
     * @param spokenFormUserDao UserDao to access the spokenFormByUser table.
     */
    @Inject
    public GetSpokenFormByUserActivity(SpokenFormUserDao spokenFormUserDao) {
        this.spokenFormUserDao = spokenFormUserDao;
    }

    /**
     * Retrieves all user.
     * @return GetAllUsersResponse object containing all users.
     */
    public GetSpokenFormByUserResponse execute(String id, String app) {
        List<SpokenFormUserModel> userModels =
                spokenFormUserDao.getByUser(id, app);

       List<SpokenFormUser> users = new ArrayList<>();

       userModels.forEach(user -> users.add(ModelConverter.toSpokenFormUser(user)));

        return GetSpokenFormByUserResponse.builder()
                .users(users)
                .build();
    }
}