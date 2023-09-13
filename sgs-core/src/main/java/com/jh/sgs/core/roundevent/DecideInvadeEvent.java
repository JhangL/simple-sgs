package com.jh.sgs.core.roundevent;

import com.jh.sgs.core.pojo.Card;

public interface DecideInvadeEvent extends RoundEvent{
    Card decideInvade();
}
