package com.jh.sgs.core.general;

import com.jh.sgs.core.ContextManage;
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

public class ZhaoYun extends BaseGeneral implements AbilityEvent, Ability.PlayCardAbilityable {


    Ability longDan = new Ability(7, "龙胆", this, Ability.PLAY_CARD);

    public ZhaoYun(CompletePlayer completePlayer) {
        super(completePlayer);
    }


    @Override
    public List<Ability> addAbilityOption() {
        return Collections.singletonList(longDan);
    }

    @Override
    public Card playCardAbility(Ability ability, Consumer<Card> action) {
        if (ability == longDan) {
            return longDan(action);
        } else {
            throw new SgsRuntimeException("系统错误");
        }
    }

    private Card longDan(Consumer<Card> action) {

        TPool<Card> falseCard = new TPool<>();
        ContextManage.interactiveMachine().addEvent(getPlayerIndex(), "(龙胆)请出牌", new JNXZPImpl(getCompletePlayer(), card -> {
            Card falseCard1;
            switch (card.getNameId()) {
                case 1:
                    card.changeFalse();
                    falseCard1 = card;
                    falseCard1.setNameId(2);
                    break;
                case 2:
                    card.changeFalse();
                    falseCard1 = card;
                    falseCard1.setNameId(1);
                    break;
                default:
                    throw new SgsApiException("选择的牌不是杀/闪");
            }
            action.accept(falseCard1);
            falseCard.setPool(falseCard1);
        }, false)).lock();
        if (!falseCard.isEmpty()) {
            Map<String, String> cardParameter = ContextManage.cardManage().getCardParameter(falseCard.getPool().getNameId());
            falseCard.getPool().setName(cardParameter.get("name"));
            falseCard.getPool().setName(cardParameter.get("remark"));
        }
        return falseCard.getPool();
    }
}
