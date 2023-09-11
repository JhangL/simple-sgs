package com.jh.sgs.core.enums;

/**
 * 交互枚举，在com.jh.sgs.core.interactive包下有同名接口，用于分辨交互事件
 */
public enum InteractiveEnum {
    /**
     * 选择武将
     */
    XZYX,
    /**
     * 出牌
     */
    CP,
    /**
     * 弃牌
     */
    QP,
    /**
     * 选择目标
     */
    XZMB,
    /**
     * 过河拆桥顺手牵羊选择牌
     */
    GHCQSSQYXZP,

    /**
     * 五谷丰登选择牌
     */
    WGFDXZP,

    /**
     * 技能出牌
     */
    JNCP,

    /**
     * 是否
     */
    TOF,
    /**
     * 贯石斧
     */
    GSF,

    /**
     * 选择牌（给定目标选择）
     */
    XZP,
    /**
     * 技能选择牌（手牌，装备牌选择）
     */
    JNXZP,

}
