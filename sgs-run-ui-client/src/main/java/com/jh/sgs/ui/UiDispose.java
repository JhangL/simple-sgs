package com.jh.sgs.ui;

import java.io.IOException;

public class UiDispose {
    Player player;
    UiClient uiClient;

    public UiDispose(Player player, UiClient uiClient) {
        this.player = player;
        this.uiClient = uiClient;
    }

    public void dispose() {
        while (true) {
            try {
                TcpObject tcpObject = uiClient.handle();
                switch (tcpObject.getType()) {
                    case TcpObject.T_GM:
                        player.gloText(tcpObject.getGlobalMessage());
                        break;
                    case TcpObject.T_PM:
                        if (tcpObject.getShowCompletePlayer() != null)
                            player.flushData(tcpObject.getShowCompletePlayer());
                        player.plaText(tcpObject.getPlayerMessage());
                        break;
                    case TcpObject.T_M:
                        player.lableText(tcpObject.getMessage());

                        break;
                    case TcpObject.T_OP:
                        switch (tcpObject.getOperate()) {
                            case TcpObject.O_INOUT:
                                player.input();
                                break;
                            case TcpObject.O_TOF:
                                player.tof();
                                break;
                            case TcpObject.O_CANCEL:
                                player.cancel();
                                break;
                            case TcpObject.O_HANDCARD:
                                player.handCard();
                                break;
                            case TcpObject.O_ABILITY:
                                player.ability(tcpObject.getShowPlayCardAbility());
                                break;
                            case TcpObject.O_WAIT:
                                player.flushData(tcpObject.getShowCompletePlayer());
                                String i = player.waitValue();
                                TcpObject tcpObject1 = new TcpObject();
                                tcpObject1.setValue(i);
                                tcpObject1.setType(TcpObject.T_V);
                                uiClient.request(tcpObject1);
                                break;
                            case TcpObject.O_WAITS:
                                player.flushData(tcpObject.getShowCompletePlayer());
                                String i1 = player.waitValues();
                                TcpObject tcpObject12 = new TcpObject();
                                tcpObject12.setValue(i1);
                                tcpObject12.setType(TcpObject.T_V);
                                uiClient.request(tcpObject12);
                                break;
                            case TcpObject.O_CHOOSE:
                                player.choose(tcpObject.getChoose());
                                break;
                        }
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("与服务器断开连接");
                System.exit(0);
            }
        }
    }
}
