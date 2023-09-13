package com.jh.sgs.core.pojo;

import com.jh.sgs.core.enums.SuitEnum;
import com.jh.sgs.core.interfaces.ShowStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class Card extends SupportFalse implements Cloneable, ShowStatus {

    private int nameId;
    private String num;
    private int suit;
    private String name;
    private String remark;

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
        return getId() + " " + getStatus();
    }

    @Override
    public String getStatus() {
        if (suit == -1) {
            return "隐藏";
        }
        return SuitEnum.getByIndex(suit).getName() + num + name;
    }

    @Override
    void falseSteps(Map<String, Object> originalData) {
        originalData.put("nameId", nameId);
        originalData.put("num", num);
        originalData.put("suit", suit);
        originalData.put("name", name);
        originalData.put("remark", remark);
    }

    @Override
    void trueSteps(Map<String, Object> originalData) {
        nameId = (int) originalData.get("nameId");
        num = (String) originalData.get("num");
        suit = (int) originalData.get("suit");
        name = (String) originalData.get("name");
        remark = (String) originalData.get("remark");
    }
}
