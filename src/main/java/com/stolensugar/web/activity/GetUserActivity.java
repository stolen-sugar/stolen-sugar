package com.stolensugar.web.activity;

import javax.inject.Inject;

import com.stolensugar.web.converters.ModelConverter;
import com.stolensugar.web.dao.UserDao;
import com.stolensugar.web.dynamodb.models.UserModel;
import com.stolensugar.web.model.requests.GetUserRequest;
import com.stolensugar.web.model.response.GetUserResponse;

public class GetUserActivity {
    private UserDao userDao;

    /**
     * Instantiates a new GetUserActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public GetUserActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    
    /**
     * Retrieves the user associated with the provided user id.
     *
     * @param request Request object containing the user ID associated with the user to get from the User.
     * @return GetUserResponse Response object containing the requested user.
     */
    public GetUserResponse execute(final GetUserRequest request) {
        UserModel userModel = userDao.getUser(request.getUserId());
        return GetUserResponse.builder()
            .user(ModelConverter.toUser(userModel))
            .build();
    }
}

