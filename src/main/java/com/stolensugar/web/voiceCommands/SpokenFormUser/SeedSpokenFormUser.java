package com.stolensugar.web.voiceCommands.SpokenFormUser;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.stolensugar.web.model.SpokenForm;
import com.stolensugar.web.model.SpokenFormUser;
import com.stolensugar.web.voiceCommands.SpokenFormCommand;
import com.stolensugar.web.voiceCommands.SpokenFormUserMapper;

import java.util.List;

public class SeedSpokenFormUser {

    public static void main(String[] args){
        createJsonFile();
    }


    public static void createJsonFile() {
        try{
            List<SpokenFormUser> commandList = formatJsonCommandFile("talon");
            if(commandList == null) return;

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

            writer.writeValue(Paths.get("src/main/java/com/stolensugar" +
                            "/web/voiceCommands/tempFiles/spokenFormUsersReadyForApi" +
                            ".json").toFile(),
                    commandList);

        } catch( Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<SpokenFormUser> formatJsonCommandFile(String appName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            List<SpokenFormUser> spokenFormUsers = new ArrayList<>();


            List<SpokenFormUserMapper> spokenFormUserMappers =
                    Arrays.asList(mapper.readValue(Paths.get("src/main/java" +
                                    "/com/stolensugar" +
                                    "/web/voiceCommands/tempFiles" +
                                    "/alt_commands.json").toFile(),
                            SpokenFormUserMapper[].class));

            for (int i=0;i<spokenFormUserMappers.size() - 1;i++ ) {
                SpokenFormUserMapper spokenFormUserMapper = spokenFormUserMappers.get(i);
                String repo_id = spokenFormUserMapper.getRepo_id();
                String user_id = spokenFormUserMapper.getUser_id();
                String lastUpdated = spokenFormUserMapper.getTimestamp();
                String branch = spokenFormUserMapper.getBranch();

                List<Map<String, Object>> command_groups =
                spokenFormUserMapper.getCommand_groups();

                for(int j=0;j<command_groups.size()-1;j++) {
                    Map<String, String> commands =
                            (Map<String, String>) command_groups.get(j).get("commands");
                    String context = (String) command_groups.get(j).get(
                            "context");
                    String file = (String) command_groups.get(j).get("file");

                    Iterator<Map.Entry<String, String>> itr = commands.entrySet().iterator();
                    while(itr.hasNext()) {
                        Map.Entry<String, String> entry = itr.next();
                        String choice = entry.getKey();
                        String command = entry.getValue();
                        String spokenFormFullName =
                                appName + "::s::" + file + "::s::" + context + "::s::" + command;

                        SpokenFormUser spokenFormUser = SpokenFormUser.builder()
                                        .userId(user_id)
                                        .spokenFormFullName(spokenFormFullName)
                                        .lastUpdated(lastUpdated)
                                        .app(appName)
                                        .file(file)
                                        .context(context)
                                        .repo(repo_id)
                                        .branch(branch)
                                        .choice(choice)
                                        .build();
                        spokenFormUsers.add(spokenFormUser);
                    }
                }
            }

            return spokenFormUsers;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}