package com.jh.sgs.base.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShowPlayCardAbility extends ID implements Serializable {

    private String name;

    @Override
    public String toString() {
        return getId()+" "+getName();
    }
}
