package com.jh.sgs.core.pojo;

import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pojo.ID;
import com.jh.sgs.core.enums.IdentityEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CompletePlayer extends ID {

    private OriginalPlayer originalPlayer;

    private CompleteGeneral completeGeneral;
    private IdentityEnum identity;

    private int maxBlood;
    private int blood;

    private Card[] equipCard=new Card[4];

    private List<Card> decideCard=new ArrayList<>();

    private List<Card> handCard=new LinkedList<>();

    public CompletePlayer(OriginalPlayer originalPlayer) {
        this.originalPlayer = originalPlayer;
    }
}
