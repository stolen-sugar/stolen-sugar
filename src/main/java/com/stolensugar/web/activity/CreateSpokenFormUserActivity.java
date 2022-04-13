package com.stolensugar.web.activity;

import javax.inject.Inject;

import com.stolensugar.web.converters.ModelConverter;
import com.stolensugar.web.dao.SpokenFormDao;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.SpokenFormUser;
import com.stolensugar.web.model.requests.CreateSpokenFormUserRequest;
import com.stolensugar.web.model.response.CreateSpokenFormUserResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CreateSpokenFormUserActivity {
    private SpokenFormUserDao spokenFormUserDao;
    private SpokenFormDao spokenFormDao;
    private Map<String, Map<String, Set<String> >> alternatives = new HashMap<>();
    /**
     * Instantiates a new CreateSpokenFormUserActivity object.
     *
     * @param spokenFormUserDao Dao to access the spokenFormUser table.
     */
    @Inject
    public CreateSpokenFormUserActivity(SpokenFormUserDao spokenFormUserDao, SpokenFormDao spokenFormDao) {
        this.spokenFormUserDao = spokenFormUserDao;
        this.spokenFormDao = spokenFormDao;
    }
    
    /**
     * Creates a new spokenFormUser associated with the provided user id, spokenForm id,
     * spoken form choice.
     *
     * @param request Request object containing the user id, spokenForm id, and choice.
     * @return CreateSpokenFormUserResponse Response object containing the requested spokenFormUser.
     */
    public CreateSpokenFormUserResponse execute(final CreateSpokenFormUserRequest request) {
        List<SpokenFormUserModel> newSpokenFormUserModels = new ArrayList<>();
        List<SpokenFormUser> newSpokenFormUsers = new ArrayList<>();

        int size = request.getSpokenFormUsers().size();

        for(int i = 0; i < size; i++) {
            SpokenFormUserModel spokenFormUserModel = SpokenFormUserModel.builder()
                .userId(request.getSpokenFormUsers().get(i).getUserId())
                .action(request.getSpokenFormUsers().get(i).getAction())
                .fullName(request.getSpokenFormUsers().get(i).getFullName())
                .lastUpdated(request.getSpokenFormUsers().get(i).getLastUpdated())
                .app(request.getSpokenFormUsers().get(i).getApp())
                .repo(request.getSpokenFormUsers().get(i).getRepo())
                .branch(request.getSpokenFormUsers().get(i).getBranch())
                .choice(request.getSpokenFormUsers().get(i).getChoice())
                .file(request.getSpokenFormUsers().get(i).getFile())
                .context(request.getSpokenFormUsers().get(i).getContext())
                .build();
            newSpokenFormUserModels.add(spokenFormUserModel);
            newSpokenFormUsers.add(ModelConverter.toSpokenFormUser(spokenFormUserModel));
            addToAlternatives(request.getSpokenFormUsers().get(i).getFile(),
                    request.getSpokenFormUsers().get(i).getAction(),
                    request.getSpokenFormUsers().get(i).getChoice());
        }

        addNewAlternativesToDao();

        spokenFormUserDao.saveSpokenFormUser(newSpokenFormUserModels);
        return CreateSpokenFormUserResponse.builder()
            .spokenFormUsers(newSpokenFormUsers)
            .build();
    }

    private void addToAlternatives(String file, String action, String choice) {
        Map<String, Set<String>> fileMap = new HashMap<>();
        Set<String> actionSet = new HashSet<>();
        actionSet.add(choice);
        fileMap.put(action, actionSet);

        if(alternatives.get(file) != null) {
            if(alternatives.get(file).get(action) != null) {
                Set<String> currentActionSet =
                        alternatives.get(file).get(action);
                currentActionSet.add(choice);
                alternatives.get(file).put(action, currentActionSet);
            } else {
                alternatives.get(file).put(action, actionSet);
            }
        } else {
            alternatives.put(file, fileMap);
        }
    }

    private void addNewAlternativesToDao() {
        Iterator<Map.Entry<String, Map<String, Set<String>>>> itr = alternatives.entrySet().iterator();
        
        while (itr.hasNext()) {
            Map.Entry<String, Map<String, Set<String>>> entry = itr.next();
            String file = entry.getKey();
            Iterator<Map.Entry<String, Set<String>>> innerItr = entry.getValue().entrySet().iterator();
            
            while(innerItr.hasNext()) {
                Map.Entry<String, Set<String>> actionEntry = innerItr.next();
                String action = actionEntry.getKey();
                Set<String> alts = actionEntry.getValue();
                spokenFormDao.updateItemAlternatives(file, action, alts);
            }
        }
    }    
}
