package com.jh.sgs.core.desktop;

import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.core.CardManage;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.card.Decidable;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.pojo.MessageReceipter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DecideCardDesktop extends CardDesktop {

    private Decidable decidable;

    public DecideCardDesktop(int player, Card card) {
    super(player, card);
    }

    @Override
    protected void initCheck() {
        BaseCard baseCard = CardManage.getBaseCard(getCard());
        if (!(baseCard instanceof Decidable)) throw new SgsApiException("该牌不需要判定");
        decidable = (Decidable) baseCard;
    }

    public static void initCheck(Card card) {
        BaseCard baseCard = CardManage.getBaseCard(card);
        if (!(baseCard instanceof Decidable)) throw new SgsApiException("该牌不需要判定");
    }


    @Override
    protected void execute() throws DesktopException {
        log.debug("{} {}开始判定", getPlayer(), getCard());
        MessageReceipter.globalInContext("{} {}开始判定", getPlayer(), getCard());
        decidable.decide();
    }

    @Override
    protected void end() {
        MessageReceipter.globalInContext(getPlayer() + "完成判定" + getCard());
        if (!isCardUsed()) ContextManage.cardManage().recoveryCard(getCard());
    }

    @Override
    protected void error() {

    }
}
