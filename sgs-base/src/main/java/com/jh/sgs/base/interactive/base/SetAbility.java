package com.jh.sgs.base.interactive.base;


import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.pojo.ShowPlayCardAbility;

import java.util.List;

public interface SetAbility {
    default void setAbility(int id){
        throw SgsApiException.FFWSX;
    }

    default List<ShowPlayCardAbility> showAbility(){
        throw SgsApiException.FFWSX;
    }
}
