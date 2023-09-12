package com.jh.sgs.text;

import com.jh.sgs.StartGame;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.ShowPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
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

    public int[] inputInts() {
        int[] a;
        Util.printlnColor(32, 3, "请输入[逗号分割](" + SYSTEM + "系统)：");
        while (true)
            try {
                String s = scanner.nextLine();
                String[] split = s.split(",");
                a = new int[split.length];
                for (int i = 0; i < split.length; i++) {
                    a[i] = Integer.parseInt(split[i]);
                }
                break;
            } catch (Exception e) {
                Util.printlnColor(35, 3, "请输入数字组");
            }
        Util.printlnColor(34, 3, "输入：" + Arrays.toString(a));
        if (a[0] == SYSTEM) {
            system();
            Util.printlnColor(32, 3, "返回牌局");
            a = inputInts();
        }
        return a;
    }

    private int nowaitInputInt() {
        int a;
        Util.printlnColor(32, 3, "请输入(" + CANCAL + "取消 " + SYSTEM + "系统)：");
        while (true)
            try {
                a = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                Util.printlnColor(35, 3, "请输入数字");
            }
        Util.printlnColor(34, 3, "输入：" + a);
        if (a == SYSTEM) {
            system();
            Util.printlnColor(32, 3, "返回牌局");
            a = inputInt();
        }
        return a;
    }

    private int waitInputInt() {
        int a = CANCAL;
        int time = ticking;
        long l = System.currentTimeMillis();
        Util.printlnColor(32, 3, "请输入(" + SYSTEM + "系统)：");
        Util.printlnColor(32, 3, "计时    输入");
        while (time >= 0) {
            try {
                if (System.in.available() > 0) {
                    try {
                        a = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (Exception e) {
                        Util.printlnColor(32, 3, "输入错误");
                        Util.printColor(32, 3, "\b\b\b\b\b\b\b\b\b\b\b" + time + "     ");
                        time -= 2;
                    }
                } else {
                    Util.printColor(32, 3, "\b\b\b\b\b\b\b\b\b\b\b" + time + "     ");
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
        if (a == CANCAL) Util.printlnColor(32, 3, String.valueOf(a));
        Util.printlnColor(32, 3, "输入：" + a);
        if (a == SYSTEM) {
            system();
            Util.printlnColor(32, 3, "返回牌局");
            a = inputInt();
        }
        return a;
    }

    private void system() {
        while (true) {
            Util.printlnColor(32, 4, "1剩余牌数 2弃牌数 3玩家 886关闭系统 " + CANCAL + "返回");
            int i = Integer.parseInt(scanner.nextLine());
            switch (i) {
                case 1:
                    Util.printlnColor(32, 0, "剩余牌数:" + StartGame.messageRequest.getUsingCardNum());
                    break;
                case 2:
                    Util.printlnColor(32, 0, "牌数:" + StartGame.messageRequest.getUsedCardNum());
                    break;
                case 3:
                    Util.printlnColor(32, 4, "选择玩家id(0<=id<" + StartGame.playerNum + ") " + CANCAL + "返回");
                    int i1 = Integer.parseInt(scanner.nextLine());
                    if (i1 == CANCAL) break;
                    if (i1 < 0 || i1 >= StartGame.playerNum) {
                        Util.printlnColor(32, 6, "超出范围");
                        break;
                    }
                    ShowPlayer player1 = StartGame.messageRequest.getShowPlayer(i1);
                    int blood = player1.getBlood();
                    int maxBlood = player1.getMaxBlood();
                    List<Card> equipCard = player1.getEquipCard();
                    int handCardNum = player1.getHandCardNum();
                    List<Card> decideCard = player1.getDecideCard();
                    Util.printlnColor(32, 0, "player:" + i1 + "   " + player1.getName() + "  " + player1.getCountry());
                    Util.printlnColor(32, 0, "体力：" + blood + "/" + maxBlood + "    手牌数量：" + handCardNum);
                    Util.printlnColor(32, 0, "装备：" + equipCard.toString() + "  判定：" + decideCard.toString());
                    break;
                case 886:
                    System.exit(0);
                    break;
                case CANCAL:
                    return;
                default:
                    Util.printlnColor(32, 4, "输入错误，返回");
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
