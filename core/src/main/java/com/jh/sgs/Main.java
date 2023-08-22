package com.jh.sgs;

import com.jh.sgs.core.GameEngine;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.exception.SgsApiException;
import com.jh.sgs.interfaces.Interactive;
import com.jh.sgs.interfaces.MessageReceipt;
import com.jh.sgs.pojo.General;

import java.util.List;

// 按两次 Shift 打开“随处搜索”对话框并输入 `show whitespaces`，
// 然后按 Enter 键。现在，您可以在代码中看到空格字符。
public class Main {
    public static void main(String[] args) throws SgsApiException, InterruptedException {
        GameLauncher run = GameLauncher.run(new MessageReceipt() {
            @Override
            public void personal(int player, String message) {
                System.out.println("玩家" + player + "：" + message);
            }

            @Override
            public void global(String message) {
                System.out.println("世界：" + message);
            }
        }, 5);
        Thread.sleep(500);
        InteractiveMachine interactiveMachine = run.getGameEngine().getInteractiveMachine();

        while (!interactiveMachine.isBockBool()) ;
        List<InteractiveEvent> interactiveEvents = interactiveMachine.getInteractiveEvents();

        for (InteractiveEvent interactiveEvent : interactiveEvents) {
            Interactive interactive = interactiveEvent.getInteractive();
            List<General> selectableGeneral = interactive.getSelectableGeneral();
            interactive.setGeneral(selectableGeneral.get(0));
        }
        for (InteractiveEvent interactiveEvent : interactiveEvents) {
            interactiveEvent.complete();
        }
        while (true) ;
    }
}