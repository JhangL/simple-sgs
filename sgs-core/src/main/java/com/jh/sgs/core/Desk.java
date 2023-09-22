package com.jh.sgs.core;

import com.alibaba.fastjson2.JSON;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.interfaces.ShowStatus;
import com.jh.sgs.core.pojo.CompletePlayer;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Desk implements ShowStatus {

    private CompletePlayer[] chair;

    private boolean[] onDesk;

    private int index;

    Desk(CompletePlayer[] chair) {
        this.chair = chair;
        for (int i = 0; i < chair.length; i++) chair[i].setId(i);
        onDesk = new boolean[chair.length];
        Arrays.fill(onDesk, true);
    }

    public void getoffDesk(int player){
        onDesk[player]=false;
    }

    public int size() {
        return chair.length;
    }

    public int sizeOnDesk() {
        int a = 0;
        for (boolean b : onDesk) {
            if (b) a++;
        }
        return a;
    }

    public void foreach(BiConsumer<Integer, ? super CompletePlayer> action) {
        for (int i = 0, chairLength = chair.length; i < chairLength; i++) {
            CompletePlayer t = chair[i];
            action.accept(i, t);
        }
    }

    public void foreach(Consumer<? super CompletePlayer> action) {
        Arrays.stream(chair).forEach(action);
    }

    /**
     * 遍历在场玩家
     * @param action 遍历方法器
     */
    public void foreachOnDesk(Consumer<? super CompletePlayer> action) {
        for (int i = 0; i < chair.length; i++) {
            if (!onDesk[i]) continue;
            CompletePlayer t = chair[i];
            action.accept(t);
        }
    }

    /**
     * 按流程顺序（递增）遍历在场玩家（不包括查询玩家）
     * @param player 查询玩家位置
     * @param action 遍历方法器
     */
    public void foreachOnDeskNoPlayer(int player, Consumer<? super CompletePlayer> action) {
        for (int i = player + 1; i < chair.length + player; i++) {
            if (!onDesk[i % size()]) continue;
            CompletePlayer t = chair[i % size()];
            action.accept(t);
        }
    }

    /**
     * 遍历在场玩家并附加与查询玩家的相对距离（不包括查询玩家）
     * @param player 查询玩家位置
     * @param action 遍历方法器【距离，玩家类】
     */
    public void foreachHaveDistanceOnDeskNoPlayer(int player, BiConsumer<Integer, ? super CompletePlayer> action) {
        int[] ints = new int[sizeOnDesk()];
        int playerIndex = -1;
        //将在场的玩家位置放到临时数组里去计算距离，并保存查询玩家在临时数组的位置;
        for (int i = 0, j = 0; i < chair.length; i++) {
            if (onDesk[i]) {
                ints[j] = i;
                if (i == player) playerIndex = j;
                j++;
            }
        }
        //将整理完的数组遍历，根据查询玩家的临时数组位置，计算相对距离
        for (int i = 0, intsLength = ints.length; i < intsLength; i++) {
            if (i == playerIndex) continue;
            //公式（a查询玩家，b被查询玩家，s人数）：b>a: b-a,a-(b-c)找最小，b<a: a-b,b+c-a 找最小
            action.accept(i > playerIndex ? Math.min(i - playerIndex, playerIndex - (i - ints.length)) : Math.min(playerIndex - i, i +  ints.length - playerIndex), chair[ints[i]]);
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
        throw new SgsRuntimeException("玩家并非处于此桌");
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

    public int nextOnDesk(int index) {
        do {
            ++index;
        } while (!onDesk[index % size()]);
        return index % size();
    }

    @Override
    public String getStatus() {
        return "{" +
                "\"chair\":" + "\"JSON.toJSONString(chair)\"" +
                ", \"onDesk\":" + JSON.toJSONString(onDesk) +
                ", \"index\":" + index +
                '}';
    }

}
