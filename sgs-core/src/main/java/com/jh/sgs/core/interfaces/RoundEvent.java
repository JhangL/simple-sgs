package com.jh.sgs.core.interfaces;

public interface RoundEvent {
    public static final int PLAYER=1;
    public static final int GLOBAL=2;

    default int eventLocation(){
        return PLAYER;
    };
}
