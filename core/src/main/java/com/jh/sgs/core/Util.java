package com.jh.sgs.core;

import com.jh.sgs.core.exception.SgsException;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.ID;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class Util {


    public static <A extends Cloneable> List<A> collectionCloneToList(Collection<A> t) {
        return t.stream().map(a -> {
            try {
                return (A) a.getClass().getDeclaredMethod("clone").invoke(a);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public static <A extends Cloneable> List<A> arrayCloneToList(A[] t) {
        return Arrays.stream(t).filter(Objects::nonNull).map(a -> {
            try {
                return (A) a.getClass().getDeclaredMethod("clone").invoke(a);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public static <A extends ID> ArrayList<A> collectionCollectAndCheckIds(Collection<A> beCheck, int[] ids) {
        ArrayList<A> as = new ArrayList<>();
        for (A a : beCheck) {
            if (Arrays.stream(ids).anyMatch(value -> value == a.getId())) as.add(a);
        }
        if (ids.length == as.size()) {
            return as;
        } else {
            throw new SgsException("存在给定id并非原数据id");
        }
    }

    public static <A extends ID> A collectionCollectAndCheckId(Collection<A> beCheck, int id) {
        A as = null;
        for (A a : beCheck) {
            if (id == a.getId()) as = a;
        }
        if (as == null) throw new SgsException("给定id并非原数据id");
        return as;
    }

    public static <A extends ID> A ArrayCollectAndCheckId(A[] beCheck, int id) {
        A as = null;
        for (A a : beCheck) {
            if (a == null) continue;
            if (id == a.getId()) as = a;
        }
        if (as == null) throw new SgsException("给定id并非原数据id");
        return as;
    }

    public static <A> void ArrayRemove(A[] array, A removeData) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == removeData) array[i] = null;
        }
    }

    public static CompletePlayer getDesktopMainPlayer() {
        return getPlayer(ContextManage.desktop().getPlayer());
    }

    public static CompletePlayer getPlayer(int index) {
        return ContextManage.desk().get(index);
    }
}
