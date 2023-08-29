package com.jh.sgs.core.pojo;

import com.jh.sgs.core.interfaces.ShowStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data()
public class Card extends ID implements Cloneable, ShowStatus {
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

    @Override
    public String toString() {
        return getId() + " @" + getStatus();
    }

    @Override
    public String getStatus() {
        if (suit == -1) {
            return "隐藏";
        }
        return nameId + SuitEnum.getByIndex(suit).getName() + num;
    }
}
