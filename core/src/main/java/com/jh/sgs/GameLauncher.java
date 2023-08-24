package com.jh.sgs;

import com.jh.sgs.core.GameEngine;
import com.jh.sgs.interfaces.MessageReceipt;
import com.jh.sgs.interfaces.ShowStatus;
import lombok.Getter;

public class GameLauncher {
    public static final ThreadGroup threadGroup = new ThreadGroup("game");
    public static final String threadName = "game";

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

    public static ShowStatus run(MessageReceipt messageReceipt, int playerNum) {
        GameLauncher gameLauncher = new GameLauncher(messageReceipt, playerNum);
        gameLauncher.start();

       return gameLauncher.getGameEngine();
    }

    public static ShowStatus run() {
        GameLauncher gameLauncher = new GameLauncher();
        gameLauncher.start();
        return gameLauncher.getGameEngine();
    }

}
