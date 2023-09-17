package com.jh.sgs.core.desktop;

import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.core.CardManage;
import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.card.Executable;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.pojo.MessageReceipter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ExecuteCardDesktop extends CardDesktop {

    private Executable executable;

    public ExecuteCardDesktop(int player, Card card) {
        super(player, card);
    }

    @Override
    protected void initCheck() {
        BaseCard baseCard = CardManage.getBaseCard(getCard());
        if (!(baseCard instanceof Executable)) throw new SgsApiException("该牌不可执行");
        executable = (Executable) baseCard;
    }

    @Override
    protected void execute() throws DesktopException {
        MessageReceipter.globalInContext(getPlayer() + "出牌" + getCard());
        executable.execute();
    }

    public static void initCheck(Card card) {
        BaseCard baseCard = CardManage.getBaseCard(card);
        if (!(baseCard instanceof Executable)) throw new SgsApiException("该牌不可执行");
    }


}
