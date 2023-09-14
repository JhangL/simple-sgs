package com.jh.sgs.core.desktop;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.interfaces.MessageReceipt;
import com.jh.sgs.core.pojo.Card;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public abstract class CardDesktop extends Desktop{

    @Getter
    private Card card;
    @Getter
    private boolean cardUsed;
    @Getter
    private List<Card> processCards = new ArrayList<>();

    public CardDesktop(int player, Card card) {
        super(player);
        this.card = card;
    }


    public void useCard() {
        if (cardUsed) throw new SgsRuntimeException("desktop牌已使用");
        else {
            cardUsed = true;
        }
    }

    @Override
    protected void end() {
        MessageReceipt.globalInContext(getPlayer() + "完成操作" + getCard());
        if (!isCardUsed()) ContextManage.cardManage().recoveryCard(getCard());
        if (card.isFalsed())card.backTrue();
        ContextManage.cardManage().recoveryCard(getProcessCards());
    }
    @Override
    protected void error() {
        log.debug("{} {}执行出错，退牌", getPlayer(), getCard());
        if (card.isFalsed())card.backTrue();
        Util.getPlayer(getPlayer()).getHandCard().add(getCard());
    }
}
