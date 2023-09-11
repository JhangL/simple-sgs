package com.jh.sgs.core.roundevent;

import com.jh.sgs.core.pojo.PlayCardAbility;

import java.util.List;

public interface PlayCardAbilityEvent extends RoundEvent {
    List<PlayCardAbility> addAbilityOption();


}
