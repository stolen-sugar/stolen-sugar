package com.stolensugar.web.voiceCommands.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.*;
import com.stolensugar.web.model.User;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeedUsers {
    private static final List<User> list = new ArrayList<>();
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build();

    public static void main(String[] args) {
        int counter = 10;
        for(int i = 1;i < counter; i++) {
            String json = callApi(i);
            if(json != null) {
                List<User> list = convertApiCallToList(json);
                if (list.size() > 0) {
                    createJsonFile(list);
                }
                continue;
            }
            return;
        }
    }

    public static String callApi(Integer pageNum) {
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/knausj85/knausj_talon" +
                        "/forks?per_page=100&page=" + Integer.toString(pageNum))
                .header("accept", "application/vnd.github.v3+json")
                .header("Authorization", "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")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string() + " testing";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> convertApiCallToList(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<SeedUserMapper> userMapper =
                    Arrays.asList(mapper.readValue(json,
                    SeedUserMapper[].class));
            for(int i=0;i< userMapper.size();i++) {
                list.add(
                        User.builder()
                                .id(userMapper.get(i).getOwner().get("id"))
                                .imageavatarUrl(userMapper.get(i).getOwner().get("avatar_url"))
                                .name(userMapper.get(i).getOwner().get("login"))
                                .talonUrl(userMapper.get(i).getSsh_url())
                                .talonLastPush(userMapper.get(i).getPushed_at())
                                .build()
                );
            }
            System.out.println(list.size());
            return list;
        } catch( Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static void createJsonFile(List<User> list) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());


            writer.writeValue(Paths.get("src/main/java/com/stolensugar" +
                            "/web/voiceCommands/tempFiles/usersReadyForApi" +
                            ".json").toFile(),
                    list);

        } catch( Exception ex) {
            ex.printStackTrace();
        }
    }
}
