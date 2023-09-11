package com.jh.sgs.core.enums;

import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.general.*;

public enum GeneralEnum {

    Test(1, Test1General.class),
    ZHANG_FEI(2, ZhangFei.class),
    GUAN_YU(3, GuanYu.class),
    ZHAO_YUN(5, ZhaoYun.class),
    LV_MENG(10, LvMeng.class);

    public final int id;
    public final Class<? extends BaseGeneral> aClass;

    GeneralEnum(int id, Class<? extends BaseGeneral> aClass) {
        this.id = id;
        this.aClass = aClass;
    }

    public static GeneralEnum getById(int id) {
        for (GeneralEnum value : GeneralEnum.values()) {
            if (value.id == id) return value;
        }
        throw new SgsApiException("武将未实现");
    }
}
