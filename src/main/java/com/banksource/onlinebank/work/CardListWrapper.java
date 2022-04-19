package com.banksource.onlinebank.work;

import com.banksource.onlinebank.components.BankCard;
import com.banksource.onlinebank.data.CardData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardListWrapper {

    public List<CardData> ListWrapper(List<BankCard> bankCardList){
        List<CardData> cardDataList = new ArrayList<>();

        for (BankCard card: bankCardList) {
            CardData cardData = new CardData();

            cardData.setId(card.getId());
            cardData.setSumm(card.getSumm());
            cardData.setCode(card.getCode());
            cardData.setPaySystem(card.getPaySystem().getType());
            cardData.setStatusTime(card.getStatusTime());

            cardDataList.add(cardData);
        }
        return cardDataList;
    }
}
