package com.jh.sgs.core;

import com.jh.sgs.base.interfaces.MessageReceipt;
import com.jh.sgs.core.desktop.DecideCardDesktop;
import com.jh.sgs.core.desktop.Desktop;
import com.jh.sgs.core.desktop.ExecuteCardDesktop;
import com.jh.sgs.core.desktop.ShaCardDesktop;
import com.jh.sgs.core.exception.SgsRuntimeException;

public class ContextManage {
    private static final ThreadLocal<GameEngine> gameEngineThreadLocal = new ThreadLocal<>();

    public static void setContext(GameEngine gameEngine) {
        gameEngineThreadLocal.set(gameEngine);
    }

    public static GameEngine gameEngine() {
        GameEngine gameEngine = gameEngineThreadLocal.get();
        if (gameEngine == null)
            throw new SgsRuntimeException("当前上下文不存在gameEngine，请不要在其他地方执行上下文相关操作");
        return gameEngine;
    }

    public static CardManage cardManage() {
        return gameEngine().getCardManage();
    }

    public static GeneralManage generalManage() {
        return gameEngine().getGeneralManage();
    }

    public static InteractiveMachine interactiveMachine() {
        return gameEngine().getInteractiveMachine();
    }

    public static MessageReceipt messageReceipt() {
        return gameEngine().getMessageReceipt();
    }

    public static Desk desk() {
        return gameEngine().getGameProcess().getDesk();
    }

    public static Desktop.Stack desktopStack() {
        return gameEngine().getRoundMange().getDesktopStack();
    }

    public static ExecuteCardDesktop executeCardDesktop() {
        return (ExecuteCardDesktop) gameEngine().getRoundMange().getDesktopStack().peek();
    }

    public static DecideCardDesktop decideCardDesktop() {
        return (DecideCardDesktop) gameEngine().getRoundMange().getDesktopStack().peek();
    }

    public static ShaCardDesktop shaCardDesktop() {
        return (ShaCardDesktop) gameEngine().getRoundMange().getDesktopStack().peek();
    }

    public static RoundManage roundManage() {
        return gameEngine().getRoundMange();
    }

    public static RoundProcess roundProcess(int player) {
        return gameEngine().getRoundMange().getRoundProcess(player);
    }
}
