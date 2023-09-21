package com.jh.sgs;

import com.jh.sgs.base.interfaces.MessageReceipt;
import com.jh.sgs.base.interfaces.MessageRequest;
import com.jh.sgs.core.GameLauncher;
import com.jh.sgs.core.data.JsonBasicData;
import com.jh.sgs.core.interfaces.BasicData;
import com.jh.sgs.core.interfaces.GameConfig;
import com.jh.sgs.ui.Util;
import com.jh.sgs.uiServer.UiMessageReceipt;
import com.jh.sgs.uiServer.UiServer;

import javax.swing.*;
import java.io.IOException;

/**
 * 〈功能概述〉<br>
 *
 * @author:hp
 * @date: 2023/9/15 15:26
 */
public class StartGameInUIServer {
    public static MessageRequest run;

    public static void main(String[] args) throws IOException {
        new Thread(() -> run = GameLauncher.run(new GameConfig() {

            @Override
            public MessageReceipt messageReceipt() {
                return new UiMessageReceipt(new UiServer(playerNum()));
            }

            @Override
            public int playerNum() {
                return 2;
            }

            @Override
            public BasicData basicData() {
                return new JsonBasicData(Util.read("classpath:data.json"));
//                return new DataBaseBasicData("classpath:dataBase.properties");
            }
        })).start();
        if (args.length == 1) {
            String arg = args[0];
            int i = Integer.parseInt(arg);
            for (int i1 = 0; i1 < i; i1++) {
                new Thread(() -> {
                    try {
                        StartGameInUIClient.main(new String[]{});
                    } catch (IOException | ClassNotFoundException | UnsupportedLookAndFeelException |
                             InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }
    }
}
