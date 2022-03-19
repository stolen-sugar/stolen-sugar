package com.stolensugar.web.controller;
import com.stolensugar.web.AppApplication;
import com.stolensugar.web.activity.GetUserActivity;
import com.stolensugar.web.model.requests.GetUserRequest;
import com.stolensugar.web.dagger.ApplicationComponent;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

     private static final ApplicationComponent component = AppApplication.component;

    @GetMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity<?> getUser(@PathVariable String id) {
        GetUserActivity userActivity = component.provideGetUserActivity();
        GetUserRequest getUserRequest = GetUserRequest.builder().withUserId(id).build();
        return new ResponseEntity<>(userActivity.execute(getUserRequest), HttpStatus.OK);
    }
}
