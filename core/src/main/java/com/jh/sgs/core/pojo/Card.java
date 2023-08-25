package com.jh.sgs.core.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Card extends ID implements Cloneable{
    private int nameId;
    private String num;
    private int suit;

    @Override
    public Card clone() {
        try {
            return (Card) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
