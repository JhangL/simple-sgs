package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.interactive.impl.XZMBImpl;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pool.TPool;

import java.util.List;

public interface Shaable {
    default void sha() throws DesktopException{
        shaExecute(shaTarget());
    };

    default int shaTarget() throws DesktopErrorException{
        List<CompletePlayer> targets = ContextManage.roundManage().findTarget(ContextManage.shaCardDesktop().getPlayer(), ContextManage.shaCardDesktop().getCard(), shaDistance());
        TPool<Integer> targetPlayer=new TPool<>();
        ContextManage.interactiveMachine().addEvent(ContextManage.shaCardDesktop().getPlayer(),"请选择目标",new XZMBImpl(targetPlayer,targets)).lock();
        if (targetPlayer.getPool() == null) throw new DesktopErrorException("未选择目标");
        return targetPlayer.getPool();
    };

    void shaExecute(int player);
    int shaDistance();
}
