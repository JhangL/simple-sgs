package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.interfaces.MessageReceipt;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class OneSilkbagCard extends SilkbagCard {
    @Override
    public void begin() {

    }

    @Override
    public void effect() throws DesktopException {
        int player = getPlayer();
        MessageReceipt.globalInContext(ContextManage.executeCardDesktop().getPlayer() +"将对"+player+"使用"+ContextManage.executeCardDesktop().getCard());
        ContextManage.roundManage().wxkjCheck();
//        log.debug("{}：执行玩家：{}，被执行玩家：{}",getName() , ContextManage.desktop().getPlayer(), player);
        effect(player);
//        log.debug("{}完成：执行玩家：{}，被执行玩家：{}",getName() , ContextManage.desktop().getPlayer(), player);
        MessageReceipt.globalInContext(ContextManage.executeCardDesktop().getPlayer() +"完成对"+player+"使用"+ContextManage.executeCardDesktop().getCard());
    }

    @Override
    public void end() {

    }

    abstract int getPlayer() throws DesktopErrorException;

    abstract void effect(int player);
}
