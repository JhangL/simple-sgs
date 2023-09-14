package com.jh.sgs.core.general;

import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.core.interactive.impl.JNXZPImpl;
import com.jh.sgs.core.interactive.impl.TOFImpl;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pool.BooleanPool;
import com.jh.sgs.core.pool.TPool;
import com.jh.sgs.core.roundevent.BeSubBloodEvent;
import com.jh.sgs.core.roundevent.DecideInvadeEvent;

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

    class FanKui implements BeSubBloodEvent{

        @Override
        public void beSubBlood(int operatePlayer, TPool<Card> operateCard) {
            BooleanPool tof = new BooleanPool();
            InteractiveMachine.addEventInContext(getPlayerIndex(), "是否使用反馈", new TOFImpl(tof)).lock();
            if (tof.isPool()){
                //todo 反馈
            }
        }
    }
}
