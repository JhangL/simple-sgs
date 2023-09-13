package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.impl.TOFImpl;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pool.BooleanPool;
import com.jh.sgs.core.pool.TPool;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class QingLongYanYueDao extends WeaponCard{
    @Override
    public void shaExecute(int player) {
        CompletePlayer player1 = Util.getPlayer(player);
        int mainplayer = ContextManage.shaCardDesktop().getPlayer();
        CompletePlayer maincompletePlayer = Util.getPlayer(mainplayer);
        TPool<Card> card = new TPool<>();
        boolean b = ContextManage.roundManage().playSha(mainplayer, player, ContextManage.shaCardDesktop().getCard(), card);
        if (card.getPool()!=null) ContextManage.shaCardDesktop().getProcessCards().add(card.getPool());
        if (b){
            player1.setBlood(player1.getBlood()-1);
            TPool<Card> cardTPool = new TPool<>(ContextManage.shaCardDesktop().getCard());
            ContextManage.roundManage().subBlood(mainplayer, player,cardTPool, 1);
            if (cardTPool.isEmpty())ContextManage.shaCardDesktop().useCard();
        }else {
            //青龙偃月刀特殊效果
            while (card.getPool()!=null){//被闪闪避
                BooleanPool tofs = new BooleanPool();
                ContextManage.interactiveMachine().addEvent(mainplayer, "是否使用青龙偃月刀",new TOFImpl(tofs));
                if (tofs.isPool()){
                    card.setPool(null);
                    TPool<Card> cards = new TPool<>();
                    ContextManage.roundManage().playCard(mainplayer, "请出杀", cards, card1 -> {
                        if (card1.getNameId() != CardEnum.SHA.getId())
                            throw new SgsApiException("指定牌不为杀");
                    },false);
                    if (cards.getPool()!=null){
                        ContextManage.shaCardDesktop().getProcessCards().add(cards.getPool());
                        boolean b1 = ContextManage.roundManage().playSha(mainplayer, player, cards.getPool(), card);
                        if (card.getPool()!=null) ContextManage.shaCardDesktop().getProcessCards().add(card.getPool());
                        if(b1){
                            player1.setBlood(player1.getBlood()-1);
                            TPool<Card> cardTPool = new TPool<>(ContextManage.shaCardDesktop().getCard());
                            ContextManage.roundManage().subBlood(mainplayer, player,cardTPool, 1);
                            if (cardTPool.isEmpty())ContextManage.shaCardDesktop().useCard();
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
