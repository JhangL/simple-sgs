package com.jh.sgs.core;

import com.alibaba.fastjson2.JSON;
import com.jh.sgs.core.interfaces.ShowStatus;
import com.jh.sgs.core.pojo.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CardManage implements ShowStatus {
    List<Card> usingCards;
    List<Card> usedCards;

    CardManage(List<Card> cards) {
        usingCards = new LinkedList<>();
        usingCards.addAll(cards);
        usedCards = new ArrayList<>();
        Collections.shuffle(usingCards);
    }


    void shuffle() {
        Collections.shuffle(usedCards);
        usingCards.addAll(usedCards);
        usedCards.clear();
    }

    List<Card> obtainCard(int num) {
        if (usingCards.size() < num) shuffle();
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            cards.add(usingCards.remove(0));
        }
        return cards;
    }

    void recoveryCard(List<Card> cards){
        usedCards.addAll(cards);
    }

    @Override
    public String getStatus() {
        return "{" +
                "\"usingCards\":" + JSON.toJSONString(usingCards)  +
                ", \"usedCards\":" + JSON.toJSONString(usedCards) +
                '}';
    }

}
