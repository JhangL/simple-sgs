package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pool.TPool;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class NanManRuQin extends MoreSilkbagCard {
    @Override
    String getName() {
        return "南蛮入侵";
    }

    @Override
    List<CompletePlayer> getPlayer() {
        return ContextManage.roundManage().findTarget(ContextManage.executeCardDesktop().getPlayer(), ContextManage.executeCardDesktop().getCard());
    }

    @Override
    void effect(CompletePlayer completePlayer) throws DesktopException {
        int mainPlayer = ContextManage.executeCardDesktop().getPlayer();
        log.debug("{}：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer.getId());
        //todo 南蛮入侵响应时，可能需要重新修改逻辑，当前事件执行人并不是卡牌执行事件的发起者，响应需要杀，需要添加技能等监听处理
        TPool<Card> playWhile = new TPool<>();
        ContextManage.roundManage().playCard(completePlayer.getId(), "请出杀", playWhile, card -> {
            if (card.getNameId() != CardEnum.SHA.getId())
                throw new SgsApiException("指定牌不为杀");
        },false);
        if (playWhile.getPool() == null) {
            completePlayer.setBlood(completePlayer.getBlood() - 1);
            ContextManage.roundManage().subBlood(mainPlayer, completePlayer.getId(), ContextManage.executeCardDesktop().getCard(), 1);
        } else {
            ContextManage.executeCardDesktop().getProcessCards().add(playWhile.getPool());
        }
        log.debug("{}完成：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer.getId());
    }
}
