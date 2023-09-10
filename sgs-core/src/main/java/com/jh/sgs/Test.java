package com.jh.sgs;

public class Test {
    public static void main(String[] args) {

        System.out.printf("\033[%d;%d;%dm%s\033[0m",3,31,41,"文 字 背 景 ");
    }
}
