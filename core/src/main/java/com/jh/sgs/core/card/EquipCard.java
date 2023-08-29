package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.RoundManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.EquipCardEnum;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class EquipCard extends BaseCard implements Executable{

    @Override
    public void execute() {
        CompletePlayer completePlayer = Util.getDesktopMainPlayer();
        int id = completePlayer.getId();
        Card card = completePlayer.getEquipCard()[equipType().ordinal()];
        if (card != null) {
            completePlayer.getEquipCard()[equipType().ordinal()] = null;
            //调用失去牌事件
            ContextManage.roundManage().loseCard(id,id,card, RoundManage.EQUIP_CARD);
            log.debug(id + "换下装备牌" + card);
            ContextManage.cardManage().recoveryCard(card);
        }
        completePlayer.getEquipCard()[equipType().ordinal()] = ContextManage.desktop().getCard();
        log.debug(id + "装备装备牌" + ContextManage.desktop().getCard());
    }

    abstract EquipCardEnum equipType();

}
