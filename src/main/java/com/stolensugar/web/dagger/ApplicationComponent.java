package com.stolensugar.web.dagger;

import javax.inject.Singleton;

import com.stolensugar.web.activity.*;

import dagger.Component;

@Singleton
@Component(modules = {
        DataAccessModule.class,
})
public interface ApplicationComponent {
    GetUserActivity provideGetUserActivity();
    CreateSpokenFormUserActivity provideCreateSpokenFormUserActivity();
    CreateSpokenFormActivity provideCreateSpokenFormActivity();
    CreateUserActivity provideCreateUserActivity();
    UpdateItemAlternativesActivity provideUpdateItemAlternativesActivity();
    GetAllUsersActivity provideGetAllUsersActivity();
    GetSpokenFormByUserActivity provideGetSpokenFormByUserActivity();
    GetSpokenFormActivity provideGetSpokenFormActivity();
    GetMochiDeckActivity provideGetMochiDeckActivity();
    UpdateReposActivity provideUpdateReposActivity();
}