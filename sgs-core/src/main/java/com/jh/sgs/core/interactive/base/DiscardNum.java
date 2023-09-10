package com.jh.sgs.core.interactive.base;

import com.jh.sgs.core.exception.SgsApiException;

public interface DiscardNum {
    /**
     * 弃牌数
     */
    default int discardNum() {
        throw SgsApiException.FFWSX;
    }
}
