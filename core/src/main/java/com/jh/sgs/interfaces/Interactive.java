package com.jh.sgs.interfaces;

import com.jh.sgs.exception.SgsApiException;
import com.jh.sgs.pojo.Card;
import com.jh.sgs.pojo.General;

import java.util.List;

public interface Interactive{


    default void setGeneral(General general) throws SgsApiException {
        throw SgsApiException.ffwsx;
    }

    default List<General> selectableGeneral() {
        throw SgsApiException.ffwsx;
    }



    default List<Card> handCard() {
        throw SgsApiException.ffwsx;
    }


    default void discardCard(List<Card> cards) {
        throw SgsApiException.ffwsx;
    }
    default void cancelPlayCard() {
        throw SgsApiException.ffwsx;
    }

    String message();
//    int getPlayer();
}
