package com.stolensugar.web.converters;

import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.SpokenFormUser;
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
    
    public static SpokenFormUser toSpokenFormUser(SpokenFormUserModel spokenFormUser ) {
        return SpokenFormUser.builder()
                .withUserId(spokenFormUser.getUserId())
                .withSpokenFormId(spokenFormUser.getSpokenFormId())
                .withChoice(spokenFormUser.getChoice())
                .withChoiceHistory(spokenFormUser.getChoiceHistory())
                .withDetails(spokenFormUser.getDetails())
                .withUpdatedBranch(spokenFormUser.getUpdatedBranch())
                .build();
    }   
}
