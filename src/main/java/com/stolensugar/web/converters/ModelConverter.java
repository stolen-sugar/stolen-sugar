package com.stolensugar.web.converters;

import com.stolensugar.web.dynamodb.models.SpokenFormModel;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.SpokenForm;
import com.stolensugar.web.model.SpokenFormUser;
import com.stolensugar.web.model.User;
import com.stolensugar.web.dynamodb.models.UserModel;

public class ModelConverter {
    public static User toUser(UserModel user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .imageavatarUrl(user.getImageavatarUrl())
                .talonUrl(user.getTalonUrl())
                .cursorlessUrl(user.getCursorlessUrl())
                .cursorlessLastPush(user.getCursorlessLastPush())
                .talonLastPush(user.getTalonLastPush())
                .build();
    }   
    
    public static SpokenFormUser toSpokenFormUser(SpokenFormUserModel spokenFormUser ) {
        return SpokenFormUser.builder()
                .userId(spokenFormUser.getUserId())
                .spokenFormFullName(spokenFormUser.getSpokenFormFullName())
                .lastUpdated(spokenFormUser.getLastUpdated())
                .app(spokenFormUser.getApp())
                .repo(spokenFormUser.getRepo())
                .branch(spokenFormUser.getBranch())
                .choice(spokenFormUser.getChoice())
                .build();
    }

    public static SpokenForm toSpokenForm(SpokenFormModel spokenForm ) {
        return SpokenForm.builder()
                .fullName(spokenForm.getFullName())
                .command(spokenForm.getCommand())
                .defaultName(spokenForm.getDefaultName())
                .appName(spokenForm.getAppName())
                .context(spokenForm.getContext())
                .fileName(spokenForm.getFileName())
                .build();
    }
}
