package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CardEnum;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.InteractiveEnum;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Set;

@Log4j2
public class WanJianQiFa extends MoreSilkbagCard {
    @Override
    String getName() {
        return "万箭齐发";
    }

    @Override
    List<CompletePlayer> getPlayer() {
        return ContextManage.roundManage().findTarget(ContextManage.desktop().getPlayer(), ContextManage.desktop().getCard());
    }

    @Override
    void effect(CompletePlayer completePlayer) throws DesktopException {
        int mainPlayer = ContextManage.desktop().getPlayer();
        log.debug("{}：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer.getId());
        //todo 万箭齐发响应时，可能需要重新修改逻辑，当前事件执行人并不是卡牌执行事件的发起者，响应需要闪，需要添加技能等监听处理
        Card[] playWhile = new Card[1];
        ContextManage.interactiveMachine().addEvent(completePlayer.getId(), "请出闪", new Interactiveable() {

            boolean a, b;

            @Override
            public List<Card> handCard() {
                return Util.collectionCloneToList(completePlayer.getHandCard());
            }

            @Override
            public void cancelPlayCard() {
                log.debug("取消出牌");
                playWhile[0] = null;
                b = true;
            }

            @Override
            public void playCard(int id) {
                Set<Card> handCard = completePlayer.getHandCard();
                Card card = Util.collectionCollectAndCheckId(handCard, id);
                if (card.getNameId() != CardEnum.SHAN.getId())
                    throw new SgsApiException("指定牌不为闪");
                handCard.remove(card);
                playWhile[0] = card;
                log.debug(completePlayer.getId() + "出牌:" + card);
                a = true;
            }

            @Override
            public void cancel() {
                cancelPlayCard();
            }

            @Override
            public InteractiveEvent.CompleteEnum complete() {
                return a || b ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
            }

            @Override
            public InteractiveEnum type() {
                return InteractiveEnum.CP;
            }
        });
        ContextManage.interactiveMachine().lock();
        if (playWhile[0] == null) {
            completePlayer.setBlood(completePlayer.getBlood() - 1);
            ContextManage.roundManage().subBlood(mainPlayer, completePlayer.getId(), ContextManage.desktop().getCard(), 1);
        }else {
            ContextManage.desktop().getProcessCards().add(playWhile[0]);
        }
        log.debug("{}完成：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer.getId());
    }
}
