package com.jh.sgs.core.general;

import com.jh.sgs.core.RoundProcess;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseGeneral extends RoundProcess{

    @Getter
    @Setter
    private Object[] skill=new Object[0];

    public BaseGeneral(CompletePlayer completePlayer) {
        super(completePlayer);
    }
}
