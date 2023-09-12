package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.enums.InteractiveEnum;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.interactive.impl.TOFImpl;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pool.BooleanPool;
import com.jh.sgs.core.pool.TPool;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log4j2
public class GuanShiFu extends WeaponCard {
    @Override
    public void shaExecute(int player) {
        CompletePlayer player1 = Util.getPlayer(player);
        int mainplayer = ContextManage.shaCardDesktop().getPlayer();
        CompletePlayer maincompletePlayer = Util.getPlayer(mainplayer);
        TPool<Card> card = new TPool<>();
        boolean b = ContextManage.roundManage().playSha(mainplayer, player, ContextManage.shaCardDesktop().getCard(), card);
        if (card.getPool() != null) ContextManage.shaCardDesktop().getProcessCards().add(card.getPool());
        if (b) {
            player1.setBlood(player1.getBlood() - 1);
            ContextManage.roundManage().subBlood(mainplayer, player, ContextManage.shaCardDesktop().getCard(), 1);
        } else {
            //贯石斧特殊效果
            if (card.getPool() != null) {//被闪闪避

                BooleanPool tofs = new BooleanPool();
                ContextManage.interactiveMachine().addEvent(mainplayer, "是否使用贯石斧", new TOFImpl(tofs));
                if (tofs.isPool()) {
                    boolean[] dis = new boolean[1];
                    ContextManage.interactiveMachine().addEvent(mainplayer, "请弃2张牌", new Interactiveable() {

                        boolean a;

                        @Override
                        public void disCard(int[] ids) {
                            if (ids.length != 2) throw new SgsApiException("弃牌数与要求数不符");
                            Set<Card> handCard = maincompletePlayer.getHandCard();
                            ArrayList<Card> cards = Util.collectionCollectAndCheckIds(handCard, ids);
                            cards.forEach(handCard::remove);
                            ContextManage.shaCardDesktop().getProcessCards().addAll(cards);
                            log.debug(mainplayer + "弃牌:" + cards);
                            dis[0] = true;
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
                        public InteractiveEvent.CompleteEnum complete() {
                            return a ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
                        }

                        @Override
                        public InteractiveEnum type() {
                            return InteractiveEnum.GSF;
                        }
                    });
                    if (dis[0]) {
                        player1.setBlood(player1.getBlood() - 1);
                        ContextManage.roundManage().subBlood(mainplayer, player, ContextManage.shaCardDesktop().getCard(), 1);
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
