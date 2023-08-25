package com.jh.sgs.core.pojo;

import com.jh.sgs.core.card.AddHorse;
import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.card.SubHorse;
import com.jh.sgs.core.card.TestCard;
import com.jh.sgs.core.exception.SgsApiException;

public enum CardEnum {

    Test(1,new TestCard()),
    CHI_TU(27,new SubHorse()),
    ZI_XING(28,new SubHorse()),
    DA_WAN(29,new SubHorse()),
    ZHUAN_HUANG_FEI_DIAN(30,new AddHorse()),
    DI_LU(31,new AddHorse()),
    JUE_YING(32,new AddHorse());

    public final int id;
    public final BaseCard baseCard;

    CardEnum(int id, BaseCard baseCard) {
        this.id = id;
        this.baseCard = baseCard;
    }
    public static CardEnum getById(int id) {
        for (CardEnum value : CardEnum.values()) {
            if (value.id==id) return value;
        }
        throw new SgsApiException("卡牌未实现");
    }
}
