package com.jh.sgs.core.general;

import com.jh.sgs.core.pojo.CompletePlayer;

public class ZhangFei extends BaseGeneral {

    public ZhangFei(CompletePlayer completePlayer) {
        super(completePlayer);
    }

    @Override
    public int getLimitSha() {
        return 100;
    }
}
