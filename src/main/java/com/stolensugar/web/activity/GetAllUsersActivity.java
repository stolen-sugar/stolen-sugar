package com.stolensugar.web.activity;

import com.stolensugar.web.converters.ModelConverter;
import com.stolensugar.web.dao.UserDao;
import com.stolensugar.web.dynamodb.models.UserModel;
import com.stolensugar.web.model.User;
import com.stolensugar.web.model.response.GetAllUsersResponse;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class GetAllUsersActivity {
    private UserDao userDao;

    /**
     * Instantiates a new GetUserActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public GetAllUsersActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    
    /**
     * Retrieves all user.
     * @return GetAllUsersResponse object containing all users.
     */
    public GetAllUsersResponse execute() {
        List<UserModel> userModels = userDao.getAllUsers();

        List<User> users = new ArrayList<>();

        userModels.forEach(user -> users.add(ModelConverter.toUser(user)));

        return GetAllUsersResponse.builder()
            .users(users)
            .build();
    }
}
