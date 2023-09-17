package com.jh.sgs.base.interactive.base;


import com.jh.sgs.base.exception.SgsApiException;

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
