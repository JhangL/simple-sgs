package com.jh.sgs.core.pojo;


import com.jh.sgs.core.Util;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ShowPlayer extends ID {

    private String name;
    private String country;
    private int blood;
    private int maxBlood;
    private List<Card> equipCard;
    private List<Card> decideCard;
    private int handCardNum;

    public ShowPlayer(CompletePlayer completePlayer) {
        this.setId(completePlayer.getId());
        if (completePlayer.getCompleteGeneral() != null && completePlayer.getCompleteGeneral().getGeneral() != null) {
            this.name = completePlayer.getCompleteGeneral().getGeneral().getName();
            this.country = completePlayer.getCompleteGeneral().getGeneral().getCountry();
        }
        this.blood = completePlayer.getBlood();
        this.maxBlood = completePlayer.getMaxBlood();
        this.equipCard = Util.arrayCloneToList(completePlayer.getEquipCard());
        this.decideCard = Util.collectionCloneToList(completePlayer.getDecideCard());
        this.handCardNum = completePlayer.getHandCard().size();
    }
}
