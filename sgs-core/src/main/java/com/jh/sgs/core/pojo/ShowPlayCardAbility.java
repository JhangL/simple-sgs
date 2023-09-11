package com.jh.sgs.core.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ShowPlayCardAbility extends ID {

    private String name;

    public ShowPlayCardAbility(PlayCardAbility playCardAbility) {
        setId(playCardAbility.getId());
        this.name = playCardAbility.getName();
    }


}
