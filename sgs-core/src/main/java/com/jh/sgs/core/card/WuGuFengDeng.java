package com.jh.sgs.core.card;

import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.interactive.Interactiveable;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.interfaces.MessageReceipt;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class WuGuFengDeng extends MoreSilkbagCard {
    private List<Card> cards;

    @Override
    String getName() {
        return "五谷丰登";
    }

    @Override
    public void begin() {
        cards = ContextManage.cardManage().obtainCard(ContextManage.desk().sizeOnDesk());
        MessageReceipt.globalInContext(getName() + ":获取的牌" + cards);
    }

    @Override
    public void end() {
        MessageReceipt.globalInContext(getName() + ":剩余的牌" + cards);
        ContextManage.executeCardDesktop().getProcessCards().addAll(cards);
    }

    @Override
    List<CompletePlayer> getPlayer() {
        List<CompletePlayer> completePlayers = new ArrayList<>();
        completePlayers.add(Util.getDesktopMainPlayer());
        completePlayers.addAll(ContextManage.roundManage().findTarget(CardDesktop.playerInContext(), CardDesktop.cardInContext()));
        return completePlayers;
    }

    @Override
    void effect(CompletePlayer completePlayer) {
        int mainPlayer = CardDesktop.playerInContext();
        log.debug("{}：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer);
        final Card[] card = new Card[1];
        InteractiveMachine.addEventInContext(completePlayer.getId(), "请选择牌", new Interactiveable() {
            boolean a = false;

            @Override
            public void setCard(int id) {
                card[0] = Util.collectionCollectAndCheckId(cards, id);
                a = true;
            }

            @Override
            public List<Card> targetCard() {
                return Util.collectionCloneToList(cards);
            }

            @Override
            public void cancel() {
                setCard(targetCard().get(0).getId());
            }

            @Override
            public CompleteEnum complete() {
                return a ? CompleteEnum.COMPLETE : CompleteEnum.NOEXECUTE;
            }

            @Override
            public InteractiveEnum type() {
                return InteractiveEnum.WGFDXZP;
            }
        }).lock();
        cards.remove(card[0]);
        completePlayer.getHandCard().add(card[0]);
        MessageReceipt.globalInContext(getName() + ":" + completePlayer.getId() + "选择了" + card[0]);
        log.debug("{}完成：执行玩家：{}，被执行玩家：{}，选择的卡牌：{}", getName(), mainPlayer, completePlayer, card[0]);
    }
}
