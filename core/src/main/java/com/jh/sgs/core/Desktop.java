package com.jh.sgs.core;

import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.card.ExecutableCard;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class Desktop {
    @Getter
    private CompletePlayer completePlayer;
    @Getter
    private Card card;
    private ExecutableCard executableCard;
    private boolean isCardExecute;

    public Desktop(CompletePlayer completePlayer, Card card) {
        this.completePlayer = completePlayer;
        this.card = card;
        isCardExecute = true;
    }

    private void initCheck() {
        if (isCardExecute) {
            BaseCard baseCard = ContextManage.cardManage().getBaseCard(card);
            if (!(baseCard instanceof ExecutableCard)) throw new SgsApiException("该牌不为可执行牌");
            executableCard = (ExecutableCard) baseCard;
        }
    }

    private void start() {
        executableCard.execute();
    }

    public static class Stack extends java.util.Stack<Desktop> {
        public void create(CompletePlayer completePlayer, Card card) {
            Desktop desktop = new Desktop(completePlayer, card);
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
