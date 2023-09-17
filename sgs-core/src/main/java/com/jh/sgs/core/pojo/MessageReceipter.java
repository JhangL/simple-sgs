package com.jh.sgs.core.pojo;

import com.jh.sgs.core.ContextManage;
import org.apache.logging.log4j.message.FormattedMessage;

public class MessageReceipter {

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
}
