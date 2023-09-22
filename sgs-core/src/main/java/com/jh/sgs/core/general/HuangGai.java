package com.jh.sgs.core.general;

import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.pojo.Ability;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.roundevent.AbilityEvent;

import java.util.Collections;
import java.util.List;

public class HuangGai extends BaseGeneral implements AbilityEvent, Ability.SingleAbilityable {

    private Ability kuRou = new Ability(16, "苦肉", this, Ability.SINGLE);

    public HuangGai(CompletePlayer completePlayer) {
        super(completePlayer);
    }

    @Override
    public List<Ability> addAbilityOption() {
        if (getProcess() == PLAY_CARD) return Collections.singletonList(kuRou);
        else return null;
    }

    @Override
    public void singleAbility(Ability ability){
        if (ability == kuRou) {
            kuRou();
        } else {
            throw new SgsRuntimeException("系统错误");
        }
    }

    private void kuRou(){
        getCompletePlayer().setBlood(getCompletePlayer().getBlood() - 1);
        ContextManage.roundManage().subBlood(getPlayerIndex(), getPlayerIndex(), null, 1);
        List<Card> cards = ContextManage.cardManage().obtainCard(2);
        getCompletePlayer().getHandCard().addAll(cards);

    }
}
