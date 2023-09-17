package com.jh.sgs.uiServer;


import com.jh.sgs.StartGameInUIServer;
import com.jh.sgs.base.interfaces.InteractiveEvent;
import com.jh.sgs.base.interfaces.MessageReceipt;
import com.jh.sgs.ui.TcpObject;

import java.io.IOException;
import java.util.List;

/**
 * 〈功能概述〉<br>
 *
 * @author:hp
 * @date: 2023/9/15 16:12
 */
public class UiMessageReceipt implements MessageReceipt {

    UiServer uiServer;

    public UiMessageReceipt(UiServer uiServer) {
        this.uiServer = uiServer;
        try {
            uiServer.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void personal(int player, String message) {
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_PM);
        tcpObject.setShowCompletePlayer(StartGameInUIServer.run.getPlayer(player));
        tcpObject.setPlayerMessage(message+"\n");
        uiServer.request(player,tcpObject);
    }

    @Override
    public void global(String message) {
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_GM);

        tcpObject.setGlobalMessage(message+"\n");
        uiServer.request(tcpObject);
    }

    @Override
    public void system(List<InteractiveEvent> interactiveEvents) {
        for (InteractiveEvent interactiveEvent : interactiveEvents) {
            new Thread(() -> {
                EventDispose eventDispose = new EventDispose(interactiveEvent, uiServer);
                eventDispose.dispose();
            }).start();
        }
    }

    @Override
    public String name() {
        return null;
    }
}
