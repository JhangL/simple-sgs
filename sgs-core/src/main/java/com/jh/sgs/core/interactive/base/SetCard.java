package com.jh.sgs.core.interactive.base;

import com.jh.sgs.core.exception.SgsApiException;

public interface SetCard {
    /**
     * 选择牌<br/>
     * 阶段：顺手牵羊执行，五谷丰登执行
     * @param id
     */
    default void setCard(int id) {
        throw SgsApiException.FFWSX;
    }
}
