package com.stolensugar.web.activity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stolensugar.web.voiceCommands.Mochi.*;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.requests.GetMochiDeckRequest;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

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
    public String execute (final GetMochiDeckRequest request) {

        // Retrieve list of spoken forms from DB for the requested user and app
        List<SpokenFormUserModel> spokenFormUsersDAO = spokenFormUserDao.getByUser(request.getUserId(), request.getApp());

        // Copy list to thread safe and modifiable collection type
        List<SpokenFormUserModel> spokenFormUsers = new CopyOnWriteArrayList<>(spokenFormUsersDAO);

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

        if(request.getFile() != null) {
            // Remove the spoken forms which are not for the requested file
            spokenFormUsers.removeIf(spokenFormUserModel -> !Objects.equals(spokenFormUserModel.getFile(), request.getFile()));

            // Remove the cards which are not for the requested file
            cardList.removeIf(card -> !Objects.equals(card.getFields().getFile().getValue(), request.getFile()));

            // For each card, find the spoken form that matches and assign the users specified choice to that card
            for(SingleCard card : cardList) {
                for(SpokenFormUserModel spokenFormUserModel : spokenFormUsers) {
                    if (card.getFields().getName().getValue().equals(spokenFormUserModel.getAction())) {
                        card.getFields().getChoice().setValue(spokenFormUserModel.getChoice());
                        //Remove the spoken form from the users list of spoken forms if a match is found
                        spokenFormUsers.remove(spokenFormUserModel);
                    }
                }
            }
        // If no file was specified in request, find and replace all the users custom spoken forms regardless of file
        } else {
            for(SingleCard card : cardList) {
                for(SpokenFormUserModel spokenFormUserModel : spokenFormUsers) {
                    if (card.getFields().getName().getValue().equals(spokenFormUserModel.getAction()) &&
                            card.getFields().getFile().getValue().equals(spokenFormUserModel.getFile())) {

                        card.getFields().getChoice().setValue(spokenFormUserModel.getChoice());
                        // Remove the spoken form from the users list of spoken forms if a match is found
                        spokenFormUsers.remove(spokenFormUserModel);
                    }
                }
            }
        }

        // If a user has a spoken form that wasn't able to find a match from the master card list, build and add it
        if(spokenFormUsers.size() > 0) {
            for(SpokenFormUserModel spokenFormUserModel : spokenFormUsers) {
                SingleCard card = cardList.get(0);
                card.getFields().getName().setValue(spokenFormUserModel.getAction());
                card.getFields().getChoice().setValue(spokenFormUserModel.getChoice());
                card.getFields().getContext().setValue(spokenFormUserModel.getContext());
                card.getFields().getFile().setValue(spokenFormUserModel.getFile());
                cardList.add(card);
            }
        }

        // Set the deck card list to the updated list
        mochiDeck.getDecks().get(0).getCards().setList(cardList);

        // Return mochi deck json
        try {
            return mapper.writeValueAsString(mochiDeck);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Failed to write MochiDeck object to String!");
        }

        return "Failed to build deck";
    }


}
