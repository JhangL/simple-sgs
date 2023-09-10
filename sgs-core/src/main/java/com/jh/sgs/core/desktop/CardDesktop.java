package com.jh.sgs.core.desktop;

import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.pojo.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class CardDesktop extends Desktop{

    @Getter
    private Card card;
    @Getter
    private boolean cardUsed;
    @Getter
    private List<Card> processCards = new ArrayList<>();

    public CardDesktop(int player, Card card) {
        super(player);
        this.card = card;
    }


    public void useCard() {
        if (cardUsed) throw new SgsRuntimeException("desktop牌已使用");
        else cardUsed = true;
    }
}
