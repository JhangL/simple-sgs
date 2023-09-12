package com.jh.sgs.core.general;

import com.jh.sgs.core.pojo.CompletePlayer;

public class LvMeng extends BaseGeneral {
    public LvMeng(CompletePlayer completePlayer) {
        super(completePlayer);
    }

    @Override
    public void disCard() {
        if (getUseSha() != 0) super.disCard();
    }
}
