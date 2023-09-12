package com.jh.sgs.core.general;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.enums.InteractiveEnum;
import com.jh.sgs.core.enums.SuitEnum;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.interactive.impl.TOFImpl;
import com.jh.sgs.core.pojo.Ability;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.FalseCard;
import com.jh.sgs.core.pool.BooleanPool;
import com.jh.sgs.core.roundevent.AbilityEvent;

import java.util.ArrayList;
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
        final Card[] falseCard = new Card[1];
        ContextManage.interactiveMachine().addEvent(getPlayerIndex(), "(倾国)请出牌", new Interactiveable() {
            boolean a, b;

            @Override
            public void cancelPlayCard() {
                b = true;
            }

            @Override
            public List<Card> equipCard() {
                return new ArrayList<>();
            }

            @Override
            public List<Card> handCard() {
                return Util.collectionCloneToList(getCompletePlayer().getHandCard());
            }

            @Override
            public void setCard(int id) {
                Card card = Util.collectionCollectAndCheckId(getCompletePlayer().getHandCard(), id);
                Card falseCard1;
                switch (SuitEnum.getByIndex(card.getSuit())) {
                    case HEIT:
                    case MH:
                        falseCard1 = new FalseCard(card);
                        falseCard1.setNameId(CardEnum.SHAN.getId());
                        break;
                    default:
                        throw new SgsApiException("选择的牌花色不是黑色");
                }
                action.accept(falseCard1);
                getCompletePlayer().getHandCard().remove(card);
                falseCard[0] = falseCard1;
                a = true;
            }

            @Override
            public void cancel() {
                cancelPlayCard();
            }

            @Override
            public InteractiveEvent.CompleteEnum complete() {
                return a || b ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
            }

            @Override
            public InteractiveEnum type() {
                return InteractiveEnum.JNXZP;
            }
        }).lock();
        if (falseCard[0] != null) {
            Map<String, String> cardParameter = ContextManage.cardManage().getCardParameter(falseCard[0].getNameId());
            falseCard[0].setName(cardParameter.get("name"));
            falseCard[0].setName(cardParameter.get("remark"));
        }
        return falseCard[0];
    }

    @Override
    public void start() {
        super.start();

        BooleanPool a = new BooleanPool();
        do {
            ContextManage.interactiveMachine().addEvent(getPlayerIndex(), "是否使用洛神", new TOFImpl(a)).lock();
            if (a.isPool()) {
                //使用
                //todo 判定前
                Card card = ContextManage.cardManage().obtainCard(1).get(0);
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
