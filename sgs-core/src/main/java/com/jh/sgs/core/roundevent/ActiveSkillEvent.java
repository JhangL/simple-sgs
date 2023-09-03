package com.jh.sgs.core.roundevent;

import com.jh.sgs.core.interactive.Interactiveable;

public interface ActiveSkillEvent extends RoundEvent {
    Interactiveable addSkillOption(Interactiveable interactiveable);

}
