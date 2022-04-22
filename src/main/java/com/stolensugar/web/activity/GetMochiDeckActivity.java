package com.stolensugar.web.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stolensugar.web.voiceCommands.Mochi.*;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.requests.GetMochiDeckRequest;
import org.apache.commons.lang3.RandomStringUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetMochiDeckActivity {
    private SpokenFormUserDao spokenFormUserDao;

    /**
     * Instantiates a new CreateSpokenFormActivity object.
     *
     * @param spokenFormUserDao Dao to access the spokenForm table.
     */
    @Inject
    public GetMochiDeckActivity(SpokenFormUserDao spokenFormUserDao) {

        this.spokenFormUserDao = spokenFormUserDao;
    }

    /**
     * Creates a JSON file of spoken forms from a users customized forms and defaults to be
     * used as flashcards in mochi (https://mochi.cards/)
     *
     * @param request Request object containing the user ID, app (optional), and file (optional)
     * @return JSON formatted String
     */
    public MochiDeck execute (final GetMochiDeckRequest request) {

        // Retrieve list of spoken forms from DB for the requested user and app
        List<SpokenFormUserModel> spokenFormUsers = spokenFormUserDao.getByUser(request.getUserId(), request.getApp());

        Map<String, SpokenFormUserModel> spokenFormUserByAction;

        ObjectMapper mapper = new ObjectMapper();

        MochiDeck mochiDeck = null;

        // Read in mochi master deck json to a MochiDeck object
        try {
            mochiDeck = mapper.readValue(Paths.get("src/main/java" +
                                    "/com/stolensugar" +
                                    "/web/voiceCommands/tempFiles" +
                                    "/mochiMasterTest.json").toFile(),
                                    MochiDeck.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to access master file!");
        }

        // Get the list of cards from the master deck
        List<SingleCard> cardList = mochiDeck.getDecks().get(0).getCards().getList();

        // group spokenFormUsers by Action
        spokenFormUserByAction =
                spokenFormUsers.stream().filter(x -> x.getFile().equals(request.getFile()))
                        .collect(Collectors.toMap(SpokenFormUserModel::getAction,
                                Function.identity(), (existing,
                                replacement) -> existing));


        // Remove the cards which are not for the requested file
        cardList.removeIf(card -> !Objects.equals(card.getFields().getFile().getValue(), request.getFile()));

        //Set deck name to refer to file for easier ID
        mochiDeck.getDecks().get(0).setName(request.getFile() + " Spoken Phrases");

        // For each card, find the spoken form that matches and assign the users specified choice to that card
        for(SingleCard card : cardList) {
            String action = card.getFields().getName().getValue();
            if(spokenFormUserByAction.containsKey(action)) {
                String customChoice =
                        spokenFormUserByAction.get(action).getChoice();

                card.getFields().getChoice().setValue(customChoice);
                spokenFormUserByAction.remove(action);
            }
        }

//         If a user has a spoken form that wasn't able to find a match from the master card list, build and add it
        if(spokenFormUserByAction.values().size() > 0) {
            for(SpokenFormUserModel spokenFormUserModel :
                    spokenFormUserByAction.values()) {
                SingleCard card = cardList.get(0);
                card.getFields().getName().setValue(spokenFormUserModel.getAction());
                card.getFields().getChoice().setValue(spokenFormUserModel.getChoice());
                card.getFields().getContext().setValue(spokenFormUserModel.getContext());
                card.getFields().getFile().setValue(spokenFormUserModel.getFile());
                cardList.add(card);
            }
        }
        // Generate new deck id so there are no duplicates
        String newDeckId = "~:" + RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        mochiDeck.getDecks().get(0).setId(newDeckId);

        // Set every card's deck id to the new deck id
        for(SingleCard card : cardList) {
            card.setDeckId(newDeckId);
        }

        // Set the deck card list to the updated list
        mochiDeck.getDecks().get(0).getCards().setList(cardList);


        // Return mochi deck
        return mochiDeck;
    }


}
