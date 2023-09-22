package com.jh.sgs.core.card;

import com.jh.sgs.base.pool.TPool;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.interactive.impl.XZMBImpl;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.MessageReceipter;

import java.util.List;

public interface Shaable {
    default void sha() throws DesktopException {
        int i = shaTarget();
        MessageReceipter.personalInContext(i,"{}准备杀你",CardDesktop.playerInContext());
        MessageReceipter.personalInContext(CardDesktop.playerInContext(),"你准备杀{}",i);
        shaExecute(i);
        MessageReceipter.personalInContext(i,"{}完成杀你",CardDesktop.playerInContext());
        MessageReceipter.personalInContext(CardDesktop.playerInContext(),"你完成杀{}",i);
    };

    default int shaTarget() throws DesktopErrorException{
        List<CompletePlayer> targets = ContextManage.roundManage().findTarget(CardDesktop.playerInContext(), CardDesktop.cardInContext(), shaDistance());
        TPool<Integer> targetPlayer=new TPool<>();
        InteractiveMachine.addEventInContext(CardDesktop.playerInContext(),"请选择目标",new XZMBImpl(targetPlayer,targets)).lock();
        if (targetPlayer.getPool() == null) throw new DesktopErrorException("未选择目标");
        return targetPlayer.getPool();
    };

    void shaExecute(int player);
    int shaDistance();
}
