package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class TaoYuanJieYi extends MoreSilkbagCard {
    @Override
    List<CompletePlayer> getPlayer() {
        List<CompletePlayer> completePlayers = new ArrayList<>();
        completePlayers.add(Util.getDesktopMainPlayer());
        completePlayers.addAll(ContextManage.roundManage().findTarget(ContextManage.executeCardDesktop().getPlayer(), ContextManage.executeCardDesktop().getCard()));
        //过滤满血
        return completePlayers.stream().filter(completePlayer -> completePlayer.getBlood() != completePlayer.getMaxBlood()).collect(Collectors.toList());

    }

    @Override
    void effect(CompletePlayer completePlayer) {
        int mainPlayer = ContextManage.executeCardDesktop().getPlayer();
        log.debug("{}结义：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer);
        completePlayer.setBlood(completePlayer.getBlood() + 1);
        ContextManage.roundManage().addBlood(mainPlayer, completePlayer.getId(), ContextManage.executeCardDesktop().getCard());
        log.debug("{}完成：执行玩家：{}，被执行玩家：{}", getName(), mainPlayer, completePlayer);
    }

    @Override
    String getName() {
        return "桃园结义";
    }
}
