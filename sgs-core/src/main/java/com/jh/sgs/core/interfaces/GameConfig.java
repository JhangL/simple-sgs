package com.jh.sgs.core.interfaces;

import com.jh.sgs.base.interfaces.MessageReceipt;

public interface GameConfig {
    MessageReceipt messageReceipt();
    int playerNum();
    BasicData basicData();
}
