package com.jh.sgs.core.roundevent;

import com.jh.sgs.core.interfaces.RoundEvent;


public interface LossCardEvent extends RoundEvent {

    public static final int HAND_CARD = 1;
    public static final int EQUIP_CARD = 2;
    public static final int DECIDE_CARD = 3;
    void loseCard(int operatePlayer, int lossLocation);
}
