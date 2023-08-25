package com.jh.sgs.core.interfaces;

import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.General;

import java.util.List;

public interface Interactive{


    default void setGeneral(int id) throws SgsApiException {
        throw SgsApiException.ffwsx;
    }

    default List<General> selectableGeneral() {
        throw SgsApiException.ffwsx;
    }



    default List<Card> handCard() {
        throw SgsApiException.ffwsx;
    }


    default void discardCard(int[] ids) {
        throw SgsApiException.ffwsx;
    }
    default void cancelPlayCard() {
        throw SgsApiException.ffwsx;
    }
    default void playCard(int id) {
        throw SgsApiException.ffwsx;
    }

//    String message();
//    int getPlayer();
}
