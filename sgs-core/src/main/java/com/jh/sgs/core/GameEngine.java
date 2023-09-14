package com.jh.sgs.core;


import com.jh.sgs.core.data.DataBaseBasicData;
import com.jh.sgs.core.interfaces.BasicData;
import com.jh.sgs.core.interfaces.GameConfig;
import com.jh.sgs.core.interfaces.MessageReceipt;
import com.jh.sgs.core.interfaces.MessageRequest;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.OriginalPlayer;
import com.jh.sgs.core.pojo.ShowPlayer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class GameEngine implements Runnable, MessageRequest {
    public static final ThreadGroup threadGroup = new ThreadGroup("game");
    public static final ThreadGroup threadGroupPlayer = new ThreadGroup("player");
    public static final String threadName = "game";
    @Getter
    private CardManage cardManage;
    @Getter
    private IdentityManage identityManage;
    @Getter
    private GeneralManage generalManage;
    private PlayerManage playerManage;
    @Getter
    private InteractiveMachine interactiveMachine;
    @Getter
    private GameProcess gameProcess;
    @Getter
    private RoundManage roundMange;


    @Setter
    @Getter
    private MessageReceipt messageReceipt;

    @Setter
//    BasicData basicData = new DataBaseBasicData("jdbc:mysql://localhost:3306/sgs", "root", "123456");
    BasicData basicData ;

    int playerNum;

    GameEngine(GameConfig gameConfig) {
        messageReceipt = gameConfig.messageReceipt();
        playerNum = gameConfig.playerNum();
        basicData = gameConfig.basicData();
    }

    GameEngine(MessageReceipt messageReceipt, int playerNum) {
        this.messageReceipt = messageReceipt;
        this.playerNum = playerNum;
        this.basicData=new DataBaseBasicData("classpath:dataBase.properties");
    }


    @Override
    public void run() {
        log.info("初始化系统{}{}", 1, 2);
        ContextManage.setContext(this);
        check();
        init();
        log.info("系统初始化完成");
        log.info("开局");
        MessageReceipt.globalInContext("开始游戏");
        gameProcess = new GameProcess(playerManage.players.values());
        gameProcess.begin();
        log.info("回合前置完成，进入回合");
        roundMange = new RoundManage(gameProcess.getDesk());
        roundMange.init();
        MessageReceipt.globalInContext("开始回合");
        roundMange.begin();
    }


    private void check() {
        if (messageReceipt == null) log.warn("回调消息：无");
        else log.info("回调消息：" + messageReceipt.name());
        if (playerNum != 2 && (playerNum < 4 || playerNum > 10)) {
            log.warn(playerNum + "人数不支持");
            shutDown();
        } else log.info("人数：" + playerNum);
    }

    private void init() {
        playerManage = new PlayerManage();
        interactiveMachine = new InteractiveMachine();
        identityManage = new IdentityManage(basicData.getIdentity(playerNum));
        generalManage = new GeneralManage(basicData.getGenerals());
        cardManage = new CardManage(basicData.getCards(), basicData.getCardParameter());
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

    void shutDown() {
        log.info("系统关闭");
        Thread.currentThread().interrupt();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getAll() {
        return "{" +
                "\"cardManage\":" + cardManage.getStatus() +
                ", \"identityManage\":" + identityManage.getStatus() +
                ", \"interactiveMachine\":" + interactiveMachine.getStatus() +
                ", \"gameProcess\":" + gameProcess.getStatus() +
                ", \"roundMange\":" + "\"roundMange\"" +
                ", \"playerNum\":" + playerNum +
                '}';
    }

    @Override
    public CompletePlayer getPlayer(int id) {
        return gameProcess.getDesk().get(id);
    }

    @Override
    public ShowPlayer getShowPlayer(int id) {
        CompletePlayer completePlayer = gameProcess.getDesk().get(id);
        return new ShowPlayer(completePlayer);
    }

    @Override
    public int getUsingCardNum() {
        return cardManage.getUsingCardsNum();
    }

    @Override
    public int getUsedCardNum() {
        return cardManage.getUsedCardsNum();
    }
}
