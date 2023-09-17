package com.jh.sgs.base.interactive;


import com.jh.sgs.base.exception.SgsApiException;

public interface TOF {

    default void trueOrFalse(boolean tof){
        throw SgsApiException.FFWSX;
    }
}
