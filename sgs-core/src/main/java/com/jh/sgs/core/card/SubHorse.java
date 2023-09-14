package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.enums.EquipCardEnum;
import com.jh.sgs.core.roundevent.OffenseEvent;

public class SubHorse extends EquipCard implements OffenseEvent, Loseable {
    @Override
    public void execute() {
        super.execute();
        ContextManage.roundManage().addEvent(CardDesktop.playerInContext(), this);
    }

    @Override
    EquipCardEnum equipType() {
        return EquipCardEnum.SUB_HORSE;
    }

    @Override
    public int offense() {
        return 1;
    }

    @Override
    String getName() {
        return "-1马";
    }

    @Override
    public void lose(int player) {
        ContextManage.roundManage().subEvent(player, this);
    }
}
