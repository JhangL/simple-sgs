package com.jh.sgs.core.pojo;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


public abstract class SupportFalse extends ID {
    private Map<String, Object> originalData = new HashMap<>();

    @Getter
    private boolean falsed;

    abstract void falseSteps(Map<String, Object> originalData);

    abstract void trueSteps(Map<String, Object> originalData);

    public void changeFalse() {
        if (!falsed) {
            falseSteps(originalData);
        }
    }

    public void backTrue() {
        if (falsed) {
            trueSteps(originalData);
        }
    }
}
