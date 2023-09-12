package com.jh.sgs.core.interactive;

import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.base.TargetCard;

public interface GX extends TargetCard {


    default void putCardsTop(int[] ids) {
        throw SgsApiException.FFWSX;
    }

    default void putCardsBottom(int[] ids) {
        throw SgsApiException.FFWSX;
    }
}
