package com.jh.sgs;

import com.jh.sgs.core.GameLauncher;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interfaces.MessageReceipt;
import com.jh.sgs.core.interfaces.MessageRequest;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// 按两次 Shift 打开“随处搜索”对话框并输入 `show whitespaces`，
// 然后按 Enter 键。现在，您可以在代码中看到空格字符。
@Log4j2
public class Main {
    public static void main(String[] args) throws SgsApiException, InterruptedException, IOException {
        MessageRequest run = GameLauncher.run(new MessageReceipt() {
            @Override
            public void personal(int player, String message) {
                System.out.println("玩家" + player + "：" + message);
            }

            @Override
            public void global(String message) {
                System.out.println("世界：" + message);
            }

            @Override
            public void system(List<InteractiveEvent> interactiveEvents) {
                for (InteractiveEvent interactiveEvent : interactiveEvents) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {

                    }
                    log.debug(interactiveEvent.getMessage());
                    if (!"请出牌".equals(interactiveEvent.getMessage())) {
                        interactiveEvent.cancel();
                        continue;
                    }
                    System.out.println(interactiveEvent.interactive().handCard());
                    if (interactiveEvent.interactive().handCard().stream().anyMatch(card -> {
                        int nameId = card.getNameId();
                        return nameId >= 27 && nameId <= 32;
                    })) {

                        Scanner scaner = new Scanner(System.in);
                        while (true) {
                            int i = scaner.nextInt();
                            try {
                                interactiveEvent.interactive().playCard(i);
                                break;
                            } catch (Exception e) {
                            }
                        }
                    } else {
                        interactiveEvent.interactive().cancelPlayCard();
                    }
                    interactiveEvent.complete();
                }
            }

            @Override
            public String name() {
                return "测试回调器";
            }
        }, 5);
//        Thread.sleep(500);
//        Scanner scaner = new Scanner(System.in);
//        while (true) {
//            System.out.println("[1111] 查看状态");
//            System.out.println("[2] Write results to text file");
//            System.out.println("[3333] exit");
//            int i = scaner.nextInt();
//
//            switch (i) {
//                case 1111: {
//                    System.out.println("查看状态");
//                    FileWriter fileWriter = new FileWriter("E:\\Users\\lenovo\\Desktop\\sentenal\\sgs\\status.html", false);
//                    fileWriter.write(run.getStatus());
//                    fileWriter.flush();
//                    fileWriter.close();
//                    break;
//                }
//                case 2222: {
//
//                    System.out.println(" Press any key to continue..");
//                    break;
//                }
//                case 3333: {
//                    System.exit(0);
//                    break;
//                }
//                default: {
//                    System.out.println("Unknown Entry.");
//                    break;
//                }
//            }
//
//        }
    }
}