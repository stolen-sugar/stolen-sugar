package com.stolensugar.web.voiceCommands.baseCommands;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.stolensugar.web.model.SpokenForm;

import java.util.List;

public class GetSeedCommands {

    public static void main(String[] args){
        createJsonFile();
    }


    public static void createJsonFile() {
        try{
            List<SpokenForm> commandList = formatJsonCommandFile("talon");
            if(commandList == null) return;

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

            writer.writeValue(Paths.get("src/main/java/com/stolensugar" +
                            "/web/voiceCommands/tempFiles/commandsReadyForApi" +
                            ".json").toFile(),
                    commandList);

        } catch( Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<SpokenForm> formatJsonCommandFile(String appName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            List<SpokenForm> spokenForms = new ArrayList<>();


            BaseCommandsMapper baseCommandsMapper =
                    mapper.readValue(Paths.get("src/main/java" +
                                    "/com/stolensugar" +
                                    "/web/voiceCommands/tempFiles" +
                                    "/base_commands.json").toFile(),
                            BaseCommandsMapper.class);

            List<Map<String, Object>> commandGroups =
                    baseCommandsMapper.getCommand_groups();
            String branch = baseCommandsMapper.getBranch();
            String repo = baseCommandsMapper.getRepo_id();
            String timestamp = baseCommandsMapper.getTimestamp();
            String userId = baseCommandsMapper.getUser_id();
            for (int i=0;i<commandGroups.size();i++ ) {
                String context = (String) commandGroups.get(i).get("context");
                String file = (String) commandGroups.get(i).get("file");
                Map<String, String> actionList =
                        (Map<String, String>) commandGroups.get(i).get(
                                "commands");
                Iterator<Map.Entry<String, String>> itr = actionList.entrySet().iterator();

                while(itr.hasNext()) {
                    Map.Entry<String, String> entry = itr.next();
                    String defaultName = entry.getKey();
                    String action = entry.getValue();

                    SpokenForm spokenForm = SpokenForm.builder()
                                    .fileName(file)
                                    .context(context)
                                    .defaultName(defaultName)
                                    .action(action)
                                    .appName(appName)
                                    .build();
                    spokenForms.add(spokenForm);
                }
            }

            return spokenForms;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

