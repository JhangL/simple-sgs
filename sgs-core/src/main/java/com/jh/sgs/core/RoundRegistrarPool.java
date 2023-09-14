package com.jh.sgs.core;

import com.jh.sgs.core.interfaces.RoundEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoundRegistrarPool extends HashMap<Class<? extends RoundEvent>, RoundRegistrar<? extends RoundEvent>> {


    public <T extends RoundEvent> void addRegistrar(Class<T> tClass) {
        put(tClass, new RoundRegistrar<T>());
    }


    public <T extends RoundEvent> RoundRegistrar<T> getRegistrar(Class<T> tClass) {
        return (RoundRegistrar<T>) get(tClass);
    }


    public List<RoundRegistrar<RoundEvent>> getRegistrar(RoundEvent roundEvent) {
        ArrayList<RoundRegistrar<RoundEvent>> registrars = new ArrayList<>();
        for (Class<? extends RoundEvent> aClass : keySet()) {
//            if (roundEvent.getClass().isAssignableFrom(aClass))
            if (aClass.isAssignableFrom(roundEvent.getClass()))
                registrars.add((RoundRegistrar<RoundEvent>) get(aClass));
        }
        return registrars;
    }
}
