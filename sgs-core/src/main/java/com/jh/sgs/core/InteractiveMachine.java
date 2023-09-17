package com.jh.sgs.core;

import com.jh.sgs.base.interactive.Interactiveable;
import com.jh.sgs.base.interfaces.MessageReceipt;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.interfaces.InteractiveSubmit;
import com.jh.sgs.core.interfaces.ShowStatus;
import com.jh.sgs.core.pojo.MessageReceipter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InteractiveMachine implements ShowStatus, InteractiveSubmit {

    private AtomicInteger lockNum = new AtomicInteger();
    @Getter
    private boolean bockBool;
    private List<InteractiveEventer> interactiveEventers = new LinkedList<>();

    InteractiveMachine() {
    }

    public InteractiveMachine addEvent(int player, String message, Interactiveable interactiveable) {
        MessageReceipter.personalInContext(player,"请处理请求");
        return addEvent(new InteractiveEventer(player, message, interactiveable,this));
    }

    private InteractiveMachine addEvent(InteractiveEventer interactiveEventer) {
        if (Thread.currentThread().getThreadGroup() != GameEngine.threadGroup)
            throw new SgsRuntimeException("非游戏组线程调用");
        lockNum.getAndIncrement();
        interactiveEventers.add(interactiveEventer);
        return this;
    }

    void subEvent(InteractiveEventer event) {
        if (Thread.currentThread().getThreadGroup() == GameEngine.threadGroup)
            throw new SgsRuntimeException("游戏组线程调用");
        interactiveEventers.remove(event);
        lockNum.getAndDecrement();
    }

    @Override
    public void submit(InteractiveEventer interactiveEventer) {
        try {
            subEvent(interactiveEventer);
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
            messageReceipt.system(new ArrayList<>(interactiveEventers));
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
