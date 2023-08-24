package com.jh.sgs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {


        Set<String> strings = new HashSet<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("3");
        objects.add("4");
        System.out.println(strings.containsAll(objects));
        System.out.println(strings.removeAll(objects));
        System.out.println(strings);
    }
}
