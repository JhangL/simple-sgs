package com.jh.sgs.core;

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
}
