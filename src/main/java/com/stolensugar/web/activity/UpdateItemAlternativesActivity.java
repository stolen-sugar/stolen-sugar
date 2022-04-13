package com.stolensugar.web.activity;

import com.stolensugar.web.dao.SpokenFormDao;
import com.stolensugar.web.model.requests.AlternativesMapper;
import com.stolensugar.web.model.requests.UpdateItemAlternativesRequest;
import com.stolensugar.web.model.response.UpdateItemAlternativesResponse;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class UpdateItemAlternativesActivity {
    private SpokenFormDao spokenFormDao;

    /**
     * Instantiates a new GetUserActivity object.
     *
     * @param spokenFormDao UserDao to access the user table.
     */
    @Inject
    public UpdateItemAlternativesActivity(SpokenFormDao spokenFormDao) {
        this.spokenFormDao = spokenFormDao;
    }

    /**
     * Retrieves the user associated with the provided user id.
     *
     * @param request Request object containing the user ID associated with the user
     *                to get from the User.
     * @return GetUserResponse Response object containing the requested user.
     */
    public UpdateItemAlternativesResponse execute(final UpdateItemAlternativesRequest request) {
        List<AlternativesMapper> alternativesMappers =
                request.getAlternativesMapperList();

        List<String> responses = new ArrayList<>();

        alternativesMappers.forEach( x -> {
            String response =
                    spokenFormDao.updateItemAlternatives(x.getFileName(),x.getAction(),
                            x.getAlternatives());
            responses.add(response);
        });

        return UpdateItemAlternativesResponse.builder()
                .messages(responses)
                .build();
    }

}
