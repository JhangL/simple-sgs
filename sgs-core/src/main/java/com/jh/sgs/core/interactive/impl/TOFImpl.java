package com.jh.sgs.core.interactive.impl;

import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.interactive.Interactiveable;
import com.jh.sgs.base.pool.BooleanPool;

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
    public CompleteEnum complete() {
        return a ? CompleteEnum.COMPLETE : CompleteEnum.NOEXECUTE;
    }
}
