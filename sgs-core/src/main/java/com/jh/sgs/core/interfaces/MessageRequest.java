package com.jh.sgs.core.interfaces;

import com.jh.sgs.base.pojo.ShowPlayer;
import com.jh.sgs.core.pojo.CompletePlayer;

public interface MessageRequest {
    String getAll();

    CompletePlayer getPlayer(int id);
    ShowPlayer getShowPlayer(int id);

    int getUsingCardNum();
    int getUsedCardNum();


}
