package com.jh.sgs.base.interactive.base;


import com.jh.sgs.base.exception.SgsApiException;

public interface CancelPlayCard {
    /**
     * 取消出牌<br/>
     * 阶段：回合出牌
     */
    default void cancelPlayCard() {
        throw SgsApiException.FFWSX;
    }
}
