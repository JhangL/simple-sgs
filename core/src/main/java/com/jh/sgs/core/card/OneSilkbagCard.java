package com.jh.sgs.core.card;

public abstract class OneSilkbagCard extends SilkbagCard{
    @Override
    public void begin() {

    }

    @Override
    public void effect() {
        int player = getPlayer();
        //todo 无懈可击检查
        effect(player);
    }

    @Override
    public void end() {

    }
    abstract int getPlayer();
    abstract void effect(int player);
}
