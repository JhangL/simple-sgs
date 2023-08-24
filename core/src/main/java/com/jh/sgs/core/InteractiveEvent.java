package com.jh.sgs.core;

import com.jh.sgs.exception.SgsApiException;
import com.jh.sgs.interfaces.Interactiveable;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class InteractiveEvent {
    @Getter
    private final int player;
    @Getter
    private final Interactiveable interactiveable;
    private final InteractiveMachine interactiveMachine;

//    private final long timeoutMS;

    public InteractiveEvent(int player, Interactiveable interactive, InteractiveMachine interactiveMachine) {
        this(player,interactive,interactiveMachine,0);
    }

    public InteractiveEvent(int player, Interactiveable interactive, InteractiveMachine interactiveMachine, long timeoutMS) {
        this.player = player;
        this.interactiveable = interactive;
        this.interactiveMachine = interactiveMachine;
//        this.timeoutMS = timeoutMS;
    }
    public void begin(GameEngine gameEngine) throws SgsApiException {
        ContextManage.setContext(gameEngine);
    }

    public void cancel(){
        log.debug(player+"未执行操作,自动执行以下-->");
        interactiveable.cancel();
        log.debug(player+"自动执行完成-->");
        interactiveMachine.subEvent(this);

    }
    public void complete() throws SgsApiException {
        if (!interactiveable.complete()) throw new SgsApiException("流程未完成");
        interactiveMachine.subEvent(this);

    }
}
