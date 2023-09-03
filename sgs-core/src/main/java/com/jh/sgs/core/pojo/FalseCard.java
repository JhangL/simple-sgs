package com.jh.sgs.core.pojo;

import lombok.Getter;


public class FalseCard extends Card{
    @Getter
    Card card;
    public FalseCard(Card card) {
        this.card=card;
        setName(card.getName());
        setNum(card.getNum());
        setSuit(card.getSuit());
        setNameId(card.getNameId());
        setRemark(card.getRemark());
        setId(card.getId());
    }

    @Override
    public String toString() {
        return super.toString()+"("+card.toString()+")";
    }
}


//todo  处理把假牌变回去