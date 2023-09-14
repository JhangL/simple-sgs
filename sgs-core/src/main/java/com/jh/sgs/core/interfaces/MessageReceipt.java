package com.jh.sgs.core.interfaces;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveEvent;
import org.apache.logging.log4j.message.FormattedMessage;

import java.util.List;

public interface MessageReceipt {


    public static void personalInContext(int player, String message) {
        ContextManage.messageReceipt().personal(player, message);
    }
    public static void personalInContext(int player, String message,Object... params) {
        ContextManage.messageReceipt().personal(player, new FormattedMessage(message,params).getFormattedMessage());
    }

    public static void globalInContext(String message) {
        ContextManage.messageReceipt().global(message);
    }
    public static void globalInContext(String message,Object... params) {
        ContextManage.messageReceipt().global(new FormattedMessage(message,params).getFormattedMessage());
    }

    void personal(int player, String message);

    void global(String message);

    /**
     * 系统回调 系统会在所有待处理事件加载完成后，进行锁定。锁定时一并调用此方法（会在新的线程中）
     * @param interactiveEvents 当前所有待处理事件
     */
    void system(List<InteractiveEvent> interactiveEvents);

    String name();
}
