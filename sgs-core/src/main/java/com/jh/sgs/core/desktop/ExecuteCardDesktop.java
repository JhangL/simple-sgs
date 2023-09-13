package com.jh.sgs.core.desktop;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.card.Executable;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.Card;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ExecuteCardDesktop extends CardDesktop {

    private Executable executable;

    public ExecuteCardDesktop(int player, Card card) {
        super(player, card);
    }

    @Override
    protected void initCheck() {
        BaseCard baseCard = ContextManage.cardManage().getBaseCard(getCard());
        if (!(baseCard instanceof Executable)) throw new SgsApiException("该牌不可执行");
        executable = (Executable) baseCard;
    }

    @Override
    protected void execute() throws DesktopException {
        ContextManage.messageReceipt().global(getPlayer() + "出牌" + getCard());
        executable.execute();
    }

    public static void initCheck(Card card) {
        BaseCard baseCard = ContextManage.cardManage().getBaseCard(card);
        if (!(baseCard instanceof Executable)) throw new SgsApiException("该牌不可执行");
    }


}
