package com.jh.sgs.core.card;

import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.interactive.Interactiveable;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pool.BooleanPool;
import com.jh.sgs.base.pool.TPool;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.interactive.impl.TOFImpl;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class GuanShiFu extends WeaponCard {
    @Override
    public void shaExecute(int player){
        CompletePlayer player1 = Util.getPlayer(player);
        int mainplayer = CardDesktop.playerInContext();
        CompletePlayer maincompletePlayer = Util.getPlayer(mainplayer);
        TPool<Card> card = new TPool<>();
        boolean b = ContextManage.roundManage().playSha(mainplayer, player, CardDesktop.cardInContext(), card);
        if (card.getPool() != null) ContextManage.shaCardDesktop().getProcessCards().add(card.getPool());
        if (b) {
            player1.setBlood(player1.getBlood() - 1);
            TPool<Card> cardTPool = new TPool<>(CardDesktop.cardInContext());
            ContextManage.roundManage().subBlood(mainplayer, player, cardTPool, 1);
            if (cardTPool.isEmpty())ContextManage.shaCardDesktop().useCard();
        } else {
            //贯石斧特殊效果
            if (card.getPool() != null) {//被闪闪避

                BooleanPool tofs = new BooleanPool();
                InteractiveMachine.addEventInContext(mainplayer, "是否使用贯石斧", new TOFImpl(tofs));
                if (tofs.isPool()) {
                    ArrayList<Card> dis = new ArrayList<>();
                    InteractiveMachine.addEventInContext(mainplayer, "请弃2张牌", new Interactiveable() {

                        boolean a;

                        @Override
                        public void disCard(int[] ids) {
                            if (ids.length != 2) throw new SgsApiException("弃牌数与要求数不符");
                            List<Card> handCard = maincompletePlayer.getHandCard();
                            ArrayList<Card> cards = Util.collectionCollectAndCheckIds(handCard, ids);
                            handCard.removeAll(cards);
                            log.debug(mainplayer + "弃牌:" + cards);
                            dis.addAll(cards);
                            a = true;
                        }

                        @Override
                        public int discardNum() {
                            return 2;
                        }

                        @Override
                        public List<Card> handCard() {
                            return Util.collectionCloneToList(maincompletePlayer.getHandCard());
                        }

                        @Override
                        public void cancelPlayCard() {
                            a = true;
                        }

                        @Override
                        public void cancel() {
                            cancelPlayCard();
                        }

                        @Override
                        public CompleteEnum complete() {
                            return a ? CompleteEnum.COMPLETE : CompleteEnum.NOEXECUTE;
                        }

                        @Override
                        public InteractiveEnum type() {
                            return InteractiveEnum.GSF;
                        }
                    });
                    ContextManage.shaCardDesktop().getProcessCards().addAll(dis);
                    if (dis.size()==2) {
                        player1.setBlood(player1.getBlood() - 1);
                        TPool<Card> cardTPool = new TPool<>(CardDesktop.cardInContext());
                        ContextManage.roundManage().subBlood(mainplayer, player, cardTPool, 1);
                        if (cardTPool.isEmpty())ContextManage.shaCardDesktop().useCard();
                    }
                }
            }
        }

    }

    @Override
    public int shaDistance() {
        return 3;
    }

    @Override
    String getName() {
        return "贯石斧";
    }
}
