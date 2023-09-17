package com.jh.sgs.base.interactive;


import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.interactive.base.TargetCard;

public interface GX extends TargetCard {


    default void putCardsTop(int[] ids) {
        throw SgsApiException.FFWSX;
    }

    default void putCardsBottom(int[] ids) {
        throw SgsApiException.FFWSX;
    }
}
