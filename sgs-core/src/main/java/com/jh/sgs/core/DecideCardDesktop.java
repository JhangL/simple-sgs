package com.jh.sgs.core;

import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.card.Decidable;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.pojo.Card;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DecideCardDesktop extends Desktop {
    @Getter
    private Card card;
    private boolean cardUsed;

    private Decidable decidable;

    public DecideCardDesktop(int player, Card card) {
        super(player);
        this.card = card;
    }

    public void useCard() {
        if (cardUsed) throw new SgsRuntimeException("desktop牌已使用");
        else cardUsed = true;
    }
    @Override
    protected void initCheck() {
        BaseCard baseCard = ContextManage.cardManage().getBaseCard(card);
        if (!(baseCard instanceof Decidable)) throw new SgsApiException("该牌不需要判定");
        decidable = (Decidable) baseCard;
    }

    public static void initCheck(Card card) {
        BaseCard baseCard = ContextManage.cardManage().getBaseCard(card);
        if (!(baseCard instanceof Decidable)) throw new SgsApiException("该牌不需要判定");
    }


    @Override
    protected void execute() throws DesktopException {
        log.debug("{} {}开始判定", getPlayer(), getCard());
        decidable.decide();
    }

    @Override
    protected void end() {
        ContextManage.messageReceipt().global(getPlayer() + "完成判定" + card);
        if (!cardUsed) ContextManage.cardManage().recoveryCard(card);
    }

    @Override
    protected void error() {

    }
}
