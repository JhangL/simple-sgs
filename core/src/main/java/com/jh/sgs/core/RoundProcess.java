package com.jh.sgs.core;

import com.jh.sgs.exception.SgsException;
import com.jh.sgs.interfaces.Interactiveable;
import com.jh.sgs.pojo.Card;
import com.jh.sgs.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

import static com.jh.sgs.core.ContextManage.*;


@Log4j2
public class RoundProcess{
    CompletePlayer completePlayer;
    int playerIndex;

    public RoundProcess(CompletePlayer completePlayer) {
        this.completePlayer = completePlayer;
        playerIndex = desk().index(completePlayer);
    }
    void process(){
        log.info(playerIndex+"回合");
        messageReceipt().global(playerIndex+"回合");
        messageReceipt().global(playerIndex+"开始阶段");
        start();
        messageReceipt().global(playerIndex+"判定阶段");
        decide();
        messageReceipt().global(playerIndex+"摸牌阶段");
        obtainCard();
        messageReceipt().global(playerIndex+"出牌阶段");
        playCard();
        messageReceipt().global(playerIndex+"弃牌阶段");
        discardCard();
        messageReceipt().global(playerIndex+"结束阶段");
        end();
    }

    void start(){

    }

    void decide(){

    }

    void obtainCard(){
        completePlayer.getHandCard().addAll( cardManage().obtainCard(2));
    }
    void playCard(){
        final boolean[] playWhile = {true};
        while (playWhile[0]){
            interactiveMachine().addEvent(playerIndex, new Interactiveable() {
                @Override
                public void cancelPlayCard() {
                    log.debug("取消出牌");
                    playWhile[0] =false;
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

                @Override
                public String message() {
                    return "请出牌";
                }
            });
            interactiveMachine().lock();
        }

    }
    void discardCard(){
        int i = completePlayer.getHandCard().size() - completePlayer.getBlood();
        if (i>0){
            interactiveMachine().addEvent(playerIndex, new Interactiveable() {
                boolean c=false;
                @Override
                public List<Card> handCard() {
                    return new ArrayList<>(completePlayer.getHandCard());
                }

                @Override
                public void discardCard(List<Card> cards) {
                    if (cards.size()!=i) throw new SgsException("弃牌数与要求数不符");
                    if (!completePlayer.getHandCard().containsAll(cards)) throw new SgsException("所指定的牌并非原数据本身");
                    cards.forEach(completePlayer.getHandCard()::remove);
                    ContextManage.cardManage().recoveryCard(cards);
                    log.debug(i+"弃牌:"+cards);
                    c=true;
                }

                @Override
                public void cancel() {

                    discardCard(handCard().subList(0,i));

                }

                @Override
                public boolean complete() {
                    log.debug(i+"成功弃牌");
                    return c;
                }

                @Override
                public String message() {
                    return "请弃"+i+"张牌";
                }
            });
            interactiveMachine().lock();
        }
    }
    void end(){

    }
}