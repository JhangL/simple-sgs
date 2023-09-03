package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class WuZhongShengYou extends OneSilkbagCard{
    @Override
    String getName() {
        return "无中生有";
    }

    @Override
    int getPlayer() {
        return ContextManage.executeCardDesktop().getPlayer();
    }

    @Override
    void effect(int player) {
        log.debug("无中生有：执行玩家：{}",player);
        CompletePlayer player1 = Util.getPlayer(player);
        //牌堆获得两张牌，给player
        List<Card> cards = ContextManage.cardManage().obtainCard(2);
        player1.getHandCard().addAll(cards);
        log.debug("无中生有完成：执行玩家：{}",player);
    }
}
