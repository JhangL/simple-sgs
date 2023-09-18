package com.jh.sgs.core;


import com.jh.sgs.base.interfaces.MessageReceipt;
import com.jh.sgs.base.interfaces.MessageRequest;
import com.jh.sgs.base.pojo.ShowCompletePlayer;
import com.jh.sgs.base.pojo.ShowPlayer;
import com.jh.sgs.core.data.DataBaseBasicData;
import com.jh.sgs.core.interfaces.BasicData;
import com.jh.sgs.core.interfaces.GameConfig;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.MessageReceipter;
import com.jh.sgs.core.pojo.OriginalPlayer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
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
        MessageReceipter.globalInContext("开始游戏");
        gameProcess = new GameProcess(playerManage.players.values());
        gameProcess.begin();
        log.info("回合前置完成，进入回合");
        roundMange = new RoundManage(gameProcess.getDesk());
        roundMange.init();
        MessageReceipter.globalInContext("开始回合");
        roundMange.begin();
    }


    private void check() {
        if (messageReceipt == null) log.warn("回调消息：无");
        else log.info("回调消息：" + messageReceipt.name());
        if (playerNum != 1 &&playerNum != 2 && (playerNum < 4 || playerNum > 10)) {
            log.warn(playerNum + "人数不支持");
            shutDown();
        } else log.info("人数：" + playerNum);
    }

    private void init() {
        playerManage = new PlayerManage();
        interactiveMachine = new InteractiveMachine();
        identityManage = new IdentityManage(basicData.getIdentity(playerNum));
        generalManage = new GeneralManage(basicData.getGenerals(),basicData.getSkill());
        cardManage = new CardManage(new ArrayList<>(basicData.getCards()), basicData.getCardParameter());
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
    public ShowCompletePlayer getPlayer(int id) {
        CompletePlayer completePlayer = gameProcess.getDesk().get(id);
        ShowCompletePlayer showCompletePlayer = new ShowCompletePlayer();
        showCompletePlayer.setId(completePlayer.getId());
        if (completePlayer.getCompleteGeneral() != null && completePlayer.getCompleteGeneral().getGeneral() != null) {
            showCompletePlayer.setName(completePlayer.getCompleteGeneral().getGeneral().getName());
            showCompletePlayer.setCountry(completePlayer.getCompleteGeneral().getGeneral().getCountry());
            showCompletePlayer.setSkills(completePlayer.getCompleteGeneral().getGeneral().getSkills());
        }
        showCompletePlayer.setIdentity(completePlayer.getIdentity());
        showCompletePlayer.setBlood(completePlayer.getBlood());
        showCompletePlayer.setMaxBlood(completePlayer.getMaxBlood());
        showCompletePlayer.setHandCard(completePlayer.getHandCard());
        showCompletePlayer.setDecideCard(completePlayer.getDecideCard());
        showCompletePlayer.setEquipCard(completePlayer.getEquipCard());
        return showCompletePlayer;
    }

    @Override
    public ShowPlayer getShowPlayer(int id) {
        CompletePlayer completePlayer = gameProcess.getDesk().get(id);
        ShowPlayer showPlayer = new ShowPlayer();
        showPlayer.setId(completePlayer.getId());
        if (completePlayer.getCompleteGeneral() != null && completePlayer.getCompleteGeneral().getGeneral() != null) {
            showPlayer.setName(completePlayer.getCompleteGeneral().getGeneral().getName());
            showPlayer.setCountry(completePlayer.getCompleteGeneral().getGeneral().getCountry());
        }
        showPlayer.setBlood(completePlayer.getBlood());
        showPlayer.setMaxBlood(completePlayer.getMaxBlood());
        showPlayer.setEquipCard(Util.arrayCloneToList(completePlayer.getEquipCard()));
        showPlayer.setDecideCard(Util.collectionCloneToList(completePlayer.getDecideCard()));
        showPlayer.setHandCardNum(completePlayer.getHandCard().size());
        return showPlayer;
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
