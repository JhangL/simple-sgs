package com.jh.sgs.core.desktop;

import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.RoundProcess;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.card.Shaable;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.interfaces.MessageReceipt;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ShaCardDesktop extends CardDesktop {

    private Shaable shaable;

    public ShaCardDesktop(int player, Card card) {
        super(player,card);
    }

    @Override
    protected void initCheck() {
        if (getCard().getNameId()!= CardEnum.SHA.getId()) throw new SgsApiException("该牌不是杀");
        Card card1 = Util.getPlayer(getPlayer()).getEquipCard()[0];
        if (card1== null) shaable= (Shaable) CardEnum.SHA.getBaseCard();
        else shaable = (Shaable) CardEnum.getById(card1.getNameId()).getBaseCard();
    }

    @Override
    protected void execute() throws DesktopException {
        MessageReceipt.globalInContext(getPlayer() + "出牌" + getCard());
        RoundProcess roundProcess = ContextManage.roundProcess(getPlayer());
        roundProcess.setUseSha(roundProcess.getUseSha()+1);
        shaable.sha();
    }

    public static void initCheck(Card card) {
        if (card.getNameId()!= CardEnum.SHA.getId()) throw new SgsApiException("该牌不是杀");
    }

    @Override
    protected void error() {
        super.error();
        RoundProcess roundProcess = ContextManage.roundProcess(getPlayer());
        roundProcess.setUseSha(roundProcess.getUseSha()-1);
    }
}
