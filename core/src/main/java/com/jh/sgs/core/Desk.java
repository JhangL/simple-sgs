package com.jh.sgs.core;

import com.alibaba.fastjson2.JSON;
import com.jh.sgs.core.exception.SgsException;
import com.jh.sgs.core.interfaces.ShowStatus;
import com.jh.sgs.core.pojo.CompletePlayer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Desk implements ShowStatus {

    private CompletePlayer[] chair;

    private boolean[] onDesk;

    private int index;

    Desk(CompletePlayer[] chair) {
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

    public void foreach(BiConsumer<Integer, ? super CompletePlayer> action) {
        for (int i = 0, chairLength = chair.length; i < chairLength; i++) {
            CompletePlayer t = chair[i];
            action.accept(i, t);
        }
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

    public int index() {
        return index % size();
    }

    public int index(CompletePlayer completePlayer) {
        if (get() == completePlayer) return index % size();
        for (int i = 0, chairLength = chair.length; i < chairLength; i++) {
            CompletePlayer player = chair[i];
            if (player == completePlayer) return i;
        }
        throw new SgsException("玩家并非处于此桌");
    }

    public CompletePlayer get() {
        return chair[index % size()];
    }

    public CompletePlayer get(int index) {
        return chair[index];
    }

    public int next() {
        ++index;
        return index();
    }

    public CompletePlayer nextPlayer() {
        next();
        return get();
    }

    public int nextOnDesk() {
        do {
            ++index;
        } while (!onDesk[index % size()]);
        return index();
    }

    public CompletePlayer nextOnDeskPlayer() {
        nextOnDesk();
        return get();
    }

    public int previous() {
        if (index == 0) index = size();
        --index;
        return index();
    }

    public CompletePlayer previousPlayer() {
        previous();
        return get();
    }

    public int previousOnDesk() {
        do {
            if (index == 0) index = size();
            --index;
        } while (!onDesk[index % size()]);
        return index();
    }

    public CompletePlayer previousOnDeskPlayer() {
        previousOnDesk();
        return get();
    }


    @Override
    public String getStatus() {
        return "{" +
                "\"chair\":" + JSON.toJSONString(chair) +
                ", \"onDesk\":" + JSON.toJSONString(onDesk) +
                ", \"index\":" + index +
                '}';
    }

}
