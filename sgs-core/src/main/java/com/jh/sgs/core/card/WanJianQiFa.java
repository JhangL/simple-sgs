package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
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
        return ContextManage.roundManage().findTarget(ContextManage.executeCardDesktop().getPlayer(), ContextManage.executeCardDesktop().getCard());
    }

    @Override
    void effect(CompletePlayer completePlayer) throws DesktopException {
        int mainPlayer = ContextManage.executeCardDesktop().getPlayer();
        log.debug("{}：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer.getId());
        //todo 万箭齐发响应时，可能需要重新修改逻辑，当前事件执行人并不是卡牌执行事件的发起者，响应需要闪，需要添加技能等监听处理
        Card[] playWhile = new Card[1];
        ContextManage.roundManage().playCard(completePlayer.getId(), "请出闪", playWhile, card -> {
            if (card.getNameId() != CardEnum.SHAN.getId())
                throw new SgsApiException("指定牌不为闪");
        },false);
        if (playWhile[0] == null) {
            completePlayer.setBlood(completePlayer.getBlood() - 1);
            ContextManage.roundManage().subBlood(mainPlayer, completePlayer.getId(), ContextManage.executeCardDesktop().getCard(), 1);
        } else {
            ContextManage.executeCardDesktop().getProcessCards().add(playWhile[0]);
        }
        log.debug("{}完成：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer.getId());
    }
}
