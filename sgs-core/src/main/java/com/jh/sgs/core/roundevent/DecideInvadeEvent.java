package com.jh.sgs.core.roundevent;

import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.core.interfaces.RoundEvent;

public interface DecideInvadeEvent extends RoundEvent {
    Card decideInvade();
}
