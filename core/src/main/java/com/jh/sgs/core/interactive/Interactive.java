package com.jh.sgs.core.interactive;

import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.General;
import com.jh.sgs.core.pojo.InteractiveEnum;
import com.jh.sgs.core.pojo.ShowPlayer;

import java.util.List;

/**
 * 流程执行方法集，在各需要玩家响应的阶段，会产生一个流程方法集，该阶段会实现本次响应需要的方法，以供流程正常完整执行（目前无法得知具体实现的方法）
 */
public interface Interactive extends XZYX, CP, QP, XZMB, GHCQSSQYXZP, WGFDXZP {
    InteractiveEnum type();


    @Override
    default void setGeneral(int id) {
        throw SgsApiException.FFWSX;
    }

    @Override
    default List<General> selectableGeneral() {
        throw SgsApiException.FFWSX;
    }


    @Override
    default List<Card> handCard() {
        throw SgsApiException.FFWSX;
    }

    @Override
    default List<Card> equipCard() {
        throw SgsApiException.FFWSX;
    }

    @Override
    default List<Card> decideCard() {
        throw SgsApiException.FFWSX;
    }

    @Override
    default void disCard(int[] ids) {
        throw SgsApiException.FFWSX;
    }

    @Override
    default void cancelPlayCard() {
        throw SgsApiException.FFWSX;
    }

    @Override
    default void playCard(int id) {
        throw SgsApiException.FFWSX;
    }

    @Override
    default void setCard(int id) {
        throw SgsApiException.FFWSX;
    }

    @Override
    default List<ShowPlayer> targetPlayer() {
        throw SgsApiException.FFWSX;
    }

    @Override
    default void setTargetPlayer(int id) throws SgsApiException {
        throw SgsApiException.FFWSX;
    }

    @Override
    default void cancelTargetPlayer() {
        throw SgsApiException.FFWSX;
    }


    @Override
    default int discardNum() {
        throw SgsApiException.FFWSX;
    }

    @Override
    default List<Card> targetCard() {
        throw SgsApiException.FFWSX;
    }



}
