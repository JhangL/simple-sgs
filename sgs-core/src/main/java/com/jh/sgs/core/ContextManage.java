package com.jh.sgs.core;

import com.jh.sgs.core.desktop.DecideCardDesktop;
import com.jh.sgs.core.desktop.Desktop;
import com.jh.sgs.core.desktop.ExecuteCardDesktop;
import com.jh.sgs.core.desktop.ShaCardDesktop;
import com.jh.sgs.core.interfaces.MessageReceipt;

public class ContextManage {
    private static final ThreadLocal<GameEngine> gameEngineThreadLocal = new ThreadLocal<>();

    public static void setContext(GameEngine gameEngine) {
        gameEngineThreadLocal.set(gameEngine);
    }

    public static GameEngine gameEngine() {
        return gameEngineThreadLocal.get();
    }

    public static CardManage cardManage() {
        return gameEngineThreadLocal.get().getCardManage();
    }

    public static GeneralManage generalManage() {
        return gameEngineThreadLocal.get().getGeneralManage();
    }

    public static InteractiveMachine interactiveMachine() {
        return gameEngineThreadLocal.get().getInteractiveMachine();
    }

    public static MessageReceipt messageReceipt() {
        return gameEngineThreadLocal.get().getMessageReceipt();
    }

    public static Desk desk() {
        return gameEngineThreadLocal.get().getGameProcess().getDesk();
    }

    public static Desktop.Stack desktopStack() {
        return gameEngineThreadLocal.get().getRoundMange().getDesktopStack();
    }

    public static ExecuteCardDesktop executeCardDesktop() {
        return (ExecuteCardDesktop) gameEngineThreadLocal.get().getRoundMange().getDesktopStack().peek();
    }

    public static DecideCardDesktop decideCardDesktop() {
        return (DecideCardDesktop) gameEngineThreadLocal.get().getRoundMange().getDesktopStack().peek();
    }
    public static ShaCardDesktop shaCardDesktop() {
        return (ShaCardDesktop) gameEngineThreadLocal.get().getRoundMange().getDesktopStack().peek();
    }

    public static RoundManage roundManage() {
        return gameEngineThreadLocal.get().getRoundMange();
    }

    public static RoundProcess roundProcess(int player) {
        return gameEngineThreadLocal.get().getRoundMange().getRoundProcess(player);
    }
}
