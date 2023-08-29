package com.jh.sgs.core.card;

import com.jh.sgs.core.pojo.CompletePlayer;

import java.util.List;

public abstract class MoreSilkbagCard extends SilkbagCard {
    @Override
    public void begin() {

    }

    @Override
    public void effect() {
        for (CompletePlayer completePlayer : getPlayer()) {
            //todo 无懈可击检查
            effect(completePlayer);
        }
    }

    @Override
    public void end() {

    }

    abstract List<CompletePlayer> getPlayer();

    abstract void effect(CompletePlayer completePlayer);
}
