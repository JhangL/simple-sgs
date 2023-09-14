package com.jh.sgs.core;

import com.alibaba.fastjson2.JSON;
import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.interfaces.ShowStatus;
import com.jh.sgs.core.pojo.Card;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;

public class CardManage implements ShowStatus {
    @Getter
    List<Card> usingCards;
    List<Card> usedCards;
    Map<Integer, Map<String, String>> cardParameter;

    CardManage(List<Card> cards, Map<Integer, Map<String, String>> cardParameter) {
        usingCards = new LinkedList<>();
        usingCards.addAll(cards);
        usedCards = new ArrayList<>();
        this.cardParameter = cardParameter;
        for (Card usingCard : usingCards) {
            Map<String, String> stringStringMap = cardParameter.get(usingCard.getNameId());
            usingCard.setName(stringStringMap.get("name"));
            usingCard.setRemark(stringStringMap.get("remark"));
        }
        Collections.shuffle(usingCards);
    }

    void shuffle() {
        Collections.shuffle(usedCards);
        usingCards.addAll(usedCards);
        usedCards.clear();
    }

    public List<Card> obtainCard(int num) {
        if (usingCards.size() < num) shuffle();
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            cards.add(usingCards.remove(0));
        }
        return cards;
    }

    public void recoveryCard(List<Card> cards) {
        for (Card card : cards) {
            recoveryCard(card);
        }
    }

    public void recoveryCard(@NonNull Card card) {
        if (card.isFalsed()) card.backTrue();
        usedCards.add(card);
    }

    public static BaseCard getBaseCard(Card card) {
        return CardEnum.getById(card.getNameId()).getBaseCard();
    }

    public int getUsedCardsNum() {
        return usedCards.size();
    }

    public int getUsingCardsNum() {
        return usingCards.size();
    }

    public  Map<String, String> getCardParameter(int nameId) {
        return cardParameter.get(nameId);
    }

    @Override
    public String getStatus() {
        return "{" +
                "\"usingCards\":" + JSON.toJSONString(usingCards) +
                ", \"usedCards\":" + JSON.toJSONString(usedCards) +
                '}';
    }

}
