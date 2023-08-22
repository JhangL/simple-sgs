package com.jh.sgs.interfaces;

import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.exception.SgsApiException;
import com.jh.sgs.pojo.General;

import java.util.List;

public interface Interactive {

    default void setGeneral(General general) throws SgsApiException {
        throw SgsApiException.ffwsx;
    }

    default List<General> getSelectableGeneral() {
        throw SgsApiException.ffwsx;
    }

//    void cancel();

    boolean complete();
//    int getPlayer();
}
