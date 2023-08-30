package com.jh.sgs.core;

import com.jh.sgs.core.interfaces.MessageReceipt;

public class ContextManage {
    private static final ThreadLocal<GameEngine> gameEngineThreadLocal= new ThreadLocal<>() ;
    public static void setContext(GameEngine gameEngine){
        gameEngineThreadLocal.set(gameEngine);
    }
    public static GameEngine gameEngine(){
        return gameEngineThreadLocal.get();
    }
    public static CardManage cardManage(){
        return gameEngineThreadLocal.get().getCardManage();
    }
    public static GeneralManage generalManage(){
        return gameEngineThreadLocal.get().getGeneralManage();
    }
    public static InteractiveMachine interactiveMachine(){
        return gameEngineThreadLocal.get().getInteractiveMachine();
    }
    public static MessageReceipt messageReceipt(){
        return gameEngineThreadLocal.get().getMessageReceipt();
    }
    public static Desk desk(){
        return gameEngineThreadLocal.get().getGameProcess().getDesk();
    }
    public static Desktop.Stack desktopStack(){
        return gameEngineThreadLocal.get().getRoundMange().getDesktopStack();
    }
    public static Desktop desktop(){
        return gameEngineThreadLocal.get().getRoundMange().getDesktopStack().peek();
    }
    public static RoundManage roundManage(){
        return gameEngineThreadLocal.get().getRoundMange();
    }
}
