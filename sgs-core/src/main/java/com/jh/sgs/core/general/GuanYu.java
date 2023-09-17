package com.jh.sgs.core.general;

import com.jh.sgs.base.enums.SuitEnum;
import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pool.TPool;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.interactive.impl.JNXZPImpl;
import com.jh.sgs.core.pojo.Ability;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.roundevent.AbilityEvent;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GuanYu extends BaseGeneral implements AbilityEvent, Ability.PlayCardAbilityable {

    private Ability wuSeng = new Ability(4, "武圣", this, Ability.PLAY_CARD);

    public GuanYu(CompletePlayer completePlayer) {
        super(completePlayer);
    }


    @Override
    public List<Ability> addAbilityOption() {
        return Collections.singletonList(wuSeng);
    }

    @Override
    public Card playCardAbility(Ability ability, Consumer<Card> action) {
        if (ability == wuSeng) {
            return wuSeng(action);
        } else {
            throw new SgsRuntimeException("系统错误");
        }
    }

    private Card wuSeng(Consumer<Card> action) {

        TPool<Card> falseCard = new TPool<>();
        InteractiveMachine.addEventInContext(getPlayerIndex(), "(武圣)请出牌", new JNXZPImpl(getCompletePlayer(), card -> {
            Card falseCard1;
            switch (SuitEnum.getByIndex(card.getSuit())) {
                case HONGT:
                case FP:
                    card.changeFalse();
                    falseCard1 = card;
                    falseCard1.setNameId(CardEnum.SHA.getId());
                    break;
                default:
                    throw new SgsApiException("选择的牌不是红色");
            }
             try {
                action.accept(falseCard1);
            }catch (SgsApiException e){
                card.backTrue();
                throw e;
            }
            falseCard.setPool(falseCard1);
        }, true)).lock();
        if (!falseCard.isEmpty()) {
            Map<String, String> cardParameter = ContextManage.cardManage().getCardParameter(falseCard.getPool().getNameId());
            falseCard.getPool().setName(cardParameter.get("name"));
            falseCard.getPool().setRemark(cardParameter.get("remark"));
        }
        return falseCard.getPool();
    }
}
