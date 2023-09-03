package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.pojo.*;
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
        ContextManage.roundProcess().getPlayCardBool().setTrue("乐不思蜀生效");
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
        ContextManage.interactiveMachine().addEvent(ContextManage.executeCardDesktop().getPlayer(), "请选择目标", new Interactiveable() {

            boolean a = false;
            boolean b = false;

            @Override
            public InteractiveEnum type() {
                return InteractiveEnum.XZMB;
            }

            @Override
            public List<ShowPlayer> targetPlayer() {
                return collect.stream().map(ShowPlayer::new).collect(Collectors.toList());
            }

            @Override
            public void setTargetPlayer(int id) throws SgsApiException {
                ShowPlayer showPlayer = Util.collectionCollectAndCheckId(targetPlayer(), id);
                log.debug("选择目标：" + showPlayer);
                targetPlayer[0] = showPlayer.getId();
                a = true;
            }

            @Override
            public void cancelTargetPlayer() {
                targetPlayer[0] = null;
                log.debug("取消选择目标");
                b = true;
            }

            @Override
            public void cancel() {
                cancelTargetPlayer();
            }

            @Override
            public InteractiveEvent.CompleteEnum complete() {
                return a || b ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
            }
        });
        ContextManage.interactiveMachine().lock();
        if (targetPlayer[0] == null) throw new DesktopErrorException("未选择目标");
        return targetPlayer[0];
    }

    @Override
    String getName() {
        return "乐不思蜀";
    }
}
