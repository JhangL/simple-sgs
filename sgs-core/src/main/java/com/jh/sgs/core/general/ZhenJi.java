package com.jh.sgs.core.general;

import com.jh.sgs.base.enums.SuitEnum;
import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.interactive.impl.JNXZPImpl;
import com.jh.sgs.core.interactive.impl.TOFImpl;
import com.jh.sgs.core.pojo.Ability;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pool.BooleanPool;
import com.jh.sgs.core.pool.TPool;
import com.jh.sgs.core.roundevent.AbilityEvent;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ZhenJi extends BaseGeneral implements AbilityEvent, Ability.PlayCardAbilityable {
    private Ability qingGuo = new Ability(33, "倾国", this, Ability.PLAY_CARD);

    public ZhenJi(CompletePlayer completePlayer) {
        super(completePlayer);
    }


    @Override
    public List<Ability> addAbilityOption() {
        return Collections.singletonList(qingGuo);
    }

    @Override
    public Card playCardAbility(Ability ability, Consumer<Card> action) {
        if (ability == qingGuo) {
            return qingGuo(action);
        } else {
            throw new SgsRuntimeException("系统错误");
        }
    }

    private Card qingGuo(Consumer<Card> action) {
        TPool<Card> falseCard = new TPool<>();
        InteractiveMachine.addEventInContext(getPlayerIndex(), "(倾国)请出牌", new JNXZPImpl(getCompletePlayer(), card -> {
            Card falseCard1;
            switch (SuitEnum.getByIndex(card.getSuit())) {
                case HEIT:
                case MH:
                    card.changeFalse();
                    falseCard1 = card;
                    falseCard1.setNameId(CardEnum.SHAN.getId());
                    break;
                default:
                    throw new SgsApiException("选择的牌花色不是黑色");
            }
             try {
                action.accept(falseCard1);
            }catch (SgsApiException e){
                card.backTrue();
                throw e;
            }
            falseCard.setPool(falseCard1);
        }, false)).lock();
        if (!falseCard.isEmpty()) {
            Map<String, String> cardParameter = ContextManage.cardManage().getCardParameter(falseCard.getPool().getNameId());
            falseCard.getPool().setName(cardParameter.get("name"));
            falseCard.getPool().setRemark(cardParameter.get("remark"));
        }
        return falseCard.getPool();
    }

    @Override
    public void start() {
        super.start();

        BooleanPool a = new BooleanPool();
        do {
            InteractiveMachine.addEventInContext(getPlayerIndex(), "是否使用洛神", new TOFImpl(a)).lock();
            if (a.isPool()) {
                //使用
                Card card = ContextManage.roundManage().decide(getPlayerIndex());
                switch (SuitEnum.getByIndex(card.getSuit())) {
                    case HEIT:
                    case MH:
                        getCompletePlayer().getHandCard().add(card);
                        a.setPool(true);
                        break;
                    default:
                        ContextManage.cardManage().recoveryCard(card);
                        a.setPool(false);
                }
            }
        } while (a.isPool());

    }


}
