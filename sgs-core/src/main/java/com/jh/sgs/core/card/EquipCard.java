package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.RoundManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.enums.EquipCardEnum;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class EquipCard extends BaseCard implements Executable{

    @Override
    public void execute() {
        log.debug("执行{}-->",getName());
        ContextManage.messageReceipt().global(ContextManage.executeCardDesktop().getPlayer() +"装备"+ContextManage.executeCardDesktop().getCard());
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
        completePlayer.getEquipCard()[equipType().ordinal()] = ContextManage.executeCardDesktop().getCard();
        ContextManage.executeCardDesktop().useCard();
        ContextManage.roundManage().statusRefresh(ContextManage.executeCardDesktop().getPlayer(),ContextManage.executeCardDesktop().getPlayer());
        log.debug(id + "装备装备牌" + ContextManage.executeCardDesktop().getCard());
        ContextManage.messageReceipt().global(ContextManage.executeCardDesktop().getPlayer() +"完成装备"+ContextManage.executeCardDesktop().getCard());
    }

    abstract EquipCardEnum equipType();

}
