package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.DesktopRefuseException;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.MessageReceipter;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public abstract class MoreSilkbagCard extends SilkbagCard {
    @Override
    public void begin() {

    }

    @Override
    public void effect() throws DesktopException {
        for (CompletePlayer completePlayer : getPlayer()) {
            MessageReceipter.globalInContext(CardDesktop.playerInContext() +"将对"+completePlayer.getId()+"使用"+ CardDesktop.cardInContext());
            try {
                ContextManage.roundManage().wxkjCheck();
            } catch (DesktopRefuseException e) {
                continue;
            }
//            log.debug("{}：执行玩家：{}，被执行玩家：{}",getName() , ContextManage.desktop().getPlayer(), completePlayer);
            effect(completePlayer);
//            log.debug("{}完成：执行玩家：{}，被执行玩家：{}",getName() , ContextManage.desktop().getPlayer(), completePlayer);
            MessageReceipter.globalInContext(CardDesktop.playerInContext() +"完成对"+completePlayer.getId()+"使用"+CardDesktop.cardInContext());
        }
    }

    @Override
    public void end() {

    }

    abstract List<CompletePlayer> getPlayer() throws DesktopException;

    abstract void effect(CompletePlayer completePlayer) throws DesktopException;
}
