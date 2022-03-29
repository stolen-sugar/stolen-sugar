package com.stolensugar.web.converters;

import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.SpokenFormUser;
import com.stolensugar.web.model.User;
import com.stolensugar.web.dynamodb.models.UserModel;

public class ModelConverter {
    public static User toUser(UserModel user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .imageavatarUrl(user.getImageavatarUrl())
                .reposUrl(user.getReposUrl())
                .build();
    }   
    
    public static SpokenFormUser toSpokenFormUser(SpokenFormUserModel spokenFormUser ) {
        return SpokenFormUser.builder()
                .id(spokenFormUser.getId())
                .userId(spokenFormUser.getUserId())
                .spokenFormId(spokenFormUser.getSpokenFormId())
                .choice(spokenFormUser.getChoice())
                .choiceHistory(spokenFormUser.getChoiceHistory())
                .details(spokenFormUser.getDetails())
                .pullRequestAvailable(spokenFormUser.getPullRequestAvailable())
                .build();
    }   
}
