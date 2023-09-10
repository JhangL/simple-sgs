package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.enums.SuitEnum;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class LeBuSiShu extends DelaySilkbagCard {
    @Override
    boolean decideTerm(Card card) {
        return SuitEnum.HONGT!=SuitEnum.getByIndex(card.getSuit());
    }

    @Override
    void decideTrue() {
        ContextManage.roundProcess(ContextManage.decideCardDesktop().getPlayer()).getPlayCardBool().setTrue("乐不思蜀生效");
    }

    @Override
    void decideFalse() {
    }

    @Override
    int getPlayer() throws DesktopErrorException {
        //获取目标
        List<CompletePlayer> target = ContextManage.roundManage().findTarget(ContextManage.executeCardDesktop().getPlayer(), ContextManage.executeCardDesktop().getCard());
        //过滤有乐不思蜀的人
        List<CompletePlayer> collect = target.stream().filter(completePlayer -> completePlayer.getDecideCard().stream().noneMatch(card -> card.getNameId() == CardEnum.LE_BU_SI_SHU.getId())).collect(Collectors.toList());
        final Integer[] targetPlayer = new Integer[1];
        ContextManage.roundManage().selectTarget(ContextManage.executeCardDesktop().getPlayer(),collect,targetPlayer);
        if (targetPlayer[0] == null) throw new DesktopErrorException("未选择目标");
        return targetPlayer[0];
    }

    @Override
    String getName() {
        return "乐不思蜀";
    }
}
