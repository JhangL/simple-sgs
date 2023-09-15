package com.jh.sgs;

import com.jh.sgs.core.GameLauncher;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.data.JsonBasicData;
import com.jh.sgs.core.interfaces.BasicData;
import com.jh.sgs.core.interfaces.GameConfig;
import com.jh.sgs.core.interfaces.MessageReceipt;
import com.jh.sgs.ui.Main;
import com.jh.sgs.ui.Player;
import com.jh.sgs.ui.UiMessageReceipt;
import com.jh.sgs.ui.Util;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 〈功能概述〉<br>
 *
 * @author:hp
 * @date: 2023/9/15 15:26
 */
public class StartGameInUI {

    public static void main(String[] args) {
        final Player[][] players = new Player[1][];
        GameConfig gameConfig = new GameConfig() {

            @Override
            public MessageReceipt messageReceipt() {
                players[0] =new Player[playerNum()];
                for (int i = 0; i < players[0].length; i++) {
                    players[0][i]=new Player();
                }
                return new UiMessageReceipt(players[0]);
            }

            @Override
            public int playerNum() {
                return 2;
            }

            @Override
            public BasicData basicData() {
                return new JsonBasicData(Util.read("classpath:data.json"));
            }
        };
        GameLauncher.run(gameConfig);
        for (Player player : players[0]) {
            Main main = new Main();
            main.setContentPane(player);
            main.pack();
            main.setVisible(true);
        }
    }


}
