package com.jh.sgs.base.pojo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ShowPlayer extends ID implements Serializable {

    private String name;
    private String country;
    private int blood;
    private int maxBlood;
    private List<Card> equipCard;
    private List<Card> decideCard;
    private int handCardNum;

}
