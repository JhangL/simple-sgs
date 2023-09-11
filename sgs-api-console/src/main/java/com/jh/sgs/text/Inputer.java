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
        Util.printlnColor(32,3,"请输入(" + SYSTEM + "系统)：");
        while (true)
            try {
                a = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                Util.printlnColor(35,3,"请输入数字");
            }
        Util.printlnColor(34,3,"输入：" + a);
        if (a == SYSTEM) {
            system();
            Util.printlnColor(32,3,"返回牌局");
            a = inputInt();
        }
        return a;
    }

    private int waitInputInt() {
        int a = CANCAL;
        int time = ticking;
        long l = System.currentTimeMillis();
        Util.printlnColor(32,3,"请输入(" + SYSTEM + "系统)：");
        Util.printlnColor(32,3,"计时    输入");
        while (time >= 0) {
            try {
                if (System.in.available() > 0) {
                    try {
                        a = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (Exception e) {
                        Util.printlnColor(32,3,"输入错误");
                        Util.printColor(32,3,"\b\b\b\b\b\b\b\b\b\b\b" + time + "     ");
                        time -= 2;
                    }
                } else {
                    Util.printColor(32,3,"\b\b\b\b\b\b\b\b\b\b\b" + time + "     ");
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
        if (a == CANCAL) Util.printlnColor(32,3, String.valueOf(a));
        Util.printlnColor(32,3,"输入：" + a);
        if (a == SYSTEM) {
            system();
            Util.printlnColor(32,3,"返回牌局");
            a = inputInt();
        }
        return a;
    }

    private void system() {
        while (true) {
            Util.printlnColor(32,4,"1剩余牌数 2弃牌数 3玩家 886关闭系统 " + CANCAL + "返回");
            int i = Integer.parseInt(scanner.nextLine());
            switch (i) {
                case 1:
                    Util.printlnColor(32,4,"剩余牌数:" + StartGame.messageRequest.getUsingCardNum());
                    break;
                case 2:
                    Util.printlnColor(32,4,"牌数:" + StartGame.messageRequest.getUsedCardNum());
                    break;
                case 3:
                    Util.printlnColor(32,4,"选择玩家id(0<=id<"+StartGame.playerNum+") " + CANCAL + "返回");
                    int i1 = Integer.parseInt(scanner.nextLine());
                    if (i1 == CANCAL) break;
                    if (i1 < 0 || i1 >= StartGame.playerNum) {
                        Util.printlnColor(32,4,"超出范围");
                        break;
                    }
                    System.out.println(StartGame.messageRequest.getShowPlayer(i1));
                    break;
                case 886:
                    System.exit(0);
                    break;
                case CANCAL:
                    return;
                default:
                    Util.printlnColor(32,4,"输入错误，返回");
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
