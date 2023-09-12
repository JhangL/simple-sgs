package com.jh.sgs.core.interactive.impl;

import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.enums.InteractiveEnum;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.pool.BooleanPool;

public class TOFImpl implements Interactiveable {
    boolean a;

    BooleanPool pool;

    public TOFImpl(BooleanPool pool) {
        this.pool = pool;
    }

    @Override
    public void trueOrFalse(boolean tof) {
        pool.setPool(tof);
        a=true;
    }

    @Override
    public InteractiveEnum type() {
        return InteractiveEnum.TOF;
    }

    @Override
    public void cancel() {
        trueOrFalse(false);
    }

    @Override
    public InteractiveEvent.CompleteEnum complete() {
        return a ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
    }
}
