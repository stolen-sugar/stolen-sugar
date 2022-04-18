package com.stolensugar.web.voiceCommands.mochiCards;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;

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
        if(list.isArray()) {
            for(final JsonNode item : list) {
                if(item.findPath("~:name").asText().equals("\"module \"")) {
                   JsonNode fields = item.findValue("~:fields");
                   JsonNode choice = fields.findValue("~:jvjwdOZ1");
                    ((ObjectNode) choice).put("~:value", "Testing");
//                   String resultJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(fields);
//                   System.out.println(resultJson);
                }
            }
//            String resultJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
//            System.out.println(resultJson);

            String testJson = objectMapper.writeValueAsString(root);
            objectMapper.writeValue(new File("src/main/java/com/stolensugar/web/voiceCommands/tempFiles/test.json"), root);
        }

    }
}
