package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ShanDian extends DelaySilkbagCard {
    @Override
    boolean decideTerm(Card card) {
        return false;
    }

    @Override
    void decideTrue() {

    }

    @Override
    void decideFalse() {

    }

    @Override
    int getPlayer() throws DesktopErrorException {
        return ContextManage.desktop().getPlayer();
    }

    @Override
    void effect(int player) {
        int mainPlayer = ContextManage.desktop().getPlayer();
        log.debug("{}：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, player);
        CompletePlayer player1 = Util.getPlayer(player);
        player1.getDecideCard().add(0, ContextManage.desktop().getCard());
        ContextManage.desktop().useCard();
        log.debug("{}完成：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, player);
    }
}
