package com.jh.sgs.core;

import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.card.Loseable;
import com.jh.sgs.core.desktop.Desktop;
import com.jh.sgs.core.desktop.ExecuteCardDesktop;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.enums.InteractiveEnum;
import com.jh.sgs.core.exception.DesktopRefuseException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.general.BaseGeneral;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.pojo.*;
import com.jh.sgs.core.roundevent.DefenseEvent;
import com.jh.sgs.core.roundevent.OffenseEvent;
import com.jh.sgs.core.roundevent.PlayCardAbilityEvent;
import com.jh.sgs.core.roundevent.RoundEvent;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Log4j2
public class RoundManage {


    private Desk desk;
    @Getter
    private Desktop.Stack desktopStack;

    private RoundProcess[] roundProcesses;

    private RoundRegistrar<OffenseEvent> offenseRegistrar = new RoundRegistrar<>();
    private RoundRegistrar<DefenseEvent> defenseRegistrar = new RoundRegistrar<>();
    private RoundRegistrar<PlayCardAbilityEvent> playAbilityRegistrar = new RoundRegistrar<>();


    RoundManage(Desk desk) {
        this.desk = desk;
        desktopStack = new Desktop.Stack();
        roundProcesses = new RoundProcess[desk.size()];
    }

    public void init() {
        desk.foreach((integer, completePlayer) -> {
            BaseGeneral baseGeneral = completePlayer.getCompleteGeneral().getBaseGeneral();
            roundProcesses[integer] = baseGeneral;
            if (baseGeneral instanceof RoundEvent) addEvent(integer, (RoundEvent) baseGeneral);
        });

    }


    public void addEvent(int player, RoundEvent roundEvent) {
        //注册全局事件
        if (roundEvent instanceof PlayCardAbilityEvent)
            playAbilityRegistrar.addPlayerEvent(player, (PlayCardAbilityEvent) roundEvent);
        if (roundEvent instanceof OffenseEvent)
            offenseRegistrar.addPlayerEvent(player, (OffenseEvent) roundEvent);
        if (roundEvent instanceof DefenseEvent)
            defenseRegistrar.addPlayerEvent(player, (DefenseEvent) roundEvent);
    }

    public void subEvent(int player, RoundEvent roundEvent) {
        //注册全局事件
        if (roundEvent instanceof PlayCardAbilityEvent)
            playAbilityRegistrar.subPlayerEvent(player, (PlayCardAbilityEvent) roundEvent);
        if (roundEvent instanceof OffenseEvent)
            offenseRegistrar.subPlayerEvent(player, (OffenseEvent) roundEvent);
        if (roundEvent instanceof DefenseEvent)
            defenseRegistrar.subPlayerEvent(player, (DefenseEvent) roundEvent);
    }

    public RoundProcess getRoundProcess(int player) {
        return roundProcesses[player];
    }

    public void begin() {
        int index = desk.index();
        try {
            while (true) {
                try {
                    roundProcesses[index].process();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                index = desk.nextOnDesk();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("游戏结束");
        }
    }


    public void playCard(int player, String message, Card[] cards, Consumer<Card> action, boolean singleAbility) {
        CompletePlayer player1 = Util.getPlayer(player);
        //todo 只实现了代替打出牌的技能使用，未实现独立技能。
        ArrayList<PlayCardAbility> abilities = new ArrayList<>();
        //添加技能注册
        playAbilityRegistrar.handlePlayer(player, playAbilityEvent -> {
             abilities.addAll(playAbilityEvent.addAbilityOption());
        });
        while (cards[0] == null) {
            final PlayCardAbility[] abilty = new PlayCardAbility[1];
            Interactiveable interactiveable = new Interactiveable() {

                boolean cancel;
                boolean play;
                boolean ability;

                @Override
                public void cancelPlayCard() {
                    log.debug("取消出牌");
                    cards[0] = null;
                    cancel = true;
                }

                @Override
                public InteractiveEnum type() {
                    if (abilities.isEmpty()) return InteractiveEnum.CP;
                    else return InteractiveEnum.JNCP;
                }

                @Override
                public List<Card> handCard() {
                    return Util.collectionCloneToList(player1.getHandCard());
                }


                @Override
                public void setAbility(int id) {
                    if (abilities.isEmpty()) Interactiveable.super.setAbility(id);
                    abilty[0] = Util.collectionCollectAndCheckId(abilities, id);
                    ability = true;
                }


                @Override
                public List<ShowPlayCardAbility> showAbility() {
                    if (abilities.isEmpty()) Interactiveable.super.showAbility();
                    return abilities.stream().map(ShowPlayCardAbility::new).collect(Collectors.toList());
                }

                @Override
                public void playCard(int id) {
                    Set<Card> handCard = player1.getHandCard();
                    Card card = Util.collectionCollectAndCheckId(handCard, id);
                    //检查
                    action.accept(card);
                    handCard.remove(card);
                    log.debug(player + "出牌:" + card);
                    play = true;
                    cards[0] = card;
                }

                @Override
                public void cancel() {
                    cancelPlayCard();
                }

                @Override
                public InteractiveEvent.CompleteEnum complete() {
//                    log.debug("完成出牌阶段");
                    return cancel || play || ability ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
                }
            };
            ContextManage.interactiveMachine().addEvent(player, message, interactiveable).lock();
            //处理技能
            if (abilty[0] != null) {
                //使用了牌技能
                cards[0] = abilty[0].getAbilityable().playCardAbility(abilty[0],action);
                //若牌技能未返回牌，循环重新询问
            } else {
                //没使用牌技能
                if (cards[0] == null) {
                    //没用技能没出牌，，退出循环
                    break;
                }
            }
        }

    }

    /**
     *  执行杀，仅仅只是反映执行的结果，不执行结果的处理（处理闪牌，杀成功掉血操作），需调用方处理
     * @param operatePlayer 操作人
     * @param beShaPlayer 被杀玩家
     * @param shaCard 杀牌
     * @param shan 闪牌（被杀玩家所出的牌）
     * @return T杀成功（未被闪避）  F杀失败（被闪避，出闪闪避及其他闪避）
     */
    public boolean playSha(int operatePlayer, int beShaPlayer, Card shaCard, Card[] shan) {
        //todo 杀前逻辑


        //执行闪询问
        playCard(beShaPlayer, "", shan, card -> {
            if (card.getNameId() != CardEnum.SHAN.getId())
                throw new SgsApiException("指定牌不为闪");
        }, false);
        return shan[0] == null;
    }

    /**
     * 全局无懈可击检查，当锦囊牌对人生效前，执行此方法。通过此方法来阻断锦囊牌的生效。
     * @throws DesktopRefuseException 无懈可击生效时，抛出此异常。
     */
    public void wxkjCheck() throws DesktopRefuseException {
        ArrayList<CompletePlayer> completePlayers = new ArrayList<>();
        //场上是否有无懈可击
        ContextManage.desk().foreachOnDesk(completePlayer -> {
            Set<Card> handCard = completePlayer.getHandCard();
            if (handCard.stream().anyMatch(card -> card.getNameId() == CardEnum.WU_XIE_KE_JI.getId()))
                completePlayers.add(completePlayer);

        });
        if (!completePlayers.isEmpty()) ContextManage.messageReceipt().global("等待使用无懈可击");
        //无懈可击出牌
        Card[] playWhile = new Card[1];
        for (CompletePlayer completePlayer : completePlayers) {
            playCard(completePlayer.getId(), "是否使用无懈可击", playWhile, card -> {
                if (card.getNameId() != CardEnum.WU_XIE_KE_JI.getId())
                    throw new SgsApiException("指定牌不为无懈可击");
                ExecuteCardDesktop.initCheck(card);
            }, false);
            //新增desktop（无懈可击使用desktop传递异常）
            if (playWhile[0] != null) {
                if (!completePlayers.isEmpty()) ContextManage.messageReceipt().global("使用无懈可击");
                desktopStack.create(new ExecuteCardDesktop(completePlayer.getId(), playWhile[0]));
                desktopStack.remove();
                break;
            }
        }
        if (playWhile[0] == null)
            if (!completePlayers.isEmpty()) ContextManage.messageReceipt().global("不使用无懈可击");
    }


    /**
     * 选择目标，当需要选择目标时，调用此方法。
     * @param player 选择玩家
     * @param targets 目标池
     * @param target 被选目标
     */
    public void selectTarget(int player, List<CompletePlayer> targets, Integer[] target) {
        ContextManage.interactiveMachine().addEvent(player, "请选择目标", new Interactiveable() {

            boolean a = false;
            boolean b = false;

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
                target[0] = showPlayer.getId();
                a = true;
            }

            @Override
            public void cancelTargetPlayer() {
                target[0] = null;
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
        });
        ContextManage.interactiveMachine().lock();
    }

    /**
     * 查找目标事件，获取特定距离内的目标。在查找时，获取到的目标是处理监听事件（进攻事件，防御事件）后的目标。
     * @param player 要查找玩家
     * @param card 触发牌
     * @param findDistance 查找距离
     * @return 目标玩家
     */
    public List<CompletePlayer> findTarget(int player, Card card, int findDistance) {
        //card（触发牌） 用为特定技能对特定牌的特殊处理

        ArrayList<CompletePlayer> completePlayers = new ArrayList<>();
        final int[] offenseDistance = {findDistance};
        //计算进攻事件距离;
        offenseRegistrar.handlePlayer(player, offenseEvent -> offenseDistance[0] += offenseEvent.offense());
        desk.foreachHaveDistanceOnDeskNoPlayer(player, (distance, player1) -> {
            final int[] defenseDistance = {0};
            //计算防御事件距离
            defenseRegistrar.handlePlayer(player1.getId(), defenseEvent -> defenseDistance[0] += defenseEvent.defense());
            distance += defenseDistance[0];
            //比较距离；
            if (distance <= offenseDistance[0]) completePlayers.add(player1);
        });

        //todo 查找目标时的特殊事件处理（例如：空城）
        return completePlayers;
    }

    /**
     * 查找目标事件，获取除自身以外的目标。
     * @param player 要查找玩家
     * @param card 触发牌
     * @return 目标玩家
     */
    public List<CompletePlayer> findTarget(int player, Card card) {
        ArrayList<CompletePlayer> completePlayers = new ArrayList<>();
        desk.foreachOnDeskNoPlayer(player, completePlayers::add);
        //todo 查找目标时的特殊事件处理（例如：空城）
        return completePlayers;
    }

    /**
     *
     */
    public static final int HAND_CARD = 1;
    public static final int EQUIP_CARD = 2;
    public static final int DECIDE_CARD = 3;

    /**
     * 牌失去事件，当牌失去时，请求此事件。（此方法无实际效果，仅调用使用）
     * @param operatePlayer 操作玩家
     * @param lossCardPlayer 失牌玩家
     * @param lossCard 失去牌
     * @param lossLocation 失去位置
     */
    public void loseCard(int operatePlayer, int lossCardPlayer, Card lossCard, int lossLocation) {
        switch (lossLocation) {
            case HAND_CARD:
                break;
            case EQUIP_CARD:
                //去掉装备牌的特殊效果
                BaseCard baseCard = CardEnum.getById(lossCard.getNameId()).getBaseCard();
                if (baseCard instanceof Loseable) ((Loseable) baseCard).lose(lossCardPlayer);
                break;
            case DECIDE_CARD:
                break;
        }
    }

    /**
     * 加血事件，当加血时，请求此事件。（此方法无实际效果，仅调用使用）
     * @param operatePlayer 操作玩家
     * @param addBloodPlayer 加血玩家
     * @param addBloodCard 加血牌
     */
    public void addBlood(int operatePlayer, int addBloodPlayer, Card addBloodCard) {

    }

    /**
     * 减血事件，当减血时，请求此事件。（此方法无实际效果，仅调用使用）
     * @param operatePlayer 操作玩家
     * @param subBloodPlayer 减血玩家
     * @param subBloodCard 减血牌
     * @param subBloodNum 减血数
     */
    public void subBlood(int operatePlayer, int subBloodPlayer, Card subBloodCard, int subBloodNum) {

    }

}
