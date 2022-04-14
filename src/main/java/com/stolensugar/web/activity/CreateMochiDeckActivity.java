package com.stolensugar.web.activity;

import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.SpokenFormUser;
import com.stolensugar.web.model.requests.CreateMochiDeckRequest;
import com.stolensugar.web.model.response.CreateMochiDeckResponse;

import javax.inject.Inject;
import java.util.List;

public class CreateMochiDeckActivity {
    private SpokenFormUserDao spokenFormUserDao;

    @Inject
    public CreateMochiDeckActivity(SpokenFormUserDao spokenFormUserDao) {

        this.spokenFormUserDao = spokenFormUserDao;
    }

    public CreateMochiDeckResponse execute (final CreateMochiDeckRequest request) {

        if(request.getApp() == null || request.getApp() == "talon") {
            String appRequest = "talon";
        }

        List<SpokenFormUserModel> spokenFormUsers = spokenFormUserDao.getByUser(request.getUserId(), request.getApp());

        

    }


}
