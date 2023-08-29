package com.jh.sgs;

import com.jh.sgs.core.GameLauncher;
import com.jh.sgs.core.interfaces.MessageRequest;
import com.jh.sgs.text.Inputer;
import com.jh.sgs.text.TextMessageReceipt;

import java.io.IOException;

public class StartGame {
    public static Inputer inputer=new Inputer(System.in,-1);
    public static void main(String[] args) throws IOException, InterruptedException {
        MessageRequest run = GameLauncher.run(new TextMessageReceipt(), 5);
    }
}
