package com.jh.sgs.core.general;

import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.roundevent.CardTargetHideEvent;

public class LuXun extends BaseGeneral implements CardTargetHideEvent  {
    public LuXun(CompletePlayer completePlayer) {
        super(completePlayer);
    }

    @Override
    public boolean hide(Card card) {
        //谦逊
        return card.getNameId() == CardEnum.SHUN_SHOU_QIAN_YANG.getId() || card.getNameId() == CardEnum.LE_BU_SI_SHU.getId();
    }
    //todo 连营
}
