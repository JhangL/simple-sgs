package com.jh.sgs.core.enums;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.pojo.Ability;
import lombok.Getter;

public enum AbilityEnum {

//    S_LONG_DAN(new PlayCardAbility(7,"龙胆",getAbility(GeneralEnum.ZHAO_YUN)));
;

    @Getter
    private Ability ability;

    AbilityEnum(Ability ability) {
        this.ability = ability;
    }


    private static Ability.PlayCardAbilityable getAbility(GeneralEnum generalEnum){
        return  (Ability.PlayCardAbilityable) ContextManage.generalManage().getBaseGeneralMap().get(generalEnum);
    }
}
