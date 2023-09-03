package com.jh.sgs.core.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class Skill extends ID{

    private String name;

}
