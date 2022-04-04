package com.stolensugar.web.activity;

import com.stolensugar.web.converters.ModelConverter;
import com.stolensugar.web.dao.UserDao;
import com.stolensugar.web.dynamodb.models.UserModel;
import com.stolensugar.web.model.User;
import com.stolensugar.web.model.requests.CreateUserRequest;
import com.stolensugar.web.model.response.CreateSpokenFormResponse;
import com.stolensugar.web.model.response.CreateUserResponse;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class CreateUserActivity {
    private UserDao userDao;

    /**
     * Instantiates a new CreateUserActivity object.
     *
     * @param userDao Dao to access the spokenForm table.
     */
    @Inject
    public CreateUserActivity(UserDao userDao) {
        this.userDao = userDao;
    }


    /**
     * Creates a new user associated with the provided attributes
     *
     * @param request Request object containing the provided attributes
     * @return CreateUserResponse Response object containing the newly created user.
     */
    public CreateUserResponse execute(final CreateUserRequest request) {
        List<UserModel> newUserModels = new ArrayList<>();
        List<User> newUser = new ArrayList<>();

        int size = request.getUsers().size();

        for(int i = 0; i < size; i++) {
            UserModel userModel = UserModel.builder()
                    .id(request.getUsers().get(i).getId())
                    .name(request.getUsers().get(i).getName())
                    .imageavatarUrl(request.getUsers().get(i).getImageavatarUrl())
                    .talonUrl(request.getUsers().get(i).getTalonUrl())
                    .talonLastPush(request.getUsers().get(i).getTalonLastPush())
                    .cursorlessUrl(request.getUsers().get(i).getCursorlessUrl())
                    .cursorlessLastPush(request.getUsers().get(i).getCursorlessLastPush())
                    .build();
            newUserModels.add(userModel);
            newUser.add(ModelConverter.toUser(userModel));
        }

        userDao.saveUser(newUserModels);
        return CreateUserResponse.builder()
                .users(newUser)
                .build();
    }
}
