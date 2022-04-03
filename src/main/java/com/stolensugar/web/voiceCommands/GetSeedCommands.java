package com.stolensugar.web.voiceCommands;
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


            List<SpokenFormCommand> spokenFormRoughList =
                    Arrays.asList(mapper.readValue(Paths.get("src/main/java" +
                                    "/com/stolensugar" +
                                    "/web/voiceCommands/tempFiles" +
                                    "/talon_commands.json").toFile(),
                            SpokenFormCommand[].class));

            for (int i=0;i<spokenFormRoughList.size();i++ ) {
                SpokenFormCommand spokenFormCommand = spokenFormRoughList.get(i);
                String fileName = spokenFormCommand.getFile();
                String context = spokenFormCommand.getContext();

                Iterator<Map.Entry<String, String>> itr =
                        spokenFormCommand.getCommands().entrySet().iterator();

                while(itr.hasNext()) {
                    Map.Entry<String, String> entry = itr.next();
                    String defaultName = entry.getKey();
                    String command = entry.getValue();
                    String fullName =
                            appName + "::s::" + context + "::s::" + entry.getKey();

                    SpokenForm spokenForm = SpokenForm.builder()
                                    .fileName(fileName)
                                    .context(context)
                                    .defaultName(defaultName)
                                    .command(command)
                                    .fullName(fullName)
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

