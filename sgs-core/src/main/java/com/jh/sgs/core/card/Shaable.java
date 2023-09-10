package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.pojo.CompletePlayer;

import java.util.List;

public interface Shaable {
    default void sha() throws DesktopException{
        shaExecute(shaTarget());
    };

    default int shaTarget() throws DesktopErrorException{
        List<CompletePlayer> targets = ContextManage.roundManage().findTarget(ContextManage.shaCardDesktop().getPlayer(), ContextManage.shaCardDesktop().getCard(), shaDistance());
        final Integer[] targetPlayer = new Integer[1];
        ContextManage.roundManage().selectTarget(ContextManage.executeCardDesktop().getPlayer(),targets,targetPlayer);
        if (targetPlayer[0] == null) throw new DesktopErrorException("未选择目标");
        return targetPlayer[0];
    };

    void shaExecute(int player);
    int shaDistance();
}
