package com.jh.sgs.base.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShowPlayCardAbility extends ID {

    private String name;

    @Override
    public String toString() {
        return getId()+" "+getName();
    }
}
