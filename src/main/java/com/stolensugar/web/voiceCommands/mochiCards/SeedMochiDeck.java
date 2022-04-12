package com.stolensugar.web.voiceCommands.mochiCards;
import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.SpokenFormUser;


public class SeedMochiDeck {


    public static void main(String[] args) throws Exception {
        parseTalon();
    }

    public static void parseTalon() {
        SpokenFormUserModel[] spokenFormUserList;
        ObjectMapper mapper = new ObjectMapper();
        try {
            File json = new File("src/main/java/com/stolensugar/web/voiceCommands/tempFiles/userMappings.json");
            spokenFormUserList = mapper.readValue(json, SpokenFormUserModel[].class);
            Arrays.asList(spokenFormUserList).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
