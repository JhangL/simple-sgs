package com.jh.sgs.core;

import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.interfaces.InteractiveSubmit;
import com.jh.sgs.core.interfaces.MessageReceipt;
import com.jh.sgs.core.interfaces.ShowStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InteractiveMachine implements ShowStatus, InteractiveSubmit {

    private AtomicInteger lockNum = new AtomicInteger();
    @Getter
    private boolean bockBool;
    private List<InteractiveEvent> interactiveEvents = new LinkedList<>();

    InteractiveMachine() {
    }

    public InteractiveMachine addEvent(int player, String message, Interactiveable interactiveable) {
        MessageReceipt.personalInContext(player,"请处理请求");
        return addEvent(new InteractiveEvent(player, message, interactiveable,this));
    }

    private InteractiveMachine addEvent(InteractiveEvent interactiveEvent) {
        if (Thread.currentThread().getThreadGroup() != GameEngine.threadGroup)
            throw new SgsRuntimeException("非游戏组线程调用");
        lockNum.getAndIncrement();
        interactiveEvents.add(interactiveEvent);
        return this;
    }

    void subEvent(InteractiveEvent event) {
        if (Thread.currentThread().getThreadGroup() == GameEngine.threadGroup)
            throw new SgsRuntimeException("游戏组线程调用");
        interactiveEvents.remove(event);
        lockNum.getAndDecrement();
    }

    @Override
    public void submit(InteractiveEvent interactiveEvent) {
        try {
            subEvent(interactiveEvent);
        } catch (Exception e) {
            throw new SgsRuntimeException(e.getMessage());
        }
    }

    public void lock() {
        if (Thread.currentThread().getThreadGroup() != GameEngine.threadGroup)
            throw new SgsRuntimeException("非游戏组线程调用");
        bockBool = true;
        MessageReceipt messageReceipt = ContextManage.messageReceipt();
        new Thread(GameEngine.threadGroupPlayer, () -> {
            messageReceipt.system(new ArrayList<>(interactiveEvents));
        }).start();
        while (lockNum.get() != 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ;
        bockBool = false;
    }


    @Override
    public String getStatus() {
        return "{" +
                "\"bockBool\":" + bockBool +
                '}';
    }


    public static InteractiveMachine addEventInContext(int player, String message, Interactiveable interactiveable) {
        return ContextManage.interactiveMachine().addEvent(player, message, interactiveable);
    }


}
