package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pool.TPool;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class WanJianQiFa extends MoreSilkbagCard {
    @Override
    String getName() {
        return "万箭齐发";
    }

    @Override
    List<CompletePlayer> getPlayer() {
        return ContextManage.roundManage().findTarget(CardDesktop.playerInContext(), CardDesktop.cardInContext());
    }

    @Override
    void effect(CompletePlayer completePlayer) throws DesktopException {
        int mainPlayer = CardDesktop.playerInContext();
        log.debug("{}：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer.getId());
        TPool<Card> playWhile = new TPool<>();
        ContextManage.roundManage().playCard(completePlayer.getId(), "请出闪", playWhile, card -> {
            if (card.getNameId() != CardEnum.SHAN.getId())
                throw new SgsApiException("指定牌不为闪");
        },false);
        if (playWhile.getPool() == null) {
            completePlayer.setBlood(completePlayer.getBlood() - 1);
            TPool<Card> cardTPool = new TPool<>(CardDesktop.cardInContext());
            ContextManage.roundManage().subBlood(mainPlayer, completePlayer.getId(),cardTPool, 1);
            if (cardTPool.isEmpty())ContextManage.executeCardDesktop().useCard();
        } else {
            ContextManage.executeCardDesktop().getProcessCards().add(playWhile.getPool());
        }
        log.debug("{}完成：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer.getId());
    }
}
