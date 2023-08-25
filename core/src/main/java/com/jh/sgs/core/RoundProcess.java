package com.jh.sgs.core;

import com.jh.sgs.core.exception.SgsException;
import com.jh.sgs.core.interfaces.Interactiveable;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.jh.sgs.core.ContextManage.*;


@Log4j2
public class RoundProcess {
    CompletePlayer completePlayer;
    int playerIndex;

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
        playCard();
        messageReceipt().global(playerIndex + "弃牌阶段");
        discardCard();
        messageReceipt().global(playerIndex + "结束阶段");
        end();
    }

    void start() {

    }

    void decide() {

    }

    void obtainCard() {
        completePlayer.getHandCard().addAll(cardManage().obtainCard(2));
    }

    void playCard() {
        final boolean[] playWhile = {true};
        while (playWhile[0]) {
            interactiveMachine().addEvent(playerIndex, "请出牌", new Interactiveable() {

                boolean cancel;
                boolean play;

                @Override
                public void cancelPlayCard() {
                    log.debug("取消出牌");
                    playWhile[0] = false;
                    cancel = true;
                }

                @Override
                public List<Card> handCard() {
                    return Util.collectionCloneToList(completePlayer.getHandCard());
                }

                @Override
                public void playCard(int id) {
                    Set<Card> handCard = completePlayer.getHandCard();
                    Card card = Util.collectionCollectAndCheckId(handCard, id);
                    if (card == null) throw new SgsException("给定id并非原数据id");
                    ContextManage.desktopStack().create(completePlayer,card);
                    handCard.remove(card);
                    log.debug(playerIndex + "出牌:" + card);
                    play = true;
                }

                @Override
                public void cancel() {
                    cancelPlayCard();
                }

                @Override
                public boolean complete() {
                    log.debug("完成出牌阶段");
                    return true;
                }
            });
            interactiveMachine().lock();
            desktopStack().remove();
        }

    }

    void discardCard() {
        int i = completePlayer.getHandCard().size() - completePlayer.getBlood();
        if (i > 0) {
            interactiveMachine().addEvent(playerIndex, "请弃" + i + "张牌", new Interactiveable() {
                boolean c = false;

                @Override
                public List<Card> handCard() {
                    return Util.collectionCloneToList(completePlayer.getHandCard());
                }

                @Override
                public void discardCard(int[] ids) {
                    if (ids.length != i) throw new SgsException("弃牌数与要求数不符");
                    Set<Card> handCard = completePlayer.getHandCard();
                    ArrayList<Card> cards;
                    if (!Util.collectionCollectAndCheckIds(handCard, ids, cards = new ArrayList<>()))
                        throw new SgsException("存在给定id并非原数据id");
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
                    discardCard(ints);
                }

                @Override
                public boolean complete() {
                    log.debug(i + "成功弃牌");
                    return c;
                }
            });
            interactiveMachine().lock();
        }
    }

    void end() {

    }
}