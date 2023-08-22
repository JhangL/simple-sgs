package com.jh.sgs.core;

import com.jh.sgs.pojo.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CardManage {
    List<Card> usingCards;
    List<Card> usedCards;

    public CardManage(List<Card> cards) {
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
}
