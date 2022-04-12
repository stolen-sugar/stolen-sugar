package com.stolensugar.web.voiceCommands.mochiCards;
import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stolensugar.web.dynamodb.models.SpokenFormModel;
import org.apache.commons.lang3.RandomStringUtils;


public class SeedMochiDeck {


    public static void main(String[] args) throws Exception {
        parseTalon();
    }

    public static void parseTalon() {
        SpokenFormModel[] spokenFormList;
        ObjectMapper mapper = new ObjectMapper();
        try {
            File json = new File("src/main/java/com/stolensugar/web/voiceCommands/tempFiles/UserMappingsNew.json");
            spokenFormList = mapper.readValue(json, SpokenFormModel[].class);
            Arrays.asList(spokenFormList).forEach(System.out::println);
            SpokenFormModel spokenFormModel = spokenFormList[2];
//            Request request = new Request.Builder()
//                    .url("https://app.mochi.cards/api/cards")
//                    .header("Authorization", "Basic NzEwMTJhZTJhOWM3NWYxOWU1OWFjZjUzOg==")
//                    .bo
            MochiMapper m1 = new MochiMapper();
            Fields f1 = new Fields();
            Name n1 = new Name();
            Choice123 c1 = new Choice123();

            n1.setId("name");
            n1.setValue(spokenFormModel.getAction());
            c1.setId(RandomStringUtils.randomAlphanumeric(8));
            c1.setValue(spokenFormModel.getDefaultName());

            f1.setName(n1);
            f1.setChoice123(c1);
            m1.setFields(f1);
            m1.setDeckId("33O2fyWT");
            m1.setContent("Testing");

            mapper.writeValue(new File("src/main/java/com/stolensugar/web/voiceCommands/tempFiles/test.json"), m1);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
