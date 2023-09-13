package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.enums.SuitEnum;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pool.TPool;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ShanDian extends DelaySilkbagCard {
    @Override
    boolean decideTerm(Card card) {
        if (SuitEnum.HEIT==SuitEnum.getByIndex(card.getSuit())) {
            switch (card.getNum()) {
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    @Override
    void decideTrue() {
        CompletePlayer player = Util.getPlayer(ContextManage.decideCardDesktop().getPlayer());
        player.setBlood(player.getBlood() - 3);
        TPool<Card> cardTPool = new TPool<>(ContextManage.decideCardDesktop().getCard());
        ContextManage.roundManage().subBlood(-1, player.getId(), cardTPool, 3);
        if (cardTPool.isEmpty())ContextManage.decideCardDesktop().useCard();
    }

    @Override
    void decideFalse() {
        Card card = ContextManage.decideCardDesktop().getCard();
        int i = ContextManage.desk().nextOnDesk(ContextManage.decideCardDesktop().getPlayer());
        Util.getPlayer(i).getDecideCard().add(0, card);
        ContextManage.roundManage().statusRefresh(-1,i);
        ContextManage.decideCardDesktop().useCard();
    }

    @Override
    int getPlayer() throws DesktopErrorException {
        return ContextManage.executeCardDesktop().getPlayer();
    }

    @Override
    String getName() {
        return "闪电";
    }
}
