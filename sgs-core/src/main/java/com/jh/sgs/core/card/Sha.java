package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class Sha extends BaseCard implements Shaable{

    @Override
    public void shaExecute(int player) {
        Card[] card=new Card[1];
        boolean b = ContextManage.roundManage().playSha(ContextManage.shaCardDesktop().getPlayer(), player, ContextManage.shaCardDesktop().getCard(), card);
        if (card[0]!=null) ContextManage.shaCardDesktop().getProcessCards().add(card[0]);
        if (b){
            CompletePlayer player1 = Util.getPlayer(player);
            player1.setBlood(player1.getBlood()-1);
            ContextManage.roundManage().subBlood(ContextManage.shaCardDesktop().getPlayer(),player,ContextManage.shaCardDesktop().getCard(),1);
        }
    }

    @Override
    public int shaDistance() {
        return 1;
    }
}
