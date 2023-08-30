package com.jh.sgs.core;

import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.card.Executable;
import com.jh.sgs.core.exception.*;
import com.jh.sgs.core.pojo.Card;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Desktop {
    @Getter
    private final int player;
    @Getter
    private Card card;
    @Getter
    private List<Card> processCards = new ArrayList<>();
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
    private void start() throws DesktopRefuseException {
        ContextManage.messageReceipt().global(player+"出牌"+card);
        try {
            executable.execute();
        } catch (DesktopException e) {
            if (e instanceof DesktopRelationalException) {
                log.debug("{} {}执行发出关联阻挡", player, card);
                ContextManage.messageReceipt().global(player+"完成阻挡"+card);
                throw new DesktopRefuseException(e.getMessage());
            } else if (e instanceof DesktopRefuseException) {
                log.debug("{} {}执行阻挡", player, card);
                ContextManage.messageReceipt().global(player+"被阻挡"+card);
//                refuse();
            } else if (e instanceof DesktopErrorException) {
                ContextManage.messageReceipt().global(player+"出牌错误"+card);
                error();
            }
        }
        end();
    }

    private void end() {
        ContextManage.messageReceipt().global(player+"完成出牌"+card);
        ContextManage.cardManage().recoveryCard(card);
        ContextManage.cardManage().recoveryCard(processCards);
    }

    private void error() {
        if (isCardExecute) {

            log.debug("{} {}执行出错，退牌", player, card);
            Util.getPlayer(player).getHandCard().add(card);
        }
    }
//    private void refuse(){
//        if (isCardExecute){
//            log.debug("{} {}执行阻挡", player, card);
//            ContextManage.cardManage().recoveryCard(card);
//        }
//    }

    public static class Stack extends java.util.Stack<Desktop> {
        public void create(int player, Card card) {
            Desktop desktop = new Desktop(player, card);
            desktop.initCheck();
            push(desktop);
        }

        public void start() throws DesktopRefuseException {
            peek().start();
        }

        public void remove() throws DesktopRefuseException {
            if (empty()) {
                log.debug("不存在待处理桌面desktop");
                return;
            }
            try {
                peek().start();
            } finally {
                pop();
            }
        }
    }
}
