package com.jh.sgs;

import com.jh.sgs.ui.*;

import javax.swing.*;
import java.io.IOException;

public class StartGameInUIClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        UIManager.setLookAndFeel(lookAndFeel);
        String add="localhost";
        if (args.length==1){
            add=args[0];
        }
        UiClient uiClient = new UiClient(add);
        TcpObject handle = uiClient.handle();
        Main main = new Main();
        main.setTitle("SGS-palyer" + handle.getIndex());
        Player player = new Player(handle.getIndex(),main);
        main.setContentPane(player);
        main.pack();
        main.setVisible(true);
        UiDispose uiDispose = new UiDispose(player, uiClient);
        uiDispose.dispose();
    }
}
