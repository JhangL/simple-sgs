package com.jh.sgs;

import com.jh.sgs.core.pojo.Card;

import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Set<Card> cards = new HashSet<>();
        Card card = new Card();
        cards.add(card);
        card.setId(2);
        cards.remove(card);
        System.out.println(cards);
    }
}
