package com.jh.sgs.text;

import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.interfaces.MessageReceipt;

import java.util.List;

public class TextMessageReceipt implements MessageReceipt {
    @Override
    public void personal(int player, String message) {
        System.out.println(player + "消息：" + message);
    }

    @Override
    public void global(String message) {
        System.out.println("全局消息：" + message);
    }

    @Override
    public void system(List<InteractiveEvent> interactiveEvents) {
        for (InteractiveEvent interactiveEvent : interactiveEvents) {
            EventDispose.disposal(interactiveEvent);
        }

    }

    @Override
    public String name() {
        return "控制台文本回调器";
    }
}
