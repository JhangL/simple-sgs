package com.jh.sgs.core;


import com.jh.sgs.data.DataBaseBasicData;
import com.jh.sgs.exception.SgsException;
import com.jh.sgs.interfaces.BasicData;
import com.jh.sgs.interfaces.Interactive;
import com.jh.sgs.interfaces.MessageReceipt;
import com.jh.sgs.pojo.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
public class GameEngine implements Runnable {
    @Getter
    CardManage cardManage;
    IdentityManage identityManage;
    @Getter
    GeneralManage generalManage;
    PlayerManage playerManage;
    @Getter
    InteractiveMachine interactiveMachine;
    GameProcess gameProcess;


    @Setter
    @Getter
    MessageReceipt messageReceipt;

    @Setter
    BasicData basicData = new DataBaseBasicData("jdbc:mysql://localhost:3306/sgs", "root", "123456");

    int playerNum;

    public GameEngine() {
    }

    public GameEngine(MessageReceipt messageReceipt, int playerNum) {
        this.messageReceipt = messageReceipt;
        this.playerNum = playerNum;
    }


    @Override
    public void run() {
        ContextManage.setContext(this);
        check();
        init();
        gameProcess = new GameProcess(playerManage.players.values());
        gameProcess.begin();
    }


    private void check() {
        if (messageReceipt == null) log.warn("未使用回调消息");
        if (playerNum < 4 || playerNum > 10) log.warn("人数不支持");
    }

    private void init() {
        playerManage = new PlayerManage();
        interactiveMachine = new InteractiveMachine();
        identityManage = new IdentityManage(basicData.getIdentity(playerNum));
        generalManage = new GeneralManage(basicData.getGenerals());
        cardManage = new CardManage(basicData.getCards());
    }

    class GameProcess {

        Desk desk;

        GameProcess(Collection<OriginalPlayer> players) {
            CompletePlayer[] array = players.stream().map(CompletePlayer::new).toArray(value -> new CompletePlayer[players.size()]);
            desk = new Desk(array);
        }

        void begin() {
            distributeIdentity();
            selectGeneral();
            setBlood();
            originalHandCard();
            System.out.println("系统完成");
        }

        private void originalHandCard() {
            desk.foreach(completePlayer -> completePlayer.getHandCard().addAll(cardManage.obtainCard(4)));

        }

        private void selectGeneral() {
            log.info("选择武将");
            messageReceipt.global("选择武将");
            CompletePlayer[] chair = desk.getChair();
            for (int i = 0; i < chair.length; i++) {
                int finalI = i;
                interactiveMachine.addEvent(i, new Interactive() {

                    int step = 0;

                    @Override
                    public void setGeneral(General general) {
                        desk.get(finalI).setGeneral(general);
                        step++;
                    }

                    @Override
                    public List<General> getSelectableGeneral() {
                        return generalManage.getAll();
                    }


                    @Override
                    public boolean complete() {
                        return step == 1;
                    }
                });
            }
            interactiveMachine.lock();
        }

        /**
         * 设置初始血量
         */
        private void setBlood() {
            desk.foreach(completePlayer -> {
                if (completePlayer.getIdentity() == Identity.ZG) {
                    completePlayer.setBlood(completePlayer.getGeneral().getBlood() + 1);
                    desk.setIndex(completePlayer);
                } else {
                    completePlayer.setBlood(completePlayer.getGeneral().getBlood());
                }
            });
        }

        /**
         * 分配身份;
         */
        private void distributeIdentity() {
            List<Identity> distribute = identityManage.distribute();
            if (distribute.size() != desk.size()) throw new SgsException("身份个数与实际不符");
            Iterator<Identity> iterator = distribute.iterator();
            desk.foreach(completePlayer -> completePlayer.setIdentity(iterator.next()));
        }

        //***************************


        //***************************
    }


    class PlayerManage {
        Map<String, OriginalPlayer> players;

        public PlayerManage() {
            players = new HashMap<>();

            for (int i = 0; i < playerNum; i++) {
                String id = "game:" + i;
                players.put(id, new OriginalPlayer(id));
            }
        }

    }
}
