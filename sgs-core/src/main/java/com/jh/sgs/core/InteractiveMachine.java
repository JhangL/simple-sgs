package com.jh.sgs.core;

import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.interfaces.ShowStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InteractiveMachine implements ShowStatus {

    private AtomicInteger lockNum = new AtomicInteger();
    @Getter
    private boolean bockBool;
    private List<InteractiveEvent> interactiveEvents = new LinkedList<>();

    InteractiveMachine() {
    }

    public void addEvent(int player,String message, Interactiveable interactiveable) {
        addEvent(new InteractiveEvent(player,message,interactiveable));
    }
    public void addEvent(InteractiveEvent interactiveEvent) {
        if (Thread.currentThread().getThreadGroup() != GameEngine.threadGroup) throw new SgsRuntimeException("非游戏组线程调用");
        lockNum.getAndIncrement();
        interactiveEvents.add(interactiveEvent);
    }

    void subEvent(InteractiveEvent event) {
        if (Thread.currentThread().getThreadGroup()== GameEngine.threadGroup) throw new SgsRuntimeException("游戏组线程调用");
        interactiveEvents.remove(event);
        lockNum.getAndDecrement();
    }

    public void lock() {
        if (Thread.currentThread().getThreadGroup() != GameEngine.threadGroup) throw new SgsRuntimeException("非游戏组线程调用");
        bockBool = true;
        GameEngine gameEngine = ContextManage.gameEngine();
        new Thread(new ThreadGroup("player"),() -> {
            ContextManage.setContext(gameEngine);
            ContextManage.messageReceipt().system(new ArrayList<>(interactiveEvents));
            ContextManage.setContext(null);
        },"player").start();
        while (lockNum.get() != 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        bockBool = false;
    }



    @Override
    public String getStatus() {
        return "{" +
                "\"bockBool\":" + bockBool +
                '}';
    }

}
