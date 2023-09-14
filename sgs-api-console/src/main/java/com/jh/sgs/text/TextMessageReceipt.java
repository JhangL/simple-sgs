package com.jh.sgs.text;

import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.interfaces.MessageReceipt;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TextMessageReceipt implements MessageReceipt {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public void personal(int player, String message) {
//        Util.printColor(34,3, LocalDateTime.now().format(formatter)+"  ");
//        Util.printlnColor(35,3,player + "  消息：\t" + message);
    }

    @Override
    public void global(String message) {
//        Util.printColor(34,3, LocalDateTime.now().format(formatter)+"  ");
//        Util.printlnColor(35,3,"全局消息：\t" + message);
    }

    @Override
    public void system(List<InteractiveEvent> interactiveEvents) {
        for (InteractiveEvent interactiveEvent : interactiveEvents) {
            EventDispose.disposal(interactiveEvent);
        }

    }
    @Override
    public String name() {
        return "控制台处理文本回调器";
    }
}
