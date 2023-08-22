package com.jh.sgs.core;

import com.jh.sgs.GameLauncher;
import com.jh.sgs.exception.SgsException;
import com.jh.sgs.interfaces.Interactive;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InteractiveMachine {

    private AtomicInteger lockNum = new AtomicInteger();
    @Getter
    private boolean bockBool;
    private List<InteractiveEvent> interactiveEvents = new LinkedList<>();

    public void addEvent(int player, Interactive interactive) {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        if (threadGroup != GameLauncher.threadGroup) throw new SgsException("非游戏组线程调用");
        //todo 回调
        lockNum.getAndIncrement();
        interactiveEvents.add(new InteractiveEvent(player, interactive, this));
    }

    void subEvent(InteractiveEvent event) {
        interactiveEvents.remove(event);
        lockNum.getAndDecrement();
    }

    public void lock() {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        if (threadGroup != GameLauncher.threadGroup) throw new SgsException("非游戏组线程调用");
        bockBool = true;
        while (lockNum.get() != 0) ;
        bockBool = false;
    }


    public List<InteractiveEvent> getInteractiveEvents() {
        return new ArrayList<>(interactiveEvents);
    }
}
