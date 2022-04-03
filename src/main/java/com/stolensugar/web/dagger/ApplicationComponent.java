package com.stolensugar.web.dagger;

import javax.inject.Singleton;

import com.stolensugar.web.activity.CreateSpokenFormActivity;
import com.stolensugar.web.activity.GetUserActivity;
import com.stolensugar.web.activity.CreateSpokenFormUserActivity;

import dagger.Component;

@Singleton
@Component(modules = {
        DataAccessModule.class,
})
public interface ApplicationComponent {
    GetUserActivity provideGetUserActivity();
    CreateSpokenFormUserActivity provideCreateSpokenFormUserActivity();
    CreateSpokenFormActivity provideCreateSpokenFormActivity();
}