package com.jh.sgs.core.enums;

import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.core.card.*;
import lombok.Getter;

public enum CardEnum {

    SHA(1,new Sha()),
    SHAN(2,new Shan()),
    TAO(3,new Tao()),
    JUE_DOU(4,null),
    GUO_HE_CHAI_QIAO(5,new GuoHeChaiQiao()),
    SHUN_SHOU_QIAN_YANG(6,new ShunShouQianYang()),
    WU_ZHONG_SHENG_YOU(7,new WuZhongShengYou()),
    WU_XIE_KE_JI(9,new WuXieKeJi()),
    NAN_MAN_RU_QIN(10,new NanManRuQin()),
    WAN_JIAN_QI_FA(11,new WanJianQiFa()),
    TAO_YUAN_JIE_YI(12,new TaoYuanJieYi()),
    WU_GU_FENG_DENG(13,new WuGuFengDeng()),
    LE_BU_SI_SHU(14,new LeBuSiShu()),
    SHAN_DIAN(15,new ShanDian()),
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

    @Getter
    private final int id;
    @Getter
    private final BaseCard baseCard;

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
