package com.jh.sgs.core;

import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.card.Executable;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.pojo.Card;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ExecuteCardDesktop extends Desktop {
    @Getter
    private Card card;
    private boolean cardUsed;
    @Getter
    private List<Card> processCards = new ArrayList<>();
    private Executable executable;

    public ExecuteCardDesktop(int player, Card card) {
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
        if (!(baseCard instanceof Executable)) throw new SgsApiException("该牌不可执行");
        executable = (Executable) baseCard;
    }

    @Override
    protected void execute() throws DesktopException {
        ContextManage.messageReceipt().global(getPlayer() + "出牌" + card);
        executable.execute();
    }

    public static void initCheck(Card card) {
        BaseCard baseCard = ContextManage.cardManage().getBaseCard(card);
        if (!(baseCard instanceof Executable)) throw new SgsApiException("该牌不可执行");
    }

    @Override
    protected void end() {
        ContextManage.messageReceipt().global(getPlayer() + "完成出牌" + card);
        if (!cardUsed) ContextManage.cardManage().recoveryCard(card);
        ContextManage.cardManage().recoveryCard(processCards);
    }
    @Override
    protected void error() {
        log.debug("{} {}执行出错，退牌", getPlayer(), card);
        Util.getPlayer(getPlayer()).getHandCard().add(card);
    }
}
