package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.EquipCardEnum;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class EquipCard extends ExecutableCard {

    @Override
    public void execute() {
        CompletePlayer completePlayer = ContextManage.desktop().getCompletePlayer();
        int id = completePlayer.getId();
        Card card = completePlayer.getEquipCard()[equipType().ordinal()];
        // todo 换装备，涉及失牌
        if (card != null) {
            completePlayer.getEquipCard()[equipType().ordinal()] = null;
            log.debug(id + "换下装备牌" + card);
            ContextManage.cardManage().recoveryCard(card);
        }
        completePlayer.getEquipCard()[equipType().ordinal()] = ContextManage.desktop().getCard();
        log.debug(id + "装备装备牌" + ContextManage.desktop().getCard());
    }

    abstract EquipCardEnum equipType();

}
