package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.enums.InteractiveEnum;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.pojo.Card;
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
        ContextManage.messageReceipt().global(getName() + ":获取的牌" + cards);
    }

    @Override
    public void end() {
        ContextManage.messageReceipt().global(getName() + ":剩余的牌" + cards);
        ContextManage.executeCardDesktop().getProcessCards().addAll(cards);
    }

    @Override
    List<CompletePlayer> getPlayer() {
        List<CompletePlayer> completePlayers = new ArrayList<>();
        completePlayers.add(Util.getDesktopMainPlayer());
        completePlayers.addAll(ContextManage.roundManage().findTarget(ContextManage.executeCardDesktop().getPlayer(), ContextManage.executeCardDesktop().getCard()));
        return completePlayers;
    }

    @Override
    void effect(CompletePlayer completePlayer) {
        int mainPlayer = ContextManage.executeCardDesktop().getPlayer();
        log.debug("{}：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer);
        final Card[] card = new Card[1];
        ContextManage.interactiveMachine().addEvent(completePlayer.getId(), "请选择牌", new Interactiveable() {
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
            public InteractiveEvent.CompleteEnum complete() {
                return a ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
            }

            @Override
            public InteractiveEnum type() {
                return InteractiveEnum.WGFDXZP;
            }
        });
        ContextManage.interactiveMachine().lock();
        cards.remove(card[0]);
        completePlayer.getHandCard().add(card[0]);
        ContextManage.messageReceipt().global(getName() + ":" + completePlayer.getId() + "选择了" + card[0]);
        log.debug("{}完成：执行玩家：{}，被执行玩家：{}，选择的卡牌：{}", getName(), mainPlayer, completePlayer, card[0]);
    }
}
