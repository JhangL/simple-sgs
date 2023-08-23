package com.jh.sgs.core;

import com.jh.sgs.interfaces.MessageReceipt;

public class ContextManage {
    private static ThreadLocal<GameEngine> gameEngineThreadLocal;
    public static void setContext(GameEngine gameEngine){
        gameEngineThreadLocal=gameEngine;
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
}
