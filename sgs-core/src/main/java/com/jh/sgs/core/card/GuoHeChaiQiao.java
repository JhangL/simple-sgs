package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.RoundManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.InteractiveEnum;
import com.jh.sgs.core.pojo.ShowPlayer;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class GuoHeChaiQiao extends OneSilkbagCard {

    @Override
    int getPlayer() throws DesktopErrorException {
        //获取目标
        List<CompletePlayer> target = ContextManage.roundManage().findTarget(ContextManage.desktop().getPlayer(), ContextManage.desktop().getCard());
        //过滤没手牌的人
        List<CompletePlayer> collect = target.stream().filter(completePlayer -> {
            if (!completePlayer.getHandCard().isEmpty()) return true;
            if (!completePlayer.getDecideCard().isEmpty()) return true;
            if (Arrays.stream(completePlayer.getEquipCard()).anyMatch(Objects::nonNull)) return true;
            return false;
        }).collect(Collectors.toList());
        final Integer[] targetPlayer = new Integer[1];
        ContextManage.interactiveMachine().addEvent(ContextManage.desktop().getPlayer(), "请选择目标", new Interactiveable() {

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
                log.debug("完成目标选择");
                return a || b ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
            }
        });
        ContextManage.interactiveMachine().lock();
        if (targetPlayer[0] == null) throw new DesktopErrorException("未选择目标");
        return targetPlayer[0];
    }

    @Override
    void effect(int player) {
        int mainPlayer = ContextManage.desktop().getPlayer();
        log.debug("过河拆桥：执行玩家：{}，被执行玩家：{}", mainPlayer, player);
        CompletePlayer player1 = Util.getPlayer(player);
        final Card[] card = new Card[1];//选择卡牌
        final int[] lossLocation = new int[1];//选择位置
        ContextManage.interactiveMachine().addEvent(mainPlayer, "请选择卡牌", new Interactiveable() {

            boolean a;

            @Override
            public InteractiveEnum type() {
                return InteractiveEnum.GHCQSSQYXZP;
            }

            @Override
            public List<Card> handCard() {
                List<Card> cards = Util.collectionCloneToList(player1.getHandCard(),true);
                for (Card card : cards) {
                    card.setNum(null);
                    card.setSuit(-1);
                    card.setNameId(-1);
                    card.setName(null);
                    card.setRemark(null);
                }
                return cards;
            }

            @Override
            public List<Card> equipCard() {
                return Util.arrayCloneToList(player1.getEquipCard());
            }

            @Override
            public List<Card> decideCard() {
                return Util.collectionCloneToList(player1.getDecideCard());
            }

            @Override
            public void setCard(int id) {

                try {
                    card[0] = Util.collectionCollectAndCheckId(player1.getHandCard(), id);
                    player1.getHandCard().remove(card[0]);
                    lossLocation[0] = RoundManage.HAND_CARD;
                } catch (Exception e) {
                    try {
                        card[0] = Util.collectionCollectAndCheckId(player1.getDecideCard(), id);
                        player1.getDecideCard().remove(card[0]);
                        lossLocation[0] = RoundManage.DECIDE_CARD;
                    } catch (Exception e1) {
                        card[0] = Util.ArrayCollectAndCheckId(player1.getEquipCard(), id);
                        Util.ArrayRemove(player1.getEquipCard(), card[0]);
                        lossLocation[0] = RoundManage.EQUIP_CARD;
                    }
                }

                a = true;
            }

            @Override
            public void cancel() {
                if (!handCard().isEmpty()) {
                    log.debug("选择第一个手牌");
                    setCard(handCard().get(0).getId());
                    return;
                }
                if (!equipCard().isEmpty()) {
                    log.debug("选择第一个装备牌");
                    setCard(equipCard().get(0).getId());
                    return;
                }
                log.debug("选择第一个判定牌");
                setCard(decideCard().get(0).getId());

            }

            @Override
            public InteractiveEvent.CompleteEnum complete() {
                log.debug("完成卡牌选择");
                return a ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
            }
        });
        ContextManage.interactiveMachine().lock();
        //执行失牌，获得牌操作
        if (lossLocation[0] == RoundManage.HAND_CARD) player1.getHandCard().remove(card[0]);
        else if (lossLocation[0] == RoundManage.EQUIP_CARD) Util.ArrayRemove(player1.getEquipCard(), card[0]);
        else if (lossLocation[0] == RoundManage.DECIDE_CARD) player1.getDecideCard().remove(card[0]);
        else throw new RuntimeException("系统异常");
        ContextManage.roundManage().loseCard(mainPlayer, player, card[0], lossLocation[0]);
        ContextManage.cardManage().recoveryCard(card[0]);
        log.debug("过河拆桥完成：执行玩家：{}，被执行玩家：{}，卡牌{}", mainPlayer, player, card[0]);
    }

    @Override
    String getName() {
        return "过河拆桥";
    }
}
