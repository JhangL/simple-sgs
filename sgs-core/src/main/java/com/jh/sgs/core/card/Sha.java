package com.jh.sgs.core.card;

import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pool.TPool;
import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.exception.DesktopPlayerDieException;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class Sha extends BaseCard implements Shaable{

    @Override
    public void shaExecute(int player) throws DesktopPlayerDieException {
        TPool<Card> card = new TPool<>();
        boolean b = ContextManage.roundManage().playSha(CardDesktop.playerInContext(), player, CardDesktop.cardInContext(), card);
        if (card.getPool()!=null) ContextManage.shaCardDesktop().getProcessCards().add(card.getPool());
        if (b){
            CompletePlayer player1 = Util.getPlayer(player);
            player1.setBlood(player1.getBlood()-1);
            TPool<Card> cardTPool = new TPool<>(CardDesktop.cardInContext());
            ContextManage.roundManage().subBlood(CardDesktop.playerInContext(),player,cardTPool,1);
            if (cardTPool.isEmpty())ContextManage.shaCardDesktop().useCard();

        }
    }

    @Override
    public int shaDistance() {
        return 1;
    }

    @Override
    String getName() {
        return "杀";
    }
}
