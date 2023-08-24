package com.jh.sgs.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CompletePlayer {

    private OriginalPlayer originalPlayer;

    private General general;
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
