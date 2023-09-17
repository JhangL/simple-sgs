package com.jh.sgs.base.pojo;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


public abstract class SupportFalse extends ID {
    private transient  Map<String, Object> originalData = new HashMap<>();

    @Getter
    private transient  boolean falsed;

    abstract void falseSteps(Map<String, Object> originalData);

    abstract void trueSteps(Map<String, Object> originalData);

    public void changeFalse() {
        if (!falsed) {
            falseSteps(originalData);
            falsed=true;
        }
    }

    public void backTrue() {
        if (falsed) {
            trueSteps(originalData);
            originalData.clear();
            falsed=false;
        }
    }
}
