package com.jh.sgs.core.card;

import com.jh.sgs.core.pojo.EquipCardEnum;

public abstract class WeaponCard extends EquipCard{

    @Override
    EquipCardEnum equipType() {
        return EquipCardEnum.WEAPON;
    }
}
