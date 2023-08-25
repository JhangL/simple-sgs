package com.jh.sgs.core;

import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interfaces.Interactive;
import com.jh.sgs.core.interfaces.Interactiveable;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class InteractiveEvent {
    @Getter
    private final int player;
    private boolean cancelLock = false;
    @Getter
    private final String message;
    private final Interactiveable interactiveable;

    public InteractiveEvent(int player, String message, Interactiveable interactiveable) {
        this.player = player;
        this.message = message;
        this.interactiveable = interactiveable;
    }

    /**
     * 取消流程执行，系统自动执行给定流程。注意：当开始执行流程（执行{@link InteractiveEvent#interactive()}）后，无法取消流程
     */
    public void cancel() {
        if (cancelLock) throw new SgsApiException("已开始执行流程，无法取消");
        log.debug(player + "未执行操作,自动执行以下-->");
        interactiveable.cancel();
        log.debug(player + "自动执行完成-->");
        ContextManage.interactiveMachine().subEvent(this);

    }

    /**
     * 当所有流程执行结束后，执行此方法意味流程结束
     */
    public void complete() {
        if (!interactiveable.complete()) throw new SgsApiException("流程未完成");
        log.debug(player + "流程执行结束-->");
        ContextManage.interactiveMachine().subEvent(this);
    }

    /**
     * 用户获取执行流程的交互组件，此方法也意味这流程执行的开始。注意：流程开始必须调用流程提交方法{@link InteractiveEvent#complete()}(目前暂不支持流程回滚)
     *
     * @return 执行流程的交互组件
     */
    public Interactive interactive() {
        cancelLock = true;
        log.debug(player + "开始执行流程-->");
        return interactiveable;
    }
}
