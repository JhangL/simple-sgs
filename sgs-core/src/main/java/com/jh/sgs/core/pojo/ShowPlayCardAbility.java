package com.jh.sgs.core.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShowPlayCardAbility extends ID {

    private String name;

    public ShowPlayCardAbility(Ability ability) {
        setId(ability.getId());
        this.name = ability.getName();
    }

    @Override
    public String toString() {
        return getId()+" "+getName();
    }
}
