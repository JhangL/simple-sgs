package com.jh.sgs.base.interactive.base;


import com.jh.sgs.base.exception.SgsApiException;

public interface DisCard {
    /**
     * 弃牌<br/>
     * 阶段：回合弃牌
     */
    default void disCard(int[] ids) {
        throw SgsApiException.FFWSX;
    }
}
