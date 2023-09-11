package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.enums.InteractiveEnum;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class QingLongYanYueDao extends WeaponCard{
    @Override
    public void shaExecute(int player) {
        CompletePlayer player1 = Util.getPlayer(player);
        int mainplayer = ContextManage.shaCardDesktop().getPlayer();
        CompletePlayer maincompletePlayer = Util.getPlayer(mainplayer);
        Card[] card=new Card[1];
        boolean b = ContextManage.roundManage().playSha(mainplayer, player, ContextManage.shaCardDesktop().getCard(), card);
        if (card[0]!=null) ContextManage.shaCardDesktop().getProcessCards().add(card[0]);
        if (b){
            player1.setBlood(player1.getBlood()-1);
            ContextManage.roundManage().subBlood(mainplayer,player,ContextManage.shaCardDesktop().getCard(),1);
        }else {
            //青龙偃月刀特殊效果
            while (card[0]!=null){//被闪闪避
                boolean[] tofs=new boolean[1];
                ContextManage.interactiveMachine().addEvent(mainplayer, "是否使用青龙偃月刀", new Interactiveable() {

                    boolean a=false;

                    @Override
                    public void trueOrFalse(boolean tof) {
                        tofs[0]=tof;
                        a=true;
                    }

                    @Override
                    public void cancel() {
                        trueOrFalse(false);
                    }

                    @Override
                    public InteractiveEvent.CompleteEnum complete() {
                        return a? InteractiveEvent.CompleteEnum.COMPLETE: InteractiveEvent.CompleteEnum.NOEXECUTE;
                    }

                    @Override
                    public InteractiveEnum type() {
                        return InteractiveEnum.TOF;
                    }
                });
                if (tofs[0]){
                    card[0]=null;
                    Card[] cards = new Card[1];ContextManage.roundManage().playCard(mainplayer, "请出杀", cards, card1 -> {
                        if (card1.getNameId() != CardEnum.SHA.getId())
                            throw new SgsApiException("指定牌不为杀");
                    },false);
                    if (cards[0]!=null){
                        ContextManage.shaCardDesktop().getProcessCards().add(cards[0]);
                        boolean b1 = ContextManage.roundManage().playSha(mainplayer, player, cards[0], card);
                        if (card[0]!=null) ContextManage.shaCardDesktop().getProcessCards().add(card[0]);
                        if(b1){
                            player1.setBlood(player1.getBlood()-1);
                            ContextManage.roundManage().subBlood(mainplayer,player,ContextManage.shaCardDesktop().getCard(),1);
                        }
                    }
                }
            }
        }

    }

    @Override
    public int shaDistance() {
        return 3;
    }


    @Override
    String getName() {
        return "青龙偃月刀";
    }
}
