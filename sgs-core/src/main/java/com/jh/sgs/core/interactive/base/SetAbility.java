package com.jh.sgs.core.interactive.base;

import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.ShowPlayCardAbility;

import java.util.List;

public interface SetAbility {
    default void setAbility(int id){
        throw SgsApiException.FFWSX;
    }

    default List<ShowPlayCardAbility> showAbility(){
        throw SgsApiException.FFWSX;
    }
}
