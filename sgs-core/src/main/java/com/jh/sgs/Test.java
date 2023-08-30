package com.jh.sgs;

import com.jh.sgs.core.Util;
import com.jh.sgs.core.pojo.Card;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        ArrayList<Card> cards = new ArrayList<>();
        Card card = new Card();
        cards.add(card);
        card.setId(1);
        List<Card> cards1 = Util.collectionCloneToList(cards);
        cards1.get(0).setId(3);
        System.out.println(cards.get(0)==cards1.get(0));
        System.out.println(cards.containsAll(cards1));
    }
}
