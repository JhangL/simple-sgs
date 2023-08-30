package com.jh.sgs.core.interactive.base;

import com.jh.sgs.core.pojo.ShowPlayer;

import java.util.List;

public interface TargetPlayer {
    /**
     * 查看目标玩家<br/>
     * 阶段：顺手牵羊目标选择
     */
    List<ShowPlayer> targetPlayer();
}
