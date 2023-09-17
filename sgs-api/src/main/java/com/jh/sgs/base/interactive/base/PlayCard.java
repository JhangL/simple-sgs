package com.jh.sgs.base.interactive.base;


import com.jh.sgs.base.exception.SgsApiException;

public interface PlayCard {
    /**
     * 出牌<br/>
     * 阶段：回合出牌
     */
    default void playCard(int id) {
        throw SgsApiException.FFWSX;
    }
}
