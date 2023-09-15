package com.jh.sgs.text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {

    /**
     *
     * @param fColor 31红色 32绿色 33黄色 34蓝色 35紫色 36青色  37灰色 97白色
     * @param fontType 0重置 1加粗 2减弱 3斜体 4下划线 5慢速闪烁 6快速闪烁
     * @param s 文字
     */
    public static void printlnColor(int fColor, int fontType, String s) {
        System.out.printf("\033[%d;%dm%s\033[0m\n", fontType, fColor, s);
    }

    public static void printColor(int fColor, int fontType, String s) {
        System.out.printf("\033[%d;%dm%s\033[0m", fontType, fColor, s);
    }

    public static void printlnColor(PrintStream printStream, int fColor, int fontType, String s) {
        printStream.printf("\033[%d;%dm%s\033[0m\n", fontType, fColor, s);
    }

    public static void printColor(PrintStream printStream, int fColor, int fontType, String s) {
        printStream.printf("\033[%d;%dm%s\033[0m", fontType, fColor, s);
    }

    public static String read(String path) {
        byte[] bytes;
        try {
            if (path.contains("classpath:")) {
                InputStream resourceAsStream = Util.class.getClassLoader().getResourceAsStream(path.substring(10));
                byte[] buffer = new byte[1024];
                int len = 0;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((len = resourceAsStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.close();
                bytes = bos.toByteArray();
            } else {
                bytes = Files.readAllBytes(Paths.get(path));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String(bytes);
    }
}
