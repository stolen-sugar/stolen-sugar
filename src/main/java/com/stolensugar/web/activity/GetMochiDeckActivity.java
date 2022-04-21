package com.stolensugar.web.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stolensugar.web.voiceCommands.Mochi.*;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.requests.GetMochiDeckRequest;

import javax.inject.Inject;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class GetMochiDeckActivity {
    private SpokenFormUserDao spokenFormUserDao;

    @Inject
    public GetMochiDeckActivity(SpokenFormUserDao spokenFormUserDao) {

        this.spokenFormUserDao = spokenFormUserDao;
    }

    public String execute (final GetMochiDeckRequest request) throws Exception {

        if(request.getApp() == null || Objects.equals(request.getApp(), "talon")) {
            request.setApp("talon");
        }

        List<SpokenFormUserModel> spokenFormUsersDAO = spokenFormUserDao.getByUser(request.getUserId(), request.getApp());
        List<SpokenFormUserModel> spokenFormUsers = new CopyOnWriteArrayList<>(spokenFormUsersDAO);

        ObjectMapper mapper = new ObjectMapper();

        MochiDeck mochiDeck = mapper.readValue(Paths.get("src/main/java" +
                                "/com/stolensugar" +
                                "/web/voiceCommands/tempFiles" +
                                "/mochiMasterTest.json").toFile(),
                                MochiDeck.class);

        List<SingleCard> cardList = mochiDeck.getDecks().get(0).getCards().getList();

        if(request.getFile() != null) {
            spokenFormUsers.removeIf(spokenFormUserModel -> !Objects.equals(spokenFormUserModel.getFile(), request.getFile()));

            cardList.removeIf(card -> !Objects.equals(card.getFields().getFile().getValue(), request.getFile()));

            for(SingleCard card : cardList) {
                for(SpokenFormUserModel spokenFormUserModel : spokenFormUsers) {
                    if (card.getFields().getName().getValue().equals(spokenFormUserModel.getAction())) {
                        card.getFields().getChoice().setValue(spokenFormUserModel.getChoice());
                        spokenFormUsers.remove(spokenFormUserModel);
                    }
                }
            }

            if(spokenFormUsers.size() > 0) {
                for(SpokenFormUserModel spokenFormUserModel : spokenFormUsers) {
                    SingleCard card = cardList.get(0);
                    card.getFields().getName().setValue(spokenFormUserModel.getAction());
                    card.getFields().getChoice().setValue(spokenFormUserModel.getChoice());
                    cardList.add(card);
                }
            }

            mochiDeck.getDecks().get(0).getCards().setList(cardList);
        }


        return mapper.writeValueAsString(mochiDeck);
    }


}
