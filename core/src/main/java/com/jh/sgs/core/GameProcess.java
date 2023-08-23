package com.jh.sgs.core;

import com.jh.sgs.exception.SgsException;
import com.jh.sgs.interfaces.Interactive;
import com.jh.sgs.pojo.CompletePlayer;
import com.jh.sgs.pojo.General;
import com.jh.sgs.pojo.Identity;
import com.jh.sgs.pojo.OriginalPlayer;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Log4j2
public class GameProcess {

    @Getter
    private Desk desk;

    GameProcess(Collection<OriginalPlayer> players) {
        CompletePlayer[] array = players.stream().map(CompletePlayer::new).toArray(value -> new CompletePlayer[players.size()]);
        desk = new Desk(array);
    }

    void begin() {
        distributeIdentity();
        selectGeneral();
        setBlood();
        originalHandCard();
    }

    /**
     * 初始手牌
     */
    private void originalHandCard() {
        desk.foreach(completePlayer -> completePlayer.getHandCard().addAll(ContextManage.cardManage().obtainCard(4)));

    }

    private void selectGeneral() {
        log.info("选择武将");
        ContextManage.messageReceipt().global("选择武将");
        CompletePlayer[] chair = desk.getChair();
        for (int i = 0; i < chair.length; i++) {
            int finalI = i;
            ContextManage.interactiveMachine().addEvent(i, new Interactive() {

                int step = 0;

                @Override
                public void setGeneral(General general) {
                    desk.get(finalI).setGeneral(general);
                    step++;
                }

                @Override
                public List<General> getSelectableGeneral() {
                    return ContextManage.generalManage().getAll();
                }


                @Override
                public boolean complete() {
                    return step == 1;
                }
            });
        }
        ContextManage.interactiveMachine().lock();
    }

    /**
     * 设置初始血量
     */
    private void setBlood() {
        desk.foreach(completePlayer -> {
            if (completePlayer.getIdentity() == Identity.ZG) {
                completePlayer.setBlood(completePlayer.getGeneral().getBlood() + 1);
                desk.setIndex(completePlayer);
            } else {
                completePlayer.setBlood(completePlayer.getGeneral().getBlood());
            }
        });
    }

    /**
     * 分配身份;
     */
    private void distributeIdentity() {
        List<Identity> distribute = ContextManage.gameEngine().getIdentityManage().distribute();
        if (distribute.size() != desk.size()) throw new SgsException("身份个数与实际不符");
        Iterator<Identity> iterator = distribute.iterator();
        desk.foreach(completePlayer -> completePlayer.setIdentity(iterator.next()));
    }

    //***************************


    //***************************
}