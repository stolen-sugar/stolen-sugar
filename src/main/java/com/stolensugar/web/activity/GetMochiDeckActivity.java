package com.stolensugar.web.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stolensugar.web.controller.mappers.Mochi.*;
import com.stolensugar.web.dao.SpokenFormUserDao;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import com.stolensugar.web.model.requests.GetMochiDeckRequest;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GetMochiDeckActivity {
    private SpokenFormUserDao spokenFormUserDao;
    @Inject
    public GetMochiDeckActivity(SpokenFormUserDao spokenFormUserDao) {

        this.spokenFormUserDao = spokenFormUserDao;
    }

    public String execute (final GetMochiDeckRequest request) throws Exception {

        if(request.getApp() == null || request.getApp() == "talon") {
            request.setApp("talon");
        }

        List<SpokenFormUserModel> spokenFormUsers = spokenFormUserDao.getByUser(request.getUserId(), request.getApp());

        ObjectMapper objectMapper = new ObjectMapper();

        MochiDeck mochiDeck = new MochiDeck();
        Deck deck = new Deck();
        Cards cards = new Cards();

        Templates templates = new Templates();
        SingleTemplate ssTemplate = new SingleTemplate();
        TemplateFields templateFields = new TemplateFields();
        TemplateFieldParams templateName = new TemplateFieldParams();
        TemplateFieldParams templateChoice = new TemplateFieldParams();
        TemplateFieldParams templateContext = new TemplateFieldParams();
        TemplateFieldParams templateFile = new TemplateFieldParams();

        templateName.setId("~:name");
        templateName.setName("action");
        templateName.setPos("a");

        templateChoice.setId("~:jvjwdOZ1");
        templateChoice.setName("phrase");
        templateChoice.setPos("m");

        templateContext.setId("~:Spe228xW");
        templateContext.setName("context");
        templateContext.setPos("s");

        templateFile.setId("~:nyZmZayN");
        templateFile.setName("file");
        templateFile.setPos("v");

        templateFields.setName(templateName);
        templateFields.setChoice(templateChoice);
        templateFields.setContext(templateContext);
        templateFields.setFile(templateFile);
        List<SingleTemplate> ssTemplateList = new ArrayList<>();
        ssTemplate.setFields(templateFields);
        ssTemplateList.add(ssTemplate);

        templates.setList(ssTemplateList);

        mochiDeck.setTemplates(templates);

        List<SingleCard> cardList = new ArrayList<>();

        for(SpokenFormUserModel spokenFormUserModel : spokenFormUsers) {
            SingleCard singleCard = new SingleCard();
            Fields fields = new Fields();
            FieldParams name = new FieldParams();
            FieldParams choice = new FieldParams();
            FieldParams context = new FieldParams();
            FieldParams file = new FieldParams();

            name.setId("~:name");
            name.setValue(spokenFormUserModel.getAction());

            choice.setId("~:jvjwdOZ1");
            choice.setValue(spokenFormUserModel.getChoice());

            context.setId("~:Spe228xW");
            context.setValue(spokenFormUserModel.getContext());

            file.setId("~:nyZmZayN");
            file.setValue(spokenFormUserModel.getFile());

            fields.setName(name);
            fields.setChoice(choice);
            fields.setContext(context);
            fields.setFile(file);

            singleCard.setName(spokenFormUserModel.getAction());
            singleCard.setFields(fields);
            cardList.add(singleCard);
        }

        cards.setList(cardList);
        deck.setCards(cards);

        List<Deck> deckList = new ArrayList<>();
        deckList.add(deck);



        mochiDeck.setDecks(deckList);

        objectMapper.writeValueAsString(mochiDeck);



        return objectMapper.writeValueAsString(mochiDeck);
    }


}
