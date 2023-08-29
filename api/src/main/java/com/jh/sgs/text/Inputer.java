package com.jh.sgs.text;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Inputer {
    public final static int CANCAL = -100;
    private Scanner scanner = new Scanner(System.in);
    private int ticking;

    public Inputer() {
        this(System.in, 10);
    }

    public Inputer(int ticking) {
        this(System.in, ticking);
    }

    public Inputer(InputStream inputStream, int ticking) {
        this.scanner = new Scanner(inputStream);
        this.ticking = ticking;
    }

    public int inputInt() {
        if (ticking < 0) return nowaitInputInt();
        else return waitInputInt();
    }

    private int nowaitInputInt() {
        int a;
        System.out.println("请输入：");
        while (true)
            try {
                a = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        System.out.println("输入：" + a);
        return a;
    }

    private int waitInputInt() {
        int a = CANCAL;
        int time = ticking;
        long l = System.currentTimeMillis();
        System.out.println("请输入：");
        System.out.println("计时    输入");
        while (time >= 0) {
            try {
                if (System.in.available() > 0) {
                    try {
                        a = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("输入错误");
                        System.out.print("\b\b\b\b\b\b\b\b\b\b\b" + time + "     ");
                        time -= 2;
                    }
                } else {
                    System.out.print("\b\b\b\b\b\b\b\b\b\b\b" + time + "     ");
                    time -= 2;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (a == 1000) System.out.println(a);
        System.out.println("输入：" + a);
        return a;
    }

    public int waitInts() {
        int a = CANCAL;
        int[] b;
        int time = ticking;
        long l = System.currentTimeMillis();
        System.out.println("请输入：");
        System.out.println("计时    输入");
        while (time >= 0) {
            try {
                if (System.in.available() > 0) {
                    try {
                        String s = scanner.nextLine();
                        String[] split = s.split(",");
                        b = new int[split.length];
                        for (int i = 0; i < split.length; i++) {
                            String string = split[i];
                            b[i] = Integer.parseInt(string);
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("输入错误");
                        b = null;
                        System.out.print("\b\b\b\b\b\b\b\b\b\b\b" + time + "     ");
                        time -= 2;
                    }
                } else {
                    System.out.print("\b\b\b\b\b\b\b\b\b\b\b" + time + "     ");
                    time -= 2;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (a == 1000) System.out.println(a);
        System.out.println("输入：" + a);
        return a;
    }
}
