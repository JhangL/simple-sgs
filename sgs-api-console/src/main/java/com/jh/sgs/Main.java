package com.jh.sgs;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// 按两次 Shift 打开“随处搜索”对话框并输入 `show whitespaces`，
// 然后按 Enter 键。现在，您可以在代码中看到空格字符。
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scaner = new Scanner(System.in);
        while (true){
            System.out.println("[1] 查看状态");
            System.out.println("[3] exit");
            int i1 = scaner.nextInt();
            switch (i1) {
                case 1: {
                    FileReader fileReader = new FileReader("E:\\Users\\lenovo\\Desktop\\sentenal\\sgs\\web\\tsconfig.json");
                    System.out.println(fileReader.getEncoding());
                    JSONObject jsonObject = JSON.parseObject(fileReader);
                    JSONObject cardManage = jsonObject.getJSONObject("cardManage");
                    JSONArray usingCards = cardManage.getJSONArray("usingCards");
                    JSONArray usedCards = cardManage.getJSONArray("usedCards");
                    JSONArray chair = jsonObject.getJSONObject("gameProcess").getJSONObject("desk").getJSONArray("chair");
                    System.out.println("牌堆数量："+usingCards.size()+"  弃牌堆数量："+usedCards.size());
                    for (int i = 0; i < chair.size(); i++) {
                        JSONObject jsonObject1 = chair.getJSONObject(i);
                        System.out.print("player"+i);
                        int blood = jsonObject1.getIntValue("blood");
                        int blood1 = jsonObject1.getIntValue("maxBlood");
                        int identity = jsonObject1.getIntValue("identity");
                        JSONArray jsonArray = jsonObject1.getJSONArray("handCard");
                        ArrayList<Card> cards = new ArrayList<>();
                        List<Card> list = jsonArray.toList(Card.class);
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("equipCard");
                        ArrayList<Card> cards1 = new ArrayList<>();
                        Card[] list1 = jsonArray1.toArray(Card.class);
                        JSONArray jsonArray2 = jsonObject1.getJSONArray("decideCard");
                        ArrayList<Card> cards2 = new ArrayList<>();
                        List<Card> list2 = jsonArray2.toList(Card.class);
                        System.out.println("  体力："+blood+"/"+blood1+"    身份："+identity);
                        System.out.println("手牌："+ list.toString());
                        System.out.print("装备："+ Arrays.toString(list1));
                        System.out.println("  判定："+list2.toString());
                    }
                    fileReader.close();
                    break;
                }
                case 3: {
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Unknown Entry.");
                    break;
                }
            }

        }
    }
}