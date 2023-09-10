package com.jh.sgs.core.interactive.base;

import com.jh.sgs.core.exception.SgsApiException;

public interface CancelTargetPlayer {
    /**
     * 取消选择目标玩家<br/>
     * 阶段：顺手牵羊目标选择
     */
    default void cancelTargetPlayer() {
        throw SgsApiException.FFWSX;
    }
}
