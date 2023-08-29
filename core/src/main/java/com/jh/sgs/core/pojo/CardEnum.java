package com.jh.sgs.core.pojo;

import com.jh.sgs.core.card.*;
import com.jh.sgs.core.exception.SgsApiException;

public enum CardEnum {

    GUO_HE_CHAI_QIAO(5,new GuoHeChaiQiao()),
    SHUN_SHOU_QIAN_YANG(6,new ShunShouQianYang()),
    WU_ZHONG_SHENG_YOU(7,new WuZhongShengYou()),
    ZHU_GE_LIAN_NU(16,new ZhuGeLianNu()),
    QING_HONG_JIAN(17,new QingHongJian()),
    CI_XIONG_SHUANG_GU_JIAN(18,new CiXiongShuangGuJian()),
    GUAN_SHI_FU(19,new GuanShiFu()),
    QING_LONG_YAN_YUE_DAO(20,new QingLongYanYueDao()),
    ZHANG_BAO_SHE_MAO(21,new ZhangBaoSheMao()),
    FANG_TIAN_HUA_JI(22,new FangTianHuaJi()),
    QI_LIN_GONG(23,new QiLinGong()),
    HAN_BING_JIAN(24,new HanBingJian()),
    BA_GUA_ZHEN(25,new BaGuaZhen()),
    REN_WANG_DUN(26,new RenWangDun()),
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
