package com.jh.sgs.core.general;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.enums.SuitEnum;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.interactive.impl.JNXZPImpl;
import com.jh.sgs.core.pojo.Ability;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pool.TPool;
import com.jh.sgs.core.roundevent.AbilityEvent;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GanNing extends BaseGeneral implements AbilityEvent, Ability.PlayCardAbilityable {

    private Ability qiXi = new Ability(14, "奇袭", this, Ability.PLAY_CARD);

    public GanNing(CompletePlayer completePlayer) {
        super(completePlayer);
    }


    @Override
    public List<Ability> addAbilityOption() {
        if (getProcess() == PLAY_CARD) return Collections.singletonList(qiXi);
        else return null;
    }

    @Override
    public Card playCardAbility(Ability ability, Consumer<Card> action) {
        if (ability == qiXi) {
            return qiXi(action);
        } else {
            throw new SgsRuntimeException("系统错误");
        }
    }

    private Card qiXi(Consumer<Card> action) {
        TPool<Card> falseCard = new TPool<>();
        InteractiveMachine.addEventInContext(getPlayerIndex(), "(奇袭)请出牌", new JNXZPImpl(getCompletePlayer(), card -> {
            Card falseCard1;
            switch (SuitEnum.getByIndex(card.getSuit())) {
                case HEIT:
                case MH:
                    card.changeFalse();
                    falseCard1 = card;
                    falseCard1.setNameId(CardEnum.GUO_HE_CHAI_QIAO.getId());
                    break;
                default:
                    throw new SgsApiException("选择的牌花色不是黑色");
            }
            action.accept(falseCard1);
            falseCard.setPool(falseCard1);
        }, true)).lock();
        if (!falseCard.isEmpty()) {
            Map<String, String> cardParameter = ContextManage.cardManage().getCardParameter(falseCard.getPool().getNameId());
            falseCard.getPool().setName(cardParameter.get("name"));
            falseCard.getPool().setName(cardParameter.get("remark"));
        }
        return falseCard.getPool();
    }
}
