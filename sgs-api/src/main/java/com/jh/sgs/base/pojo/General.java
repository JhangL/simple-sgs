package com.jh.sgs.base.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class General extends ID implements Cloneable, Serializable {

    private String name;
    private String country;
    private int blood;
    private int[] skillIds;
    private Skill[] skills;

    @Override
    public General clone() {
        try {
            return (General) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return getId()+" ["+getCountry()+"]"+getName()+"("+getBlood()+")";
    }
}
