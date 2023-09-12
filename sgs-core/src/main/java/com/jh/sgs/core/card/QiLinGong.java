package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.RoundManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.enums.InteractiveEnum;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.interactive.impl.TOFImpl;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pool.BooleanPool;
import com.jh.sgs.core.pool.TPool;

import java.util.ArrayList;
import java.util.List;

public class QiLinGong extends WeaponCard {
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
            //麒麟弓特殊效果
            ArrayList<Card> cards = new ArrayList<>();
            if (player1.getEquipCard()[2] != null) cards.add(player1.getEquipCard()[2]);
            if (player1.getEquipCard()[3] != null) cards.add(player1.getEquipCard()[3]);
            //检查目标坐骑牌是否为空
            if (!cards.isEmpty()) {
                BooleanPool tofs = new BooleanPool();
                ContextManage.interactiveMachine().addEvent(mainplayer, "是否使用" + getName(), new TOFImpl(tofs));
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
                        ContextManage.interactiveMachine().addEvent(mainplayer, "选择要弃的牌", new Interactiveable() {
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
                            public InteractiveEvent.CompleteEnum complete() {
                                return a ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
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
        return 0;
    }

    @Override
    String getName() {
        return "麒麟弓";
    }
}
