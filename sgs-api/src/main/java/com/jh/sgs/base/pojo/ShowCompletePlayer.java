package com.jh.sgs.base.pojo;

import com.jh.sgs.base.enums.IdentityEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShowCompletePlayer extends ID implements Serializable {
    private String name;
    private String country;
    private IdentityEnum identity;
    private Skill[] skills;

    private int maxBlood;
    private int blood;

    private Card[] equipCard=new Card[4];

    private List<Card> decideCard=new ArrayList<>();

    private List<Card> handCard=new LinkedList<>();
}
