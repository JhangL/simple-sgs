package com.jh.sgs.core;

import com.jh.sgs.core.exception.SgsException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.interfaces.ShowStatus;
import com.jh.sgs.core.pojo.*;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Log4j2
public class GameProcess implements ShowStatus {

    @Getter
    private Desk desk;

    GameProcess(Collection<OriginalPlayer> players) {
        log.debug("初始化desk");
        CompletePlayer[] array = players.stream().map(CompletePlayer::new).toArray(value -> new CompletePlayer[players.size()]);
        desk = new Desk(array);
        log.debug("desk初始化完成");
    }

    void begin() {
        log.info("分配身份");
        distributeIdentity();
        log.info("选择武将");
        selectGeneral();
        log.info("设置体力");
        setBlood();
        log.info("获取初始手牌");
        originalHandCard();
    }

    /**
     * 初始手牌
     */
    private void originalHandCard() {
        ContextManage.messageReceipt().global("获取初始手牌");
        desk.foreach(completePlayer -> completePlayer.getHandCard().addAll(ContextManage.cardManage().obtainCard(4)));

    }

    private void selectGeneral() {
        ContextManage.messageReceipt().global("选择武将");
        desk.foreach((integer, completePlayer) -> ContextManage.interactiveMachine().addEvent(integer,"请选择武将", new Interactiveable() {

            int step = 0;

            @Override
            public InteractiveEnum type() {
                return InteractiveEnum.XZYX;
            }

            @Override
            public void setGeneral(int id) {
                General general = Util.collectionCollectAndCheckId(selectableGeneral(), id);
                completePlayer.setCompleteGeneral(new CompleteGeneral());
                completePlayer.getCompleteGeneral().setGeneral(general);
                log.debug(integer+"选择武将"+general);
                step++;
            }

            @Override
            public List<General> selectableGeneral() {
                return ContextManage.generalManage().getAll();
            }

            @Override
            public void cancel() {
                setGeneral(selectableGeneral().get(0).getId());
            }

            @Override
            public InteractiveEvent.CompleteEnum  complete() {
//                log.debug(integer+"完成武将选择");
                return step == 1? InteractiveEvent.CompleteEnum.COMPLETE: InteractiveEvent.CompleteEnum.NOEXECUTE;
            }
        }));
        ContextManage.interactiveMachine().lock();
        desk.foreach(completePlayer -> ContextManage.generalManage().setGeneral(completePlayer));
    }

    /**
     * 设置初始血量
     */
    private void setBlood() {
        ContextManage.messageReceipt().global("设置体力");
        desk.foreach(completePlayer -> {
            if (completePlayer.getIdentity() == IdentityEnum.ZG) {
                completePlayer.setBlood(completePlayer.getCompleteGeneral().getGeneral().getBlood() + 1);
                desk.setIndex(completePlayer);
            } else {
                completePlayer.setBlood(completePlayer.getCompleteGeneral().getGeneral().getBlood());
            }
            completePlayer.setMaxBlood(completePlayer.getBlood());
        });
    }

    /**
     * 分配身份;
     */
    private void distributeIdentity() {
        ContextManage.messageReceipt().global("分配身份");
        List<IdentityEnum> distribute = ContextManage.gameEngine().getIdentityManage().distribute();
        if (distribute.size() != desk.size()) throw new SgsException("身份个数与实际不符");
        Iterator<IdentityEnum> iterator = distribute.iterator();
        desk.foreach(completePlayer -> completePlayer.setIdentity(iterator.next()));
    }

    @Override
    public String getStatus() {
        return "{" +
                "\"desk\":" + desk.getStatus() +
                '}';
    }


    //***************************


    //***************************
}