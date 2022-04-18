package com.stolensugar.web.voiceCommands.mochiCards;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;

import java.io.File;
import java.util.Iterator;

public class testMochi {

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("src/main/java/com/stolensugar/web/voiceCommands/tempFiles/mochiMasterTest.json");

        JsonNode root = objectMapper.readTree(jsonFile);
//        String ogJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
//        System.out.println(ogJson);
        JsonNode deck = root.get("~:decks");
        JsonNode cards = deck.findValue("~:cards");
        JsonNode list = cards.get("~#list");
//        JsonNode fields = list.get("~:fields");
//        JsonNode choice = fields.get("~:jvjwdOZ1");
        System.out.println(list.size());
//        for(int i = 0; i < list.size(); i++) {
//            if(list.get(i).findPath("~:name").asText().equals("key(a)")) {
//               JsonNode fields = list.get(i).findValue("~:fields");
//               JsonNode choice = fields.findValue("~:jvjwdOZ1");
//                ((ObjectNode) choice).put("~:value", "!!!!!!9898");
//            } else {
//                ((ArrayNode) list).remove(i);
//            }
//        }
//        for(JsonNode item : list) {
//            if (item.findPath("~:name").asText().equals("key(a)")) {
//                JsonNode fields = item.findValue("~:fields");
//                JsonNode choice = fields.findValue("~:jvjwdOZ1");
//                JsonNode file = fields.findValue("~:nyZmZayN");
//                if(file.findPath("~:value").asText().equals("apps/slack/slack_mac.talon")) {
//                    ((ObjectNode) choice).put("~:value", "testing");
//                }
//            } else {
//                ((ObjectNode) item).removeAll();
//            }
//        }

        Iterator<JsonNode> itr = list.iterator();
        while(itr.hasNext()) {
            JsonNode item = itr.next();
            if (item.findPath("~:name").asText().equals("key(a)")) {
                JsonNode fields = item.findValue("~:fields");
                JsonNode choice = fields.findValue("~:jvjwdOZ1");
                JsonNode file = fields.findValue("~:nyZmZayN");
                if(file.findPath("~:value").asText().equals("apps/slack/slack_mac.talon")) {
                    ((ObjectNode) choice).put("~:value", "testing");
                }
            } else {
                itr.remove();
            }
        }
//            String resultJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
//            System.out.println(resultJson);

            String testJson = objectMapper.writeValueAsString(root);
            objectMapper.writeValue(new File("src/main/java/com/stolensugar/web/voiceCommands/tempFiles/test.json"), root);
    }

}
