package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pool.TPool;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class Sha extends BaseCard implements Shaable{

    @Override
    public void shaExecute(int player) {
        TPool<Card> card = new TPool<>();
        boolean b = ContextManage.roundManage().playSha(ContextManage.shaCardDesktop().getPlayer(), player, ContextManage.shaCardDesktop().getCard(), card);
        if (card.getPool()!=null) ContextManage.shaCardDesktop().getProcessCards().add(card.getPool());
        if (b){
            CompletePlayer player1 = Util.getPlayer(player);
            player1.setBlood(player1.getBlood()-1);
            TPool<Card> cardTPool = new TPool<>(ContextManage.shaCardDesktop().getCard());
            ContextManage.roundManage().subBlood(ContextManage.shaCardDesktop().getPlayer(),player,cardTPool,1);
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
