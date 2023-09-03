package com.jh.sgs.core.interfaces;

public interface MessageRequest {
    String getAll();

    String getPlayer(int id);
    String getShowPlayer(int id);

    int getUsingCardNum();
    int getUsedCardNum();

}
