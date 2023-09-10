package com.jh.sgs.core.interactive.base;

import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.Card;

import java.util.List;

public interface HandCard {
    /**
     * 查看手牌<br/>
     * 阶段：回合弃牌，回合出牌，顺手牵羊执行
     */
    default List<Card> handCard() {
        throw SgsApiException.FFWSX;
    }
}
