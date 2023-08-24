package com.jh.sgs.general;

import com.jh.sgs.pojo.CompletePlayer;

public class TestGeneral extends BaseGeneral {


    @Override
    public com.jh.sgs.core.RoundProcess roundProcess(CompletePlayer completePlayer) {
        return new RoundProcess(completePlayer);
    }

    public static class RoundProcess extends com.jh.sgs.core.RoundProcess {

        public RoundProcess(CompletePlayer completePlayer) {
            super(completePlayer);
        }


    }

}
