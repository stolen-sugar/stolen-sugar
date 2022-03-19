package com.stolensugar.web.converters;

import com.stolensugar.web.model.User;
import com.stolensugar.web.dynamodb.models.UserModel;

public class ModelConverter {
    public static User toUser(UserModel user) {
        return User.builder()
                .withId(user.getId())
                .withName(user.getName())
                .withImageAvatarUrl(user.getImageavatarUrl())
                .withReposUrl(user.getReposUrl())
                .build();
    }   
}
