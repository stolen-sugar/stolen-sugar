package com.stolensugar.web.controller;
import com.stolensugar.web.AppApplication;
import com.stolensugar.web.activity.CreateSpokenFormUserActivity;
import com.stolensugar.web.activity.GetUserActivity;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.requests.CreateSpokenFormUserRequest;
import com.stolensugar.web.model.requests.GetUserRequest;
import com.stolensugar.web.dagger.ApplicationComponent;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

@RestController
public class Controller {

     private static final ApplicationComponent component = AppApplication.component;

    @GetMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity<?> getUser(@PathVariable String id) {
        GetUserActivity userActivity = component.provideGetUserActivity();
        GetUserRequest getUserRequest = GetUserRequest.builder().userId(id).build();
        return new ResponseEntity<>(userActivity.execute(getUserRequest), HttpStatus.OK);
    }

    @PostMapping(value = "/spokenformuser", consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> createSpokenFormUser(@Valid @RequestBody List<SpokenFormUserModel> spokenFormUsers) {
        CreateSpokenFormUserActivity spokenFormUserActivity = component.provideCreateSpokenFormUserActivity();

        CreateSpokenFormUserRequest spokenFormUserRequest =
                CreateSpokenFormUserRequest.builder().spokenFormUsers(spokenFormUsers).build();

        return new ResponseEntity<>(spokenFormUserActivity.execute(spokenFormUserRequest), HttpStatus.OK);
    }
}
