package com.jh.sgs.pojo;

import lombok.Data;

@Data
public class General {
    private int id;
    private String name;
    private String country;
    private int blood;
    private int[] skillIds;
}
