package com.jh.sgs.core.pojo;

import com.jh.sgs.core.enums.IdentityEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class CompletePlayer extends ID{

    private OriginalPlayer originalPlayer;

    private CompleteGeneral completeGeneral;
    private IdentityEnum identity;

    private int maxBlood;
    private int blood;

    private Card[] equipCard=new Card[4];

    private List<Card> decideCard=new ArrayList<>();

    private Set<Card> handCard=new HashSet<>();

    public CompletePlayer(OriginalPlayer originalPlayer) {
        this.originalPlayer = originalPlayer;
    }
}
