package com.jh.sgs.core.interactive.base;

import com.jh.sgs.core.pojo.Card;

import java.util.List;

public interface DecideCard {
    /**
     * 查看判定牌<br/>
     * 阶段：顺手牵羊执行
     */
    List<Card> decideCard();
}
