package com.jh.sgs.core.general;

import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.interactive.Interactiveable;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pool.BooleanPool;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveMachine;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.interactive.impl.TOFImpl;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.roundevent.CardTargetHideEvent;

import java.util.ArrayList;
import java.util.List;

public class ZhuGeLiang extends BaseGeneral implements CardTargetHideEvent {


    public ZhuGeLiang(CompletePlayer completePlayer) {
        super(completePlayer);
    }


    @Override
    public boolean hide(Card card) {
        if (kongCheng()) {
            return card.getNameId() == CardEnum.SHA.getId() || card.getNameId() == CardEnum.JUE_DOU.getId();
        }
        return false;
    }

    private boolean kongCheng() {
        return getCompletePlayer().getHandCard().isEmpty();
    }


    @Override
    public void start() {
        super.start();
        BooleanPool pool = new BooleanPool();
        InteractiveMachine.addEventInContext(getPlayerIndex(), "是否使用空城", new TOFImpl(pool)).lock();
        if (!pool.isPool()) return;
        //空城
        int i = ContextManage.desk().sizeOnDesk();
        i = Math.min(i, 5);
        List<Card> cards = ContextManage.cardManage().obtainCard(i);
        ArrayList<Card> top = new ArrayList<>();
        ArrayList<Card> bottom = new ArrayList<>();
        InteractiveMachine.addEventInContext(getPlayerIndex(), "请将牌置于牌堆顶或底", new Interactiveable() {

            int a = 0;
            final int b = cards.size();

            @Override
            public void putCardsTop(int[] ids) {
                ArrayList<Card> cards1 = Util.collectionCollectAndCheckIds(cards, ids);
                top.addAll(cards1);
                cards.removeAll(cards1);
                a += cards1.size();
            }

            @Override
            public void putCardsBottom(int[] ids) {
                ArrayList<Card> cards1 = Util.collectionCollectAndCheckIds(cards, ids);
                bottom.addAll(cards1);
                cards.removeAll(cards1);
                a += cards1.size();
            }

            @Override
            public List<Card> targetCard() {
                return Util.collectionCloneToList(cards);
            }

            @Override
            public void cancel() {
                int[] ints = new int[targetCard().size()];
                for (int i1 = 0; i1 < targetCard().size(); i1++) {
                    ints[i1] = targetCard().get(i1).getId();
                }
                putCardsTop(ints);
            }

            @Override
            public CompleteEnum complete() {
                if (a == b) return CompleteEnum.COMPLETE;
                else if (a == 0) return CompleteEnum.NOEXECUTE;
                else return CompleteEnum.PROGRESS;
            }

            @Override
            public InteractiveEnum type() {
                return InteractiveEnum.GX;
            }
        }).lock();
        ContextManage.cardManage().getUsingCards().addAll(0, top);
        ContextManage.cardManage().getUsingCards().addAll(bottom);
    }
}
