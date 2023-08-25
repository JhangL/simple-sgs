package com.jh.sgs.core;

import com.jh.sgs.core.pojo.ID;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Util {


    public static <A extends Cloneable > List<A> collectionCloneToList(Collection<A> t){
        return t.stream().map(a -> {
            try {
                return (A) a.getClass().getDeclaredMethod("clone").invoke(a);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
    public static <A extends ID> boolean collectionCollectAndCheckIds(Collection<A> beCheck, int[] ids , Collection<A> collect){
        ArrayList<A> as = new ArrayList<>();
        for (A a : beCheck) {
            if (Arrays.stream(ids).anyMatch(value -> value==a.getId()))as.add(a);
        }
        if (ids.length==as.size()){
            collect.addAll(as);
            return true;
        }else {
            return false;
        }
    }
    public static <A extends ID> A collectionCollectAndCheckId(Collection<A> beCheck, int id ){
        A as = null;
        for (A a : beCheck) {
            if (id==a.getId())as=a;
        }
        return as;
    }
}
