package com.jh.sgs.core.roundevent;

import com.jh.sgs.core.pojo.Card;

public interface CardTargetHideEvent extends RoundEvent{
    boolean hide(Card card);
}
