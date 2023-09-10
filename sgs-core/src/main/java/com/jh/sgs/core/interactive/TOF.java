package com.jh.sgs.core.interactive;

import com.jh.sgs.core.exception.SgsApiException;

public interface TOF {

    default void trueOrFalse(boolean tof){
        throw SgsApiException.FFWSX;
    }
}
