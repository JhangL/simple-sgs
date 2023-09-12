package com.jh.sgs.core.interactive.impl;

import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.enums.InteractiveEnum;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.ShowPlayer;
import com.jh.sgs.core.pool.TPool;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class XZMBImpl implements Interactiveable {

    private TPool<Integer> target;
    private List<CompletePlayer> targets;


    public XZMBImpl(TPool<Integer> target, List<CompletePlayer> targets) {
        this.target = target;
        this.targets = targets;
    }

    private boolean a = false;
    private boolean b = false;


    @Override
    public InteractiveEnum type() {
        return InteractiveEnum.XZMB;
    }

    @Override
    public List<ShowPlayer> targetPlayer() {
        return targets.stream().map(ShowPlayer::new).collect(Collectors.toList());
    }

    @Override
    public void setTargetPlayer(int id) throws SgsApiException {
        ShowPlayer showPlayer = Util.collectionCollectAndCheckId(targetPlayer(), id);
        log.debug("选择目标：" + showPlayer);
        target.setPool(showPlayer.getId());
        a = true;
    }

    @Override
    public void cancelTargetPlayer() {
        target.setPool(null);
        log.debug("取消选择目标");
        b = true;
    }

    @Override
    public void cancel() {
        cancelTargetPlayer();
    }

    @Override
    public InteractiveEvent.CompleteEnum complete() {
//                log.debug("完成目标选择");
        return a || b ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
    }
}
