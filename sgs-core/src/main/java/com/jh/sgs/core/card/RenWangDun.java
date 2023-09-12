package com.jh.sgs.core.card;

import com.jh.sgs.core.enums.EquipCardEnum;

public class RenWangDun extends EquipCard{
    @Override
    EquipCardEnum equipType() {
        return EquipCardEnum.ARMOR;
    }

    @Override
    String getName() {
        return "仁王盾";
    }
}
