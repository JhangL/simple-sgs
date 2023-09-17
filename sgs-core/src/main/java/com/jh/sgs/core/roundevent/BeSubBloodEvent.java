package com.jh.sgs.core.roundevent;

import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pool.TPool;
import com.jh.sgs.core.interfaces.RoundEvent;

public interface BeSubBloodEvent extends RoundEvent {

    void beSubBlood(int operatePlayer, TPool<Card> operateCard);
}
