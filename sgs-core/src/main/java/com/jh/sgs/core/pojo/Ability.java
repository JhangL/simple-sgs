package com.jh.sgs.core.pojo;

import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pojo.ID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Consumer;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class Ability extends ID {
    public static final int PLAY_CARD=1;
    public static final int SINGLE=2;


    private String name;

    private Abilityable abilityable;
    private int type;



    public Ability(int id, String name, Abilityable abilityable,int type) {
        super(id);
        this.name = name;
        this.abilityable = abilityable;
        this.type = type;
    }

    public static interface Abilityable{

    }
    public static interface SingleAbilityable extends Abilityable{

        void singleAbility(Ability ability);
    }
    public static interface PlayCardAbilityable extends Abilityable{

        Card playCardAbility(Ability ability, Consumer<Card> action);
    }
}
