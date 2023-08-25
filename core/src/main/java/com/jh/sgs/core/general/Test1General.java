package com.jh.sgs.core.general;

import com.jh.sgs.core.RoundProcess;
import com.jh.sgs.core.pojo.CompletePlayer;

public class Test1General extends BaseGeneral {


    @Override
    public RoundProcess roundProcess(CompletePlayer completePlayer) {
        return new RoundProcess(completePlayer);
    }

}
