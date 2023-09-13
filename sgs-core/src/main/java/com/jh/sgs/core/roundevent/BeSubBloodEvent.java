package com.jh.sgs.core.roundevent;

import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pool.TPool;

public interface BeSubBloodEvent extends RoundEvent{

    void beSubBlood(int operatePlayer, TPool<Card> operateCard);
}
