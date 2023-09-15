package com.jh.sgs.core.interfaces;

import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.ShowPlayer;

public interface MessageRequest {
    String getAll();

    CompletePlayer getPlayer(int id);
    ShowPlayer getShowPlayer(int id);

    int getUsingCardNum();
    int getUsedCardNum();


}
