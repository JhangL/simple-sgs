package com.jh.sgs.text;

import com.jh.sgs.StartGame;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Inputer {
    public final static int CANCAL = -100;
    public final static int SYSTEM = 8848;
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
        System.out.println("请输入(" + SYSTEM + "系统)：");
        while (true)
            try {
                a = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        System.out.println("输入：" + a);
        if (a == SYSTEM) {
            system();
            System.out.println("返回牌局");
            a = inputInt();
        }
        return a;
    }

    private int waitInputInt() {
        int a = CANCAL;
        int time = ticking;
        long l = System.currentTimeMillis();
        System.out.println("请输入(" + SYSTEM + "系统)：");
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
        if (a == SYSTEM) {
            system();
            System.out.println("返回牌局");
            a = inputInt();
        }
        return a;
    }

    private void system() {
        while (true) {
            System.out.println("1剩余牌数 2弃牌数 3玩家 886关闭系统 " + CANCAL + "返回");
            int i = Integer.parseInt(scanner.nextLine());
            switch (i) {
                case 1:
                    System.out.println("剩余牌数:" + StartGame.messageRequest.getUsingCardNum());
                    break;
                case 2:
                    System.out.println("牌数:" + StartGame.messageRequest.getUsedCardNum());
                    break;
                case 3:
                    System.out.println("选择玩家id(0<=id<总人数) " + CANCAL + "返回");
                    int i1 = Integer.parseInt(scanner.nextLine());
                    if (i1 == CANCAL) break;
                    if (i1 < 0 || i1 >= StartGame.playerNum) {
                        System.out.println("超出范围");
                        break;
                    }
                    System.out.println(StartGame.messageRequest.getPlayer(i1));
                    break;
                case 886:
                    System.exit(0);
                    break;
                case CANCAL:
                    return;
                default:
                    System.out.println("输入错误，返回");
            }
        }

    }

//    public int waitInts() {
//        int a = CANCAL;
//        int[] b;
//        int time = ticking;
//        long l = System.currentTimeMillis();
//        System.out.println("请输入：");
//        System.out.println("计时    输入");
//        while (time >= 0) {
//            try {
//                if (System.in.available() > 0) {
//                    try {
//                        String s = scanner.nextLine();
//                        String[] split = s.split(",");
//                        b = new int[split.length];
//                        for (int i = 0; i < split.length; i++) {
//                            String string = split[i];
//                            b[i] = Integer.parseInt(string);
//                        }
//                        break;
//                    } catch (Exception e) {
//                        System.out.println("输入错误");
//                        b = null;
//                        System.out.print("\b\b\b\b\b\b\b\b\b\b\b" + time + "     ");
//                        time -= 2;
//                    }
//                } else {
//                    System.out.print("\b\b\b\b\b\b\b\b\b\b\b" + time + "     ");
//                    time -= 2;
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        if (a == 1000) System.out.println(a);
//        System.out.println("输入：" + a);
//        return a;
//    }
}
