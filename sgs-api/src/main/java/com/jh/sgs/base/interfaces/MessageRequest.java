package com.jh.sgs.base.interfaces;

import com.jh.sgs.base.pojo.ShowCompletePlayer;
import com.jh.sgs.base.pojo.ShowPlayer;

public interface MessageRequest {
    String getAll();

    ShowCompletePlayer getPlayer(int id);
    ShowPlayer getShowPlayer(int id);

    int getUsingCardNum();
    int getUsedCardNum();


}
