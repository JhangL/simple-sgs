package com.jh.sgs.core;

import com.jh.sgs.exception.SgsApiException;
import com.jh.sgs.interfaces.Interactive;
import lombok.Getter;

public class InteractiveEvent {
    @Getter
    private final int player;
    @Getter
    private final Interactive interactive;
    private final InteractiveMachine interactiveMachine;

//    private final long timeoutMS;

    public InteractiveEvent(int player, Interactive interactive, InteractiveMachine interactiveMachine) {
        this(player,interactive,interactiveMachine,0);
    }

    public InteractiveEvent(int player, Interactive interactive, InteractiveMachine interactiveMachine, long timeoutMS) {
        this.player = player;
        this.interactive = interactive;
        this.interactiveMachine = interactiveMachine;
//        this.timeoutMS = timeoutMS;
    }

    public void complete() throws SgsApiException {
        if (!interactive.complete()) throw new SgsApiException("流程未完成");
        interactiveMachine.subEvent(this);
    }
}
