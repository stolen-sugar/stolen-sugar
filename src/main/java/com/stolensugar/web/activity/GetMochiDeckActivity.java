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
        List<SpokenFormUserModel> spokenFormUsers = spokenFormUserDao.getByUser(request.getUserId(), request.getApp());
        MochiDeck mochiDeck = createMochiDeckFromFile();
        mochiDeck.getDecks().get(0).setName(request.getFile() + " Spoken Phrases");
        List<SingleCard> cardList = mochiDeck.getDecks().get(0).getCards().getList();
        Map<String, SpokenFormUserModel> spokenFormUsersByAction = groupByAction(spokenFormUsers, request.getFile());
        cardList.removeIf(card -> !Objects.equals(card.getFields().getFile().getValue(), request.getFile()));
        assignCustomSpokenForms(cardList, spokenFormUsersByAction);
        createOrphanCards(cardList, spokenFormUsersByAction);
        generateNewDeckId(mochiDeck, cardList);

        return mochiDeck;
    }

    /**
     * Filter SpokenFormUserModels that contain a given file, then group the
     * remaining by action.
     * @param spokenFormUsers The list of custom spoken forms for a single user
     * @param file the file used for filtering
    */
    private Map<String, SpokenFormUserModel> groupByAction(
            List<SpokenFormUserModel> spokenFormUsers,
            String file)
    {
      return spokenFormUsers.stream()
              .filter(x -> x.getFile().equals(file))
              .collect(Collectors.toMap(
                      SpokenFormUserModel::getAction,
                      Function.identity(),
                      (existing, replacement) -> existing)
              );
    }

    /**
     * For each card, replace the spoken form if the user is using an
     * alternative.
     * @param cardList List of cards which contain default values for each
     *                 spoken form
     * @param spokenFormUserByAction List of a user's custom spoken forms
     */
    private void assignCustomSpokenForms(
            List<SingleCard> cardList,
            Map<String, SpokenFormUserModel> spokenFormUserByAction)
        {

        cardList.forEach( card -> {
            String action = card.getFields().getName().getValue();
            if(spokenFormUserByAction.containsKey(action)) {
                String customChoice =
                        spokenFormUserByAction.get(action).getChoice();

                card.getFields().getChoice().setValue(customChoice);
                spokenFormUserByAction.remove(action);
            }
        });
    }

    /**
     * Create a new card for each spoken form that was not included in the
     * master card list
     * @param cardList List of cards which contain default values for each
     *                 spoken form
     * @param spokenFormUserByActions List of a user's custom spoken forms
     */
    private void createOrphanCards(List<SingleCard> cardList,
            Map<String, SpokenFormUserModel> spokenFormUserByActions) {
        if(spokenFormUserByActions.size() > 0) {
            for(SpokenFormUserModel spokenFormUserModel :
                    spokenFormUserByActions.values()) {
                SingleCard card = cardList.get(0);
                card.getFields().getName().setValue(spokenFormUserModel.getAction());
                card.getFields().getChoice().setValue(spokenFormUserModel.getChoice());
                card.getFields().getContext().setValue(spokenFormUserModel.getContext());
                card.getFields().getFile().setValue(spokenFormUserModel.getFile());
                cardList.add(card);
            }
        }
    }

    /**
     * Create MochiDeck object from master file
     */
    private MochiDeck createMochiDeckFromFile() {
        MochiDeck mochiDeck = null;
        ObjectMapper mapper = new ObjectMapper();
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

        return mochiDeck;
    }

    /**
     * Generate new Deck id and attach each card to the deck
     * @param mochiDeck
     * @param cardList
     */
    private void generateNewDeckId(MochiDeck mochiDeck, List<SingleCard> cardList) {
        String newDeckId = "~:" + RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        mochiDeck.getDecks().get(0).setId(newDeckId);

        for(SingleCard card : cardList) {
            card.setDeckId(newDeckId);
        }
    }
}
