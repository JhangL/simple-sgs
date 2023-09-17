package com.jh.sgs.core.general;

import com.jh.sgs.base.enums.IdentityEnum;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pool.TPool;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.pojo.Ability;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.roundevent.AbilityEvent;
import com.jh.sgs.core.roundevent.BeSubBloodEvent;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CaoCao extends BaseGeneral implements AbilityEvent, Ability.PlayCardAbilityable {

    private Ability huJia = new Ability(2, "护驾", this, Ability.PLAY_CARD);

    public CaoCao(CompletePlayer completePlayer) {
        super(completePlayer);
        setSkill(new Object[]{new JianXiong(),});
    }


    @Override
    public List<Ability> addAbilityOption() {
        if (getCompletePlayer().getIdentity() == IdentityEnum.ZG) return Collections.singletonList(huJia);
        return null;
    }

    @Override
    public Card playCardAbility(Ability ability, Consumer<Card> action) {
        //护驾
        if (ability==huJia){
            List<CompletePlayer> targets = ContextManage.roundManage().findTarget(getPlayerIndex(), null);
            targets = targets.stream().filter(completePlayer -> "魏".equals(completePlayer.getCompleteGeneral().getGeneral().getCountry())).collect(Collectors.toList());
            TPool<Card> cardTPool = new TPool<>();
            for (CompletePlayer target : targets) {
                ContextManage.roundManage().playCard(target.getId(),"(曹操-护驾)请出闪",cardTPool,action,false);
                if (!cardTPool.isEmpty())break;
            }
            return cardTPool.getPool();
        }
        return null;
    }

    class JianXiong implements BeSubBloodEvent {

        @Override
        public void beSubBlood(int operatePlayer, TPool<Card> operateCard) {
            getCompletePlayer().getHandCard().add(operateCard.getPool());
            operateCard.setPool(null);
        }
    }
}
