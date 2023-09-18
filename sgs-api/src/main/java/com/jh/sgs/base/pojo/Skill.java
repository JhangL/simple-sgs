package com.jh.sgs.base.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class Skill extends ID implements Serializable {

    private String name;
    private String remake;

}
