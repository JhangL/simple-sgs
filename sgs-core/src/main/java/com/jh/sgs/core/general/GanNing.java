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
import com.jh.sgs.core.pojo.Ability;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.FalseCard;
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
        if (getProcess()==PLAY_CARD) return Collections.singletonList(qiXi);
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
        final Card[] falseCard = new Card[1];
        ContextManage.interactiveMachine().addEvent(getPlayerIndex(), "(奇袭)请出牌", new Interactiveable() {
            boolean a, b;

            @Override
            public void cancelPlayCard() {
                b = true;
            }

            @Override
            public List<Card> equipCard() {
                return Util.arrayCloneToList(getCompletePlayer().getEquipCard());
            }

            @Override
            public List<Card> handCard() {
                return Util.collectionCloneToList(getCompletePlayer().getHandCard());
            }

            @Override
            public void setCard(int id) {
                Card card = null;
                int v=0;
                try {
                    card = Util.collectionCollectAndCheckId(getCompletePlayer().getHandCard(), id);
                    v=10;
                }catch (Exception e){
                    card = Util.ArrayCollectAndCheckId(getCompletePlayer().getEquipCard(), id);
                }
                Card falseCard1;
                switch (SuitEnum.getByIndex(card.getSuit())) {
                    case HEIT:
                    case MH:
                        falseCard1 = new FalseCard(card);
                        falseCard1.setNameId(CardEnum.GUO_HE_CHAI_QIAO.getId());
                        break;
                    default:
                        throw new SgsApiException("选择的牌花色不是黑色");
                }
                action.accept(falseCard1);
                if (v==10){
                    getCompletePlayer().getHandCard().remove(card);
                }else {
                    Util.ArrayRemove(getCompletePlayer().getEquipCard(),card);
                }
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
}
