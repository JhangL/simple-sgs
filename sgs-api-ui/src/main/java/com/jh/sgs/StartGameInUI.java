package com.jh.sgs;

import com.jh.sgs.core.GameLauncher;
import com.jh.sgs.core.data.JsonBasicData;
import com.jh.sgs.core.interfaces.BasicData;
import com.jh.sgs.core.interfaces.GameConfig;
import com.jh.sgs.core.interfaces.MessageReceipt;
import com.jh.sgs.ui.Main;
import com.jh.sgs.ui.Player;
import com.jh.sgs.ui.UiMessageReceipt;
import com.jh.sgs.ui.Util;

import javax.swing.*;

/**
 * 〈功能概述〉<br>
 *
 * @author:hp
 * @date: 2023/9/15 15:26
 */
public class StartGameInUI {

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        UIManager.setLookAndFeel(lookAndFeel);
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
        Player[] player1 = players[0];
        for (int i = 0; i < player1.length; i++) {
            Player player = player1[i];
            Main main = new Main();
            main.setTitle("SGS-palyer"+i);
            main.setContentPane(player);
            main.pack();
            main.setVisible(true);
        }
    }


}
