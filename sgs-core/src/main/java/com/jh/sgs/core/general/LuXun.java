package com.jh.sgs.core.general;

import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.roundevent.CardTargetHideEvent;
import com.jh.sgs.core.roundevent.LossCardEvent;

import java.util.List;

public class LuXun extends BaseGeneral implements CardTargetHideEvent, LossCardEvent {
    public LuXun(CompletePlayer completePlayer) {
        super(completePlayer);
    }

    @Override
    public boolean hide(Card card) {
        //谦逊
        return card.getNameId() == CardEnum.SHUN_SHOU_QIAN_YANG.getId() || card.getNameId() == CardEnum.LE_BU_SI_SHU.getId();
    }

    @Override
    public void loseCard(int operatePlayer, int lossLocation) {
        //连营
        if (lossLocation == HAND_CARD) {
            if (getCompletePlayer().getHandCard().isEmpty()) {
                List<Card> cards = ContextManage.cardManage().obtainCard(1);
                getCompletePlayer().getHandCard().addAll(cards);
            }
        }
    }

}
