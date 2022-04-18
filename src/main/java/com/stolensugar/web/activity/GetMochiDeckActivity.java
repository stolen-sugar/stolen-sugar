package com.stolensugar.web.activity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.requests.GetMochiDeckRequest;
import com.stolensugar.web.model.response.GetMochiDeckResponse;

import javax.inject.Inject;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class GetMochiDeckActivity {
    private SpokenFormUserDao spokenFormUserDao;

    @Inject
    public GetMochiDeckActivity(SpokenFormUserDao spokenFormUserDao) {

        this.spokenFormUserDao = spokenFormUserDao;
    }

    public GetMochiDeckResponse execute (final GetMochiDeckRequest request) throws Exception {

        if(request.getApp() == null || request.getApp() == "talon") {
            request.setApp("talon");
        }

        List<SpokenFormUserModel> spokenFormUsers = spokenFormUserDao.getByUser(request.getUserId(), request.getApp());

        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("src/main/java/com/stolensugar/web/voiceCommands/tempFiles/mochiMasterTest.json");

        JsonNode root = objectMapper.readTree(jsonFile);
        JsonNode deck = root.get("~:decks");
        JsonNode cards = deck.findValue("~:cards");
        JsonNode list = cards.get("~#list");

//        for(SpokenFormUserModel spokenFormUserModel : spokenFormUsers) {
//            for (final JsonNode item : list) {
//                if (item.findPath("~:name").asText().equals(spokenFormUserModel.getAction())) {
//                    JsonNode fields = item.findValue("~:fields");
//                    JsonNode choice = fields.findValue("~:jvjwdOZ1");
//                    ((ObjectNode) choice).put("~:value", spokenFormUserModel.getChoice());
//                }
//            }
//        }
//        int i = 0;
//        for(JsonNode item : list) {
//
//            JsonNode fields = item.findValue("~:fields");
//            JsonNode choice = fields.findValue("~:jvjwdOZ1");
//            JsonNode file = fields.findValue("~:nyZmZayN");
//
//            for(SpokenFormUserModel spokenFormUserModel : spokenFormUsers) {
//                if (item.findPath("~:name").asText().equals(spokenFormUserModel.getAction())) {
//                    if(file.findPath("~:value").asText().equals(spokenFormUserModel.getFile())) {
//                        ((ObjectNode) choice).put("~:value", spokenFormUserModel.getChoice());
//                        i = 1;
//                    }
//                } else {
//                    i = -1;
//                }
//            }
//        }
        Iterator<JsonNode> itr = list.iterator();
        while(itr.hasNext()) {
            int i = 0;
            JsonNode item = itr.next();
            for(SpokenFormUserModel spokenFormUserModel : spokenFormUsers) {
                if (item.findPath("~:name").asText().equals(spokenFormUserModel.getAction())) {
                    JsonNode fields = item.findValue("~:fields");
                    JsonNode choice = fields.findValue("~:jvjwdOZ1");
                    JsonNode file = fields.findValue("~:nyZmZayN");
                    if (file.findPath("~:value").asText().equals(spokenFormUserModel.getFile())) {
                        ((ObjectNode) choice).put("~:value", spokenFormUserModel.getChoice());
                        i = 1;
                    }
                } else {
                    i = -1;
                }
            }

            if(i == -1) {
                itr.remove();
            }
        }

        return GetMochiDeckResponse.builder()
                .mochiJson(objectMapper.writeValueAsString(root))
                .build();

    }


}
