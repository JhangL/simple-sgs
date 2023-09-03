package com.jh.sgs.core;

import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.EventLock;
import com.jh.sgs.core.pojo.InteractiveEnum;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.jh.sgs.core.ContextManage.*;


@Log4j2
public class RoundProcess {
    @Getter
    private CompletePlayer completePlayer;
    @Getter
    private int playerIndex;
    @Getter
    private final EventLock playCardBool =new EventLock();
    public RoundProcess(CompletePlayer completePlayer) {
        this.completePlayer = completePlayer;
        playerIndex = completePlayer.getId();
    }

    void process() {
        log.info(playerIndex + "回合");
        messageReceipt().global(playerIndex + "回合");
        messageReceipt().global(playerIndex + "开始阶段");
        start();
        messageReceipt().global(playerIndex + "判定阶段");
        decide();
        messageReceipt().global(playerIndex + "摸牌阶段");
        obtainCard();
        messageReceipt().global(playerIndex + "出牌阶段");
        if(!playCardBool.isLock())playCard();
        else messageReceipt().global(playerIndex + "跳过出牌阶段 "+playCardBool.getEvent());
        messageReceipt().global(playerIndex + "弃牌阶段");
        discardCard();
        messageReceipt().global(playerIndex + "结束阶段");
        end();
    }

    void start() {
        playCardBool.setFalse("正常回合开始");
    }

    void decide() {

    }

    void obtainCard() {
        completePlayer.getHandCard().addAll(cardManage().obtainCard(2));
    }

    void playCard() {
        Card[] cards = new Card[]{null};
        do {
            cards[0] = null;
            interactiveMachine().addEvent(ContextManage.roundManage().playCard(playerIndex, "请出牌", cards, Desktop::initCheck));
            interactiveMachine().lock();
            if (cards[0] != null) {
                desktopStack().create(playerIndex, cards[0]);
                try {
                    desktopStack().remove();
                } catch (DesktopException e) {
                    throw new RuntimeException("系统错误");
                }
            }
        } while (cards[0] != null);

    }

    void discardCard() {
        int i = completePlayer.getHandCard().size() - completePlayer.getBlood();
        if (i > 0) {
            interactiveMachine().addEvent(playerIndex, "请弃" + i + "张牌", new Interactiveable() {
                boolean c = false;

                @Override
                public InteractiveEnum type() {
                    return InteractiveEnum.QP;
                }

                @Override
                public int discardNum() {
                    return i;
                }

                @Override
                public List<Card> handCard() {
                    return Util.collectionCloneToList(completePlayer.getHandCard());
                }

                @Override
                public void disCard(int[] ids) {
                    if (ids.length != i) throw new SgsApiException("弃牌数与要求数不符");
                    Set<Card> handCard = completePlayer.getHandCard();
                    ArrayList<Card> cards = Util.collectionCollectAndCheckIds(handCard, ids);
                    cards.forEach(handCard::remove);
                    ContextManage.cardManage().recoveryCard(cards);
                    log.debug(playerIndex + "弃牌:" + cards);
                    c = true;
                }

                @Override
                public void cancel() {
                    List<Card> cards = handCard();
                    int[] ints = new int[i];
                    for (int j = 0, intsLength = ints.length; j < intsLength; j++) {
                        ints[j] = cards.get(j).getId();
                    }
                    disCard(ints);
                }

                @Override
                public InteractiveEvent.CompleteEnum complete() {
//                    log.debug(i + "成功弃牌");
                    return c ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
                }
            });
            interactiveMachine().lock();
        }
    }

    void end() {

    }
}