package com.jh.sgs.core.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Consumer;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class PlayCardAbility extends ID {

    private String name;

    private PlayCardAbilityable abilityable;

    public PlayCardAbility(int id, String name, PlayCardAbilityable abilityable) {
        super(id);
        this.name = name;
        this.abilityable = abilityable;
    }

    public static interface PlayCardAbilityable {

        Card playCardAbility(PlayCardAbility playCardAbility, Consumer<Card> action);
    }
}
