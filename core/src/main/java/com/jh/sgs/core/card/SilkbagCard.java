package com.jh.sgs.core.card;

public abstract class SilkbagCard extends BaseCard implements Executable{
    @Override
    public void execute() {
        begin();
        effect();
        end();
    }

    abstract public void begin();
    abstract public void effect();
    abstract public void end();
}
