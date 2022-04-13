package com.stolensugar.web.voiceCommands.baseCommands;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.stolensugar.web.model.SpokenForm;

import java.util.List;
import java.util.Set;

public class GetSeedCommands {
    Set<String> fileSet = new HashSet<>();



    public Set<String> createJsonFile() {
        try{
            List<SpokenForm> commandList = formatJsonCommandFile("talon");
            if(commandList == null) return null;

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

            writer.writeValue(Paths.get("src/main/java/com/stolensugar" +
                            "/web/voiceCommands/tempFiles/commandsReadyForApi" +
                            ".json").toFile(),
                    commandList);
            return fileSet;
        } catch( Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public List<SpokenForm> formatJsonCommandFile(String appName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            List<SpokenForm> spokenForms = new ArrayList<>();
            Map<String,SpokenForm> commandCheck = new HashMap<>();

            BaseCommandsMapper baseCommandsMapper =
                    mapper.readValue(Paths.get("src/main/java" +
                                    "/com/stolensugar" +
                                    "/web/voiceCommands/tempFiles" +
                                    "/base_commands.json").toFile(),
                            BaseCommandsMapper.class);

            List<Map<String, Object>> commandGroups =
                    baseCommandsMapper.getCommand_groups();
            List<String> bb = new ArrayList<>();
            bb.add("love");
            for (int i=0;i<commandGroups.size();i++ ) {
                String context = (String) commandGroups.get(i).get("context");
                String file = (String) commandGroups.get(i).get("file");
                fileSet.add(file);
                Map<String, String> actionList =
                        (Map<String, String>) commandGroups.get(i).get(
                                "commands");
                Iterator<Map.Entry<String, String>> itr = actionList.entrySet().iterator();

                while(itr.hasNext()) {
                    Map.Entry<String, String> entry = itr.next();
                    String defaultName = entry.getKey();
                    String action = entry.getValue();
                    SpokenForm spokenForm;
                    if(defaultName.equals("`") || defaultName.equals(",")) {
                        continue;
                    }
                    if (commandCheck.containsKey(file + action)) {
                        spokenForm = commandCheck.get(file + action);
                        Set<String> currentAlts = spokenForm.getAlternatives();
                        if(currentAlts == null) {
                            currentAlts = new HashSet<>();
                        }
                        currentAlts.add(defaultName);
                        spokenForm.setAlternatives(currentAlts);
                    } else {
                        spokenForm = SpokenForm.builder()
                                .fileName(file)
                                .context(context)
                                .defaultName(defaultName)
                                .action(action)
                                .appName(appName)
                                .alternatives(null)
                                .build();
                        spokenForms.add(spokenForm);
                    }
                    commandCheck.put(file + action, spokenForm);
                }
            }

            return spokenForms;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

