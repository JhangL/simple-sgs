package com.jh.sgs.ui;

import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.interfaces.MessageReceipt;

import java.util.List;

/**
 * 〈功能概述〉<br>
 *
 * @author:hp
 * @date: 2023/9/15 16:12
 */
public class UiMessageReceipt implements MessageReceipt {

    Player[] players;

    public UiMessageReceipt(Player[] players) {
        this.players = players;
    }

    @Override
    public void personal(int player, String message) {
        players[player].plaText(message+"\n");
    }

    @Override
    public void global(String message) {
        for (Player player : players) {
            player.gloText(message+"\n");
        }
    }

    @Override
    public void system(List<InteractiveEvent> interactiveEvents) {
        for (InteractiveEvent interactiveEvent : interactiveEvents) {
            new Thread(() -> {
                Player player = players[interactiveEvent.getPlayer()];
                EventDispose eventDispose = new EventDispose(interactiveEvent, player);
                eventDispose.dispose();
            }).start();
        }
    }

    @Override
    public String name() {
        return null;
    }
}
