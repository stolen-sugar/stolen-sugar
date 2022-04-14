package com.stolensugar.web.voiceCommands.mochiCards;
import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import okhttp3.*;
import com.stolensugar.web.dynamodb.models.SpokenFormModel;


public class SeedMochiDeck {
    private static final String deckId = "UHiXar0A";
    private static final String templateId = "ftOIZq7E";
    private static final String nameId = "name";
    private static final String choiceId = "jvjwdOZ1";
    private static final String contextId = "Spe228xW";
    private static final String fileId = "nyZmZayN";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void main(String[] args) throws Exception {
        parseTalon();
    }

    public static void parseTalon() {
        SpokenFormModel[] spokenFormList;
        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();

        try {
            File json = new File("src/main/java/com/stolensugar/web/voiceCommands/tempFiles/UserMappingsNew.json");
            spokenFormList = mapper.readValue(json, SpokenFormModel[].class);
//            Arrays.asList(spokenFormList).forEach(System.out::println);

            for (SpokenFormModel spokenFormModel : spokenFormList) {
                MochiMapper m1 = new MochiMapper();
                Fields f1 = new Fields();
                FieldParams name = new FieldParams();
                FieldParams choice = new FieldParams();
                FieldParams context = new FieldParams();
                FieldParams file = new FieldParams();

                name.setId(nameId);
                name.setValue(spokenFormModel.getAction());

                choice.setId(choiceId);
                choice.setValue(spokenFormModel.getDefaultName());

                context.setId(contextId);
                context.setValue(spokenFormModel.getContext());

                file.setId(fileId);
                file.setValue(spokenFormModel.getFileName());

                f1.setName(name);
                f1.setChoice(choice);
                f1.setContext(context);
                f1.setFile(file);

                m1.setFields(f1);
                m1.setDeckId("UHiXar0A");
                m1.setContent("**What is talon the phrase for action below?**");
                m1.setTemplateId(templateId);

               String jsonString  = mapper.writeValueAsString(m1);

               RequestBody body = RequestBody.create(jsonString, JSON);

               Request request = new Request.Builder()
                    .url("https://app.mochi.cards/api/cards")
                    .header("Authorization", "Basic NzEwMTJhZTJhOWM3NWYxOWU1OWFjZjUzOg==")
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .post(body)
                    .build();

                Response response = client.newCall(request).execute();
                response.close();
                Thread.sleep(3);
                System.out.println(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
