package com.jh.sgs.core;

import com.jh.sgs.core.interfaces.RoundEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class RoundRegistrar<T extends RoundEvent> extends HashMap<Integer, List<T>> {


    public RoundRegistrar() {
        super();
        for (int i = 0; i < ContextManage.gameEngine().playerNum; i++) {
            put(i, new ArrayList<>());
        }
        put(-1, new ArrayList<>());
    }

    public void addPlayerEvent(int player, T roundEvent) {
        get(player).add(roundEvent);
    }

    public void subPlayerEvent(int player, T roundEvent) {
        get(player).remove(roundEvent);
    }

    public void addGlobalEvent(T roundEvent) {
        get(-1).add(roundEvent);
    }

    public void subGlobalEvent(T roundEvent) {
        get(-1).remove(roundEvent);
    }

    public void handlePlayer(int player, Consumer<T> action) {
        handlePlayer(player).forEach(action);
    }

    public Stream<T> handlePlayer(int player) {
        List<T> ts = get(player);
        ts.addAll(get(-1));
        return ts.stream();
    }


}
