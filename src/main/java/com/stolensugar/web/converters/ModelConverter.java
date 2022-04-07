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
                .fullName(spokenFormUser.getFullName())
                .action(spokenFormUser.getAction())
                .lastUpdated(spokenFormUser.getLastUpdated())
                .app(spokenFormUser.getApp())
                .repo(spokenFormUser.getRepo())
                .branch(spokenFormUser.getBranch())
                .choice(spokenFormUser.getChoice())
                .context(spokenFormUser.getContext())
                .file(spokenFormUser.getFile())
                .build();
    }

    public static SpokenForm toSpokenForm(SpokenFormModel spokenForm ) {
        return SpokenForm.builder()
                .fileName(spokenForm.getFileName())
                .action(spokenForm.getAction())
                .defaultName(spokenForm.getDefaultName())
                .appName(spokenForm.getAppName())
                .context(spokenForm.getContext())
                .alternatives(spokenForm.getAlternatives())
                .build();
    }
}
