package com.jh.sgs.core;

import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.card.Executable;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.Card;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class Desktop {
    @Getter
    private final int player;
    @Getter
    private Card card;
    private Executable executable;
    private boolean isCardExecute;

    public Desktop(int player, Card card) {
        this.player = player;
        this.card = card;
        isCardExecute = true;
    }

    private void initCheck() {
        if (isCardExecute) {
            BaseCard baseCard = ContextManage.cardManage().getBaseCard(card);
            if (!(baseCard instanceof Executable)) throw new SgsApiException("该牌不可执行");
            executable = (Executable) baseCard;
        }
    }

    /**
     * 执行操作
     */
    private void start() {
        try {
            executable.execute();
        }catch (Exception e){
            e.printStackTrace();
            error();
        }

    }
    private void error(){
        if (isCardExecute){
            log.debug("{} {}执行出错，退牌",player,card);
            Util.getPlayer(player).getHandCard().add(card);
        }
    }

    public static class Stack extends java.util.Stack<Desktop> {
        public void create(int player, Card card) {
            Desktop desktop = new Desktop(player, card);
            desktop.initCheck();
            push(desktop);
        }

        public void start() {
            peek().start();
        }

        public void remove() {
            if (empty()) {
                log.debug("不存在待处理桌面desktop");
                return;
            }
            peek().start();
            pop();
        }
    }
}
