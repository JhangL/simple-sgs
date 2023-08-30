package com.jh.sgs.core.pojo;

import lombok.Getter;

public enum SuitEnum {
    HEIT("黑桃"), HONGT("红桃"), MH("梅花"), FP("方片");
    @Getter
    private final String name;

    SuitEnum(String name) {
        this.name = name;
    }
    public static SuitEnum getByIndex(int index) {
        return SuitEnum.values()[--index];
    }

}
