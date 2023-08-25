package com.jh.sgs.core;

import com.jh.sgs.core.interfaces.MessageReceipt;
import com.jh.sgs.core.interfaces.MessageRequest;
import lombok.Getter;

import static com.jh.sgs.core.GameEngine.threadGroup;
import static com.jh.sgs.core.GameEngine.threadName;

public class GameLauncher {

    @Getter
    private GameEngine gameEngine;
    @Getter
    private Thread thread;

    public GameLauncher() {
        gameEngine = new GameEngine();
        thread = new Thread(threadGroup, gameEngine);
    }

    public GameLauncher(MessageReceipt messageReceipt, int playerNum) {
        gameEngine = new GameEngine(messageReceipt, playerNum);
        thread = new Thread(threadGroup, gameEngine,threadName);
    }

    public void start() {
        thread.start();
    }

    public static MessageRequest run(MessageReceipt messageReceipt, int playerNum) {
        GameLauncher gameLauncher = new GameLauncher(messageReceipt, playerNum);
        gameLauncher.start();

       return gameLauncher.getGameEngine();
    }

    public static MessageRequest run() {
        GameLauncher gameLauncher = new GameLauncher();
        gameLauncher.start();
        return gameLauncher.getGameEngine();
    }

}
