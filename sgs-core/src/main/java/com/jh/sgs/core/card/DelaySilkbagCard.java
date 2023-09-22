package com.jh.sgs.core.card;

import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.DesktopPlayerDieException;
import com.jh.sgs.core.exception.DesktopRefuseException;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.MessageReceipter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class DelaySilkbagCard extends OneSilkbagCard implements Decidable {
    @Override
    public void decide() {
        log.debug("{}执行判定-->", getName());
        try {
            ContextManage.roundManage().wxkjCheck();
        } catch (DesktopRefuseException e) {
            log.debug("{}被阻挡", getName());
            decideFalse();
            return;
        }
        Card card = ContextManage.roundManage().decide(ContextManage.decideCardDesktop().getPlayer());
        log.debug("{}判定牌：{}", getName(), card);
        boolean b = decideTerm(card);
        if (b) {
            log.debug("{}判定成功", getName());
            decideTrue();
        } else {
            log.debug("{}判定失败", getName());
            decideFalse();
        }
    }

    abstract boolean decideTerm(Card card);

    abstract void decideTrue() throws DesktopPlayerDieException;

    abstract void decideFalse();

    @Override
    public void effect() throws DesktopException {
        int player = getPlayer();
        MessageReceipter.globalInContext(CardDesktop.playerInContext() + "将对" + player + "使用" + CardDesktop.cardInContext());
        effect(player);
        MessageReceipter.personalInContext(player,"{}对你使用{}",CardDesktop.playerInContext(),CardDesktop.cardInContext());
        MessageReceipter.personalInContext(CardDesktop.playerInContext(),"你对{}使用{}",player,CardDesktop.cardInContext());
        MessageReceipter.globalInContext(CardDesktop.playerInContext() + "完成对" + player + "使用" + CardDesktop.cardInContext());
    }

    @Override
    void effect(int player) {
        int mainPlayer = CardDesktop.playerInContext();
        log.debug("{}：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, player);
        CompletePlayer player1 = Util.getPlayer(player);
        player1.getDecideCard().add(0, CardDesktop.cardInContext());
        ContextManage.executeCardDesktop().useCard();
        ContextManage.roundManage().statusRefresh(mainPlayer, player);
        log.debug("{}完成：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, player);
    }

}
