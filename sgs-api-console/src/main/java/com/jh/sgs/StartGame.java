package com.jh.sgs;

import com.jh.sgs.core.GameLauncher;
import com.jh.sgs.core.interfaces.BasicData;
import com.jh.sgs.core.interfaces.GameConfig;
import com.jh.sgs.core.interfaces.MessageReceipt;
import com.jh.sgs.core.interfaces.MessageRequest;
import com.jh.sgs.text.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class StartGame {
    public static Inputer inputer = new Inputer(System.in, -1);
    public static MessageRequest messageRequest;
    public static int playerNum=2;

    public static MessageReceipt messageReceipt=new TextMessageReceipt();
    public static BasicData basicData=new DataBaseBasicDataCache("classpath:dataBase.properties");

    public static void main(String[] args) throws IOException {
        File file1 = new File("./sgslog");
        System.out.println("运行文件地址："+file1.getAbsolutePath());
        if (!file1.exists()) {
            file1.mkdirs();// 能创建多级目录
        }
        if (args.length>=2){
            if ("-server".equals(args[0])){
                int s= Integer.parseInt(args[1]);

                System.out.println("运行文件地址："+file1.getAbsolutePath());
                for (int i = 0; i < s; i++) {
                    File file = new File("./sgslog/server-" + i + ".log");
                    if (!file.exists()) {
                        file.createNewFile();// 能创建多级目录
                    }
//                    GameLauncher.run(new AutoMessageReceipt(file),2);
                    GameLauncher.run(new GameConfig() {
                        @Override
                        public MessageReceipt messageReceipt() {
                            return new AutoMessageReceipt(file);
                        }

                        @Override
                        public int playerNum() {
                            return 2;
                        }

                        @Override
                        public BasicData basicData() {
                            return basicData;
                        }
                    });
                    System.out.println("成功开启 "+i);
                }
                System.out.println("完成开启");

                Scanner scanner = new Scanner(System.in);
                while (true){
                    System.out.println("输入886关闭");
                    String s1 = scanner.nextLine();
                    try {
                        int aa= Integer.parseInt(s1);
                        if (aa==886){
                            System.out.println("关闭系统");
                            System.exit(0);
                        }
                    }catch (Exception e){
                        System.err.println("请输入数字");
                    }
                }
            }
        }
        File file = new File("./sgslog/server-one.log");
        if (!file.exists()) {
            file.createNewFile();// 能创建多级目录
        }
        messageRequest = GameLauncher.run(new GameConfig() {
            @Override
            public MessageReceipt messageReceipt() {
                return new FileMessageConsoleControlReceipt(file);
            }

            @Override
            public int playerNum() {
                return 2;
            }

            @Override
            public BasicData basicData() {
                return basicData;
            }
        });
    }
}
