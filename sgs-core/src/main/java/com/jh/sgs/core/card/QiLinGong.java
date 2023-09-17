package com.jh.sgs.core.card;

import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.interactive.Interactiveable;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pool.BooleanPool;
import com.jh.sgs.base.pool.TPool;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.core.RoundManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.interactive.impl.TOFImpl;
import com.jh.sgs.core.pojo.CompletePlayer;

import java.util.ArrayList;
import java.util.List;

public class QiLinGong extends WeaponCard {
    @Override
    public void shaExecute(int player) {
        CompletePlayer player1 = Util.getPlayer(player);
        int mainplayer = CardDesktop.playerInContext();
        CompletePlayer maincompletePlayer = Util.getPlayer(mainplayer);
        TPool<Card> card = new TPool<>();
        boolean b = ContextManage.roundManage().playSha(mainplayer, player, CardDesktop.cardInContext(), card);
        if (card.getPool() != null) ContextManage.shaCardDesktop().getProcessCards().add(card.getPool());
        if (b) {
            player1.setBlood(player1.getBlood() - 1);
            TPool<Card> cardTPool = new TPool<>(CardDesktop.cardInContext());
            ContextManage.roundManage().subBlood(mainplayer, player,cardTPool, 1);
            if (cardTPool.isEmpty())ContextManage.shaCardDesktop().useCard();
            //麒麟弓特殊效果
            ArrayList<Card> cards = new ArrayList<>();
            if (player1.getEquipCard()[2] != null) cards.add(player1.getEquipCard()[2]);
            if (player1.getEquipCard()[3] != null) cards.add(player1.getEquipCard()[3]);
            //检查目标坐骑牌是否为空
            if (!cards.isEmpty()) {
                BooleanPool tofs = new BooleanPool();
                InteractiveMachine.addEventInContext(mainplayer, "是否使用" + getName(), new TOFImpl(tofs));
                if (tofs.isPool()) {
                    if (cards.size() == 1) {
                        //一个坐骑直接弃掉
                        Card card1 = cards.get(0);
                        if (player1.getEquipCard()[2] == card1) player1.getEquipCard()[2] = null;
                        else if (player1.getEquipCard()[3] == card1) player1.getEquipCard()[3] = null;
                        ContextManage.roundManage().loseCard(mainplayer, player, card1, RoundManage.EQUIP_CARD);
                        ContextManage.shaCardDesktop().getProcessCards().add(card1);
                    } else {
                        //选一个
                        Card[] cards1 = new Card[1];
                        InteractiveMachine.addEventInContext(mainplayer, "选择要弃的牌", new Interactiveable() {
                            boolean a = false;

                            @Override
                            public void cancelPlayCard() {
                                a = true;
                            }

                            @Override
                            public void setCard(int id) {
                                cards1[0] = Util.collectionCollectAndCheckId(cards, id);
                                a = true;
                            }

                            @Override
                            public List<Card> targetCard() {
                                return Util.collectionCloneToList(cards);
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
                                return InteractiveEnum.XZP;
                            }
                        });
                        if (cards1[0] != null) {
                            if (player1.getEquipCard()[2] == cards1[0]) player1.getEquipCard()[2] = null;
                            else if (player1.getEquipCard()[3] == cards1[0]) player1.getEquipCard()[3] = null;
                            ContextManage.roundManage().loseCard(mainplayer, player, cards1[0], RoundManage.EQUIP_CARD);
                            ContextManage.shaCardDesktop().getProcessCards().add(cards1[0]);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int shaDistance() {
        return 5;
    }

    @Override
    String getName() {
        return "麒麟弓";
    }
}
