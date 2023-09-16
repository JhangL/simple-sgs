package com.jh.sgs.base.interactive.base;


import com.jh.sgs.base.exception.SgsApiException;

public interface DiscardNum {
    /**
     * 弃牌数
     */
    default int discardNum() {
        throw SgsApiException.FFWSX;
    }
}
