package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.enums.EquipCardEnum;
import com.jh.sgs.core.roundevent.DefenseEvent;

public class AddHorse extends EquipCard implements DefenseEvent ,Loseable{

    @Override
    public void execute() {
        super.execute();
        ContextManage.roundManage().addEvent(ContextManage.executeCardDesktop().getPlayer(), this);
    }

    @Override
    EquipCardEnum equipType() {
        return EquipCardEnum.ADD_HORSE;
    }

    @Override
    String getName() {
        return "+1马";
    }

    @Override
    public int defense() {
        return 1;
    }

    @Override
    public void lose(int player) {
        ContextManage.roundManage().subEvent(player,this);
    }
}
