package com.jh.sgs.core.general;

import com.jh.sgs.core.RoundProcess;
import com.jh.sgs.core.pojo.CompletePlayer;

public class ZhangFei extends RoundProcess {

    public ZhangFei(CompletePlayer completePlayer) {
        super(completePlayer);
        setLimitSha(100);
    }
}
