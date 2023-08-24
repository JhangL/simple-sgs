package com.jh.sgs.pojo;

import com.jh.sgs.exception.SgsApiException;
import com.jh.sgs.general.BaseGeneral;
import com.jh.sgs.general.Test1General;

public enum GeneralEnum {

    Test(1, Test1General.class);

    public final int id;
    public final Class<? extends BaseGeneral> aClass;

    GeneralEnum(int id, Class<? extends BaseGeneral> aClass) {
        this.id = id;
        this.aClass = aClass;
    }
    public static GeneralEnum getById(int id) {
        for (GeneralEnum value : GeneralEnum.values()) {
            if (value.id==id) return value;
        }
        throw new SgsApiException("武将未实现");
    }
}
