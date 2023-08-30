package com.jh.sgs.core.interactive.base;

import com.jh.sgs.core.pojo.Card;

import java.util.List;

public interface TargetCard {
    /**
     * 查看目标牌<br/>
     * 阶段：五谷丰登执行
     */
    List<Card> targetCard();
}
