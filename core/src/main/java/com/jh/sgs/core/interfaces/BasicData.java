package com.jh.sgs.core.interfaces;

import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.General;

import java.util.List;
import java.util.Map;

/**
 * 基本数据获取接口
 */
public interface BasicData {


    /**
     * 获取卡牌数据
     * @return 卡牌数据
     */
    List<Card> getCards();

    /**
     * 获取身份分配数据
     * @param num 人数
     * @return 《字典数据（zhu,zhong,fan,nei||人数）》
     */
    Map<String,Integer> getIdentity(int num);

    /**
     * 获取武将
     * @return 武将
     */
    List<General> getGenerals();
}
