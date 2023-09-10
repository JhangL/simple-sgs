package com.jh.sgs.core.card;

import com.jh.sgs.core.enums.EquipCardEnum;

public abstract class WeaponCard extends EquipCard implements Shaable{

    @Override
    EquipCardEnum equipType() {
        return EquipCardEnum.WEAPON;
    }

}
