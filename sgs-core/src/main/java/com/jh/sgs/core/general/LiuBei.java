package com.jh.sgs.core.general;

import com.jh.sgs.base.enums.IdentityEnum;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pool.TPool;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.interactive.impl.JNXZPImpl;
import com.jh.sgs.core.interactive.impl.XZMBImpl;
import com.jh.sgs.core.pojo.Ability;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.roundevent.AbilityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LiuBei extends BaseGeneral implements AbilityEvent, Ability.PlayCardAbilityable, Ability.SingleAbilityable {

    private Ability renDe = new Ability(1, "仁德", this, Ability.SINGLE);
    private Ability jiJiang = new Ability(2, "激将", this, Ability.PLAY_CARD);

    private int renDeNum;

    public LiuBei(CompletePlayer completePlayer) {
        super(completePlayer);
    }

    @Override
    public List<Ability> addAbilityOption() {
        ArrayList<Ability> abilities = new ArrayList<>();
        if (getCompletePlayer().getIdentity() == IdentityEnum.ZG) abilities.add(jiJiang);
        if (getProcess() == PLAY_CARD) abilities.add(renDe);
        return abilities;
    }

    @Override
    public Card playCardAbility(Ability ability, Consumer<Card> action) {
        if (ability == jiJiang) {
            return jiJiang(action);
        } else {
            throw new SgsRuntimeException("系统错误");
        }
    }

    @Override
    public void singleAbility(Ability ability) {
        if (ability == renDe) {
            renDe();
        } else {
            throw new SgsRuntimeException("系统错误");
        }
    }

    private void renDe() {
        List<CompletePlayer> targets = ContextManage.roundManage().findTarget(getPlayerIndex(), null);
        TPool<Integer> target = new TPool<>();
        InteractiveMachine.addEventInContext(getPlayerIndex(), "(仁德)请选择目标", new XZMBImpl(target, targets)).lock();
        if (target.getPool() == null) return;
        TPool<Card> cardPool = new TPool<>();
        InteractiveMachine.addEventInContext(getPlayerIndex(), "(仁德)请选择手牌", new JNXZPImpl(getCompletePlayer(), cardPool::setPool, false)).lock();
        if (cardPool.getPool() == null) return;
        Util.getPlayer(target.getPool()).getHandCard().add(cardPool.getPool());
        renDeNum++;
        if (renDeNum == 2) {
            if (getCompletePlayer().getBlood() != getCompletePlayer().getMaxBlood()) {
                getCompletePlayer().setBlood(getCompletePlayer().getBlood() + 1);
                ContextManage.roundManage().addBlood(getPlayerIndex(), getPlayerIndex(), null);
            }
        }
    }

    @Override
    public void start() {
        super.start();
        renDeNum = 0;
    }

    private Card jiJiang(Consumer<Card> action) {
        List<CompletePlayer> targets = ContextManage.roundManage().findTarget(getPlayerIndex(), null);
        targets = targets.stream().filter(completePlayer -> "蜀".equals(completePlayer.getCompleteGeneral().getGeneral().getCountry())).collect(Collectors.toList());
        TPool<Card> cardTPool = new TPool<>();
        for (CompletePlayer target : targets) {
            ContextManage.roundManage().playCard(target.getId(),"(刘备-激将)请出杀",cardTPool,action,false);
            if (cardTPool.getPool()!=null)break;
        }
        return cardTPool.getPool();
    }

}
