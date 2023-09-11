package com.jh.sgs.core.enums;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.pojo.PlayCardAbility;
import lombok.Getter;

public enum AbilityEnum {

//    S_LONG_DAN(new PlayCardAbility(7,"龙胆",getAbility(GeneralEnum.ZHAO_YUN)));
;

    @Getter
    private PlayCardAbility playCardAbility;

    AbilityEnum(PlayCardAbility playCardAbility) {
        this.playCardAbility = playCardAbility;
    }


    private static PlayCardAbility.PlayCardAbilityable getAbility(GeneralEnum generalEnum){
        return  (PlayCardAbility.PlayCardAbilityable) ContextManage.generalManage().getBaseGeneralMap().get(generalEnum);
    }
}
