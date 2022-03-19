package com.stolensugar.web.dagger;

import javax.inject.Singleton;

import com.stolensugar.web.activity.GetUserActivity;

import dagger.Component;

@Singleton
@Component(modules = {
        DataAccessModule.class,
})
public interface ApplicationComponent {
    GetUserActivity provideGetUserActivity();

}