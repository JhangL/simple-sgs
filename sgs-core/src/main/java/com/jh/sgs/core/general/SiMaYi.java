package com.jh.sgs.core.general;

import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.interactive.Interactiveable;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pool.BooleanPool;
import com.jh.sgs.base.pool.TPool;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.core.RoundManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.interactive.impl.JNXZPImpl;
import com.jh.sgs.core.interactive.impl.TOFImpl;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.roundevent.BeSubBloodEvent;
import com.jh.sgs.core.roundevent.DecideInvadeEvent;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class SiMaYi extends BaseGeneral {


    public SiMaYi(CompletePlayer completePlayer) {
        super(completePlayer);
        setSkill(new Object[]{new GuiCai(), new FanKui()});
    }


    class GuiCai implements DecideInvadeEvent {

        @Override
        public Card decideInvade() {
            BooleanPool tof = new BooleanPool();
            InteractiveMachine.addEventInContext(getPlayerIndex(), "是否使用鬼才", new TOFImpl(tof)).lock();

            if (tof.isPool()) {
                TPool<Card> cardTPool = new TPool<>();
                InteractiveMachine.addEventInContext(getPlayerIndex(), "(鬼才)请出牌", new JNXZPImpl(getCompletePlayer(), cardTPool::setPool, false)).lock();
                if (!cardTPool.isEmpty()) {
                    return cardTPool.getPool();
                }
            }
            return null;
        }

        @Override
        public int eventLocation() {
            return GLOBAL;
        }
    }

    class FanKui implements BeSubBloodEvent {

        @Override
        public void beSubBlood(int operatePlayer, TPool<Card> operateCard) {

            BooleanPool tof = new BooleanPool();
            InteractiveMachine.addEventInContext(getPlayerIndex(), "是否使用反馈", new TOFImpl(tof)).lock();
            if (tof.isPool()) {
                // 反馈
                CompletePlayer player = Util.getPlayer(operatePlayer);
                final Card[] card = new Card[1];//选择卡牌
                final int[] lossLocation = new int[1];//选择位置
                InteractiveMachine.addEventInContext(getPlayerIndex(), "请选择卡牌", new Interactiveable() {

                    boolean a;

                    @Override
                    public InteractiveEnum type() {
                        return InteractiveEnum.GHCQSSQYXZP;
                    }

                    @Override
                    public List<Card> handCard() {
                        List<Card> cards = Util.collectionCloneToList(player.getHandCard(), true);
                        for (Card card : cards) {
                            card.setNum(null);
                            card.setSuit(-1);
                            card.setNameId(-1);
                            card.setName(null);
                            card.setRemark(null);
                        }
                        return cards;
                    }

                    @Override
                    public List<Card> equipCard() {
                        return Util.arrayCloneToList(player.getEquipCard());
                    }

                    @Override
                    public void cancelPlayCard() {
                        a=true;
                    }

                    @Override
                    public void setCard(int id) {

                        try {
                            card[0] = Util.collectionCollectAndCheckId(player.getHandCard(), id);
                            player.getHandCard().remove(card[0]);
                            lossLocation[0] = RoundManage.HAND_CARD;
                        } catch (Exception e) {

                            card[0] = Util.ArrayCollectAndCheckId(player.getEquipCard(), id);
                            Util.ArrayRemove(player.getEquipCard(), card[0]);
                            lossLocation[0] = RoundManage.EQUIP_CARD;
                        }

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
                }).lock();
                //执行失牌，获得牌操作
                ContextManage.roundManage().loseCard(getPlayerIndex(), operatePlayer, card[0], lossLocation[0]);
                Util.getPlayer(getPlayerIndex()).getHandCard().add(card[0]);
            }
        }
    }
}
