package com.jh.sgs.core.interactive.impl;

import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.interactive.Interactiveable;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.pojo.CompletePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JNXZPImpl implements Interactiveable {

    private CompletePlayer completePlayer;

    private Consumer<Card> action;
    private boolean haveEquip;

    public JNXZPImpl(CompletePlayer completePlayer, Consumer<Card> action, boolean haveEquip) {
        this.completePlayer = completePlayer;
        this.action = action;
        this.haveEquip = haveEquip;
    }

    private boolean a, b;

    @Override
    public void cancelPlayCard() {
        b = true;
    }

    @Override
    public List<Card> equipCard() {
        if (haveEquip) return Util.arrayCloneToList(completePlayer.getEquipCard());
        return new ArrayList<>();
    }

    @Override
    public List<Card> handCard() {
        return Util.collectionCloneToList(completePlayer.getHandCard());
    }

    @Override
    public void setCard(int id) {
        if (haveEquip) {
            Card card;
            int v = 0;
            try {
                card = Util.collectionCollectAndCheckId(completePlayer.getHandCard(), id);
                v = 10;
            } catch (Exception e) {
                card = Util.ArrayCollectAndCheckId(completePlayer.getEquipCard(), id);
            }
            action.accept(card);
            if (v == 10) {
                completePlayer.getHandCard().remove(card);
            } else {
                Util.ArrayRemove(completePlayer.getEquipCard(), card);
            }
        } else {
            Card card = Util.collectionCollectAndCheckId(completePlayer.getHandCard(), id);
            action.accept(card);
            completePlayer.getHandCard().remove(card);
        }
        a = true;
    }

    @Override
    public void cancel() {
        cancelPlayCard();
    }

    @Override
    public CompleteEnum complete() {
        return a || b ? CompleteEnum.COMPLETE : CompleteEnum.NOEXECUTE;
    }

    @Override
    public InteractiveEnum type() {
        return InteractiveEnum.JNXZP;
    }
}
