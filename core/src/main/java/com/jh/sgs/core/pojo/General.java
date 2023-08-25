package com.jh.sgs.core.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class General extends ID implements Cloneable{

    private String name;
    private String country;
    private int blood;
    private int[] skillIds;

    @Override
    public General clone() {
        try {
            return (General) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
