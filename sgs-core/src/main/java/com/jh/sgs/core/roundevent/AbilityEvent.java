package com.jh.sgs.core.roundevent;

import com.jh.sgs.core.pojo.Ability;

import java.util.List;

public interface AbilityEvent extends RoundEvent {
    List<Ability> addAbilityOption();


}
