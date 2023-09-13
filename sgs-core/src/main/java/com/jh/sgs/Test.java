package com.jh.sgs;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) {

        HashSet<String> strings = new HashSet<>();
        HashSet<Integer> integers = new HashSet<>();
        for (HashSet hashSet : new HashSet[]{strings, integers}) {
            Type type = ((ParameterizedType)hashSet.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            System.out.println(type);
            hashSet.add(1);
        }

    }
}
