package com.jh.sgs.interfaces;

import com.jh.sgs.core.InteractiveEvent;

import java.util.List;

public interface MessageReceipt {

    void personal(int player,String message);
    void global(String message);

    /**
     * 系统回调 系统会在所有待处理事件加载完成后，进行锁定。锁定时一并调用此方法（会在新的线程中）
     * @param interactiveEvents 当前所有待处理事件
     */
    void system(List<InteractiveEvent> interactiveEvents);
    String name();
}
