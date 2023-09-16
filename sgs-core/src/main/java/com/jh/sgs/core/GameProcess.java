package com.jh.sgs.core;

import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.interactive.Interactiveable;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pojo.General;
import com.jh.sgs.core.enums.IdentityEnum;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.interfaces.MessageReceipt;
import com.jh.sgs.core.interfaces.ShowStatus;
import com.jh.sgs.core.pojo.CompleteGeneral;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.OriginalPlayer;
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
        MessageReceipt.globalInContext("获取初始手牌");
        desk.foreach((integer, completePlayer) -> {
            List<Card> cards = ContextManage.cardManage().obtainCard(4);
            completePlayer.getHandCard().addAll(cards);
            MessageReceipt.personalInContext(integer, "你的初始手牌：" + cards);
        });
        MessageReceipt.globalInContext("获取初始手牌完成");
    }

    private void selectGeneral() {
        MessageReceipt.globalInContext("选择武将");
        List<General> all = ContextManage.generalManage().getAll();
        desk.foreach((integer, completePlayer) -> InteractiveMachine.addEventInContext(integer, "请选择武将", new Interactiveable() {

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
                log.debug(integer + "选择武将" + general);
                step++;
            }

            @Override
            public List<General> selectableGeneral() {
                return all;
            }

            @Override
            public void cancel() {
                setGeneral(selectableGeneral().get(0).getId());
            }

            @Override
            public CompleteEnum complete() {
//                log.debug(integer+"完成武将选择");
                return step == 1 ? CompleteEnum.COMPLETE : CompleteEnum.NOEXECUTE;
            }
        }));
        ContextManage.interactiveMachine().lock();
        desk.foreach((integer, completePlayer) -> {
            ContextManage.generalManage().setGeneral(completePlayer);
            MessageReceipt.personalInContext(integer, "你选择的武将：" + completePlayer.getCompleteGeneral().getGeneral());
        });
        MessageReceipt.globalInContext("选择武将完成");
    }

    /**
     * 设置初始血量
     */
    private void setBlood() {
        MessageReceipt.globalInContext("设置体力");
        desk.foreach((integer, completePlayer) -> {
            if (completePlayer.getIdentity() == IdentityEnum.ZG) {
                completePlayer.setBlood(completePlayer.getCompleteGeneral().getGeneral().getBlood() + 1);
                desk.setIndex(completePlayer);
            } else {
                completePlayer.setBlood(completePlayer.getCompleteGeneral().getGeneral().getBlood());
            }
            completePlayer.setMaxBlood(completePlayer.getBlood());
            MessageReceipt.personalInContext(integer, "你的体力为：" + completePlayer.getBlood());
        });
        MessageReceipt.globalInContext("设置体力完成");
    }

    /**
     * 分配身份;
     */
    private void distributeIdentity() {
        MessageReceipt.globalInContext("分配身份");
        List<IdentityEnum> distribute = ContextManage.gameEngine().getIdentityManage().distribute();
        if (distribute.size() != desk.size()) throw new SgsRuntimeException("身份个数与实际不符");
        Iterator<IdentityEnum> iterator = distribute.iterator();
        desk.foreach((integer, completePlayer) -> {
            IdentityEnum next = iterator.next();
            completePlayer.setIdentity(next);
            MessageReceipt.personalInContext(integer, "你的身份为：" + next);
        });
        MessageReceipt.globalInContext("分配身份完成");
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