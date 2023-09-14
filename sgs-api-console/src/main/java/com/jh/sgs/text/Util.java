package com.jh.sgs.text;

import java.io.PrintStream;

public class Util {

    /**
     *
     * @param fColor 31红色 32绿色 33黄色 34蓝色 35紫色 36青色  37灰色 97白色
     * @param fontType 0重置 1加粗 2减弱 3斜体 4下划线 5慢速闪烁 6快速闪烁
     * @param s 文字
     */
    public static void printlnColor(int fColor, int fontType, String s){
        System.out.printf("\033[%d;%dm%s\033[0m\n",fontType,fColor,s);
    }
    public static void printColor(int fColor, int fontType, String s){
        System.out.printf("\033[%d;%dm%s\033[0m",fontType,fColor,s);
    }
    public static void printlnColor(PrintStream printStream, int fColor, int fontType, String s){
        printStream.printf("\033[%d;%dm%s\033[0m\n",fontType,fColor,s);
    }
    public static void printColor(PrintStream printStream,int fColor, int fontType, String s){
        printStream.printf("\033[%d;%dm%s\033[0m",fontType,fColor,s);
    }
}
