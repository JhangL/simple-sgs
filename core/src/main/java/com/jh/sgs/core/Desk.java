package com.jh.sgs.core;

import com.jh.sgs.exception.SgsException;
import com.jh.sgs.pojo.CompletePlayer;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Consumer;

public class Desk {

    @Getter
    private CompletePlayer[] chair;

    private boolean[] onDesk;

    private int index;

    public Desk(CompletePlayer[] chair) {
        this.chair = chair;
        onDesk = new boolean[chair.length];
        Arrays.fill(onDesk, true);
    }

    private void check() {
        HashSet<Object> objects = new HashSet<>(Arrays.asList(chair));
        if (objects.size() != chair.length) throw new SgsException("存在重复数据");
    }

    public int size() {
        return chair.length;
    }

    public void foreach(Consumer<? super CompletePlayer> action) {
        for (CompletePlayer t : chair) {
            action.accept(t);
        }
    }

    public void setIndex(CompletePlayer t) {
        for (int i = 0, chairLength = chair.length; i < chairLength; i++) {
            CompletePlayer t1 = chair[i];
            if (t == t1) {
                index = i;
                break;
            }
        }
    }

    public CompletePlayer get() {
        return chair[index % size()];
    }
    public CompletePlayer get(int index) {
        return chair[index];
    }

    public CompletePlayer next() {
        ++index;
        return get();
    }

    public CompletePlayer nextOnDesk() {
        do {
            ++index;
        } while (!onDesk[index % size()]);
        return get();
    }

    public CompletePlayer previous() {
        if (index == 0) index = size();
        --index;
        return get();
    }

    public CompletePlayer previousOnDesk() {
        do {
            if (index == 0) index = size();
            --index;
        } while (!onDesk[index % size()]);
        return get();
    }
}
