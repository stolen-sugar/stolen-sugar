package com.stolensugar.web.voiceCommands;

import com.stolensugar.web.voiceCommands.SpokenFormUser.SeedSpokenFormUser;
import com.stolensugar.web.voiceCommands.baseCommands.GetSeedCommands;

import java.util.Set;

public class GenerateTempFiles {

    public static void main(String[] args) {
        GetSeedCommands getSeedCommands = new GetSeedCommands();
        SeedSpokenFormUser seedSpokenFormUser = new SeedSpokenFormUser();


        Set<String> fileSet = getSeedCommands.createJsonFile();
        seedSpokenFormUser.createJsonFile(fileSet);

    }

}
