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
    private CardManage cardManage;
    @Getter
    private IdentityManage identityManage;
    @Getter
    private GeneralManage generalManage;
    private PlayerManage playerManage;
    @Getter
    private InteractiveMachine interactiveMachine;
    private GameProcess gameProcess;
    private RoundMange roundMange;


    @Setter
    @Getter
    private MessageReceipt messageReceipt;

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
        log.info("回合前置完成，进入回合");
        roundMange = new RoundMange(gameProcess.getDesk());
        roundMange.begin();
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
