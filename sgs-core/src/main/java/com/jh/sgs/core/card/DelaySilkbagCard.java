package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.DesktopRefuseException;
import com.jh.sgs.core.pojo.Card;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public abstract class DelaySilkbagCard extends OneSilkbagCard implements Decidable {
    @Override
    public void decide() {
        log.debug("{}执行判定-->", getName());
        try {
            ContextManage.roundManage().wxkjCheck();
        } catch (DesktopRefuseException e) {
            log.debug("{}被阻挡", getName());
            decideFalse();
        }
        List<Card> cards = ContextManage.cardManage().obtainCard(1);
        Card card = cards.get(0);
        log.debug("{}判定牌：{}", getName(), card);
        boolean b = decideTerm(card);
        if (b) {
            log.debug("{}判定成功", getName());
            decideTrue();
        } else {
            log.debug("{}判定失败", getName());
            decideFalse();
        }
    }

    abstract boolean decideTerm(Card card);

    abstract void decideTrue();

    abstract void decideFalse();

    @Override
    public void effect() throws DesktopException {
        int player = getPlayer();
        ContextManage.messageReceipt().global(ContextManage.desktop().getPlayer() + "将对" + player + "使用" + ContextManage.desktop().getCard());
        effect(player);
        ContextManage.messageReceipt().global(ContextManage.desktop().getPlayer() + "完成对" + player + "使用" + ContextManage.desktop().getCard());
    }
}
