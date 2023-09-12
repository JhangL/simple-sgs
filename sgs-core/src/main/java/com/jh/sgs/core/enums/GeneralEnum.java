package com.jh.sgs.core.enums;

import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.general.*;

public enum GeneralEnum {

    LIU_BEI(1, LiuBei.class),
    ZHANG_FEI(2, ZhangFei.class),
    GUAN_YU(3, GuanYu.class),
    ZHU_GE_LIANG(4, ZhuGeLiang.class),
    ZHAO_YUN(5, ZhaoYun.class),
    GAN_NING(9, GanNing.class),
    LV_MENG(10, LvMeng.class),
    HUANG_GAI(11, HuangGai.class),
    ZHEN_JI(22, ZhenJi.class);

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
        throw new SgsRuntimeException("武将未实现");
    }
}
