package com.jh.sgs.core;

import com.jh.sgs.base.enums.IdentityEnum;
import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.interactive.Interactiveable;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pojo.ShowPlayCardAbility;
import com.jh.sgs.base.pool.TPool;
import com.jh.sgs.core.card.BaseCard;
import com.jh.sgs.core.card.Loseable;
import com.jh.sgs.core.desktop.Desktop;
import com.jh.sgs.core.desktop.ExecuteCardDesktop;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.exception.DesktopRefuseException;
import com.jh.sgs.core.exception.PlayerDieException;
import com.jh.sgs.core.exception.RoundErrorException;
import com.jh.sgs.core.general.BaseGeneral;
import com.jh.sgs.core.interfaces.RoundEvent;
import com.jh.sgs.core.pojo.Ability;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.MessageReceipter;
import com.jh.sgs.core.roundevent.*;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Log4j2
public class RoundManage {
    private Desk desk;
    @Getter
    private Desktop.Stack desktopStack;

    private RoundProcess[] roundProcesses;

    private RoundRegistrarPool roundRegistrarPool;
    private int index;

    RoundManage(Desk desk) {
        this.desk = desk;
        desktopStack = new Desktop.Stack();
        roundProcesses = new RoundProcess[desk.size()];
        roundRegistrarPool = new RoundRegistrarPool();
    }

    public void init() {
        Class[] classes = {AbilityEvent.class, BeSubBloodEvent.class, CardTargetHideEvent.class, DecideInvadeEvent.class, DefenseEvent.class, LossCardEvent.class, OffenseEvent.class};
        for (Class aClass : classes) {
            roundRegistrarPool.addRegistrar(aClass);
        }
        desk.foreach((integer, completePlayer) -> {
            BaseGeneral baseGeneral = completePlayer.getCompleteGeneral().getBaseGeneral();
            roundProcesses[integer] = baseGeneral;
            if (baseGeneral instanceof RoundEvent) addEvent(integer, (RoundEvent) baseGeneral);
            for (Object o : baseGeneral.getSkill()) {
                if (o instanceof RoundEvent) addEvent(integer, (RoundEvent) o);
            }
        });

    }

    public void begin() {

        index = desk.index();
        try {
            while (true) {
                try {
                    roundProcesses[index].process();
                } catch (RoundErrorException e) {
                    e.printStackTrace();
                }
                index = desk.nextOnDesk();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("游戏结束");
        }
    }


    public void addEvent(int player, RoundEvent roundEvent) {
        for (RoundRegistrar<RoundEvent> roundRegistrar : roundRegistrarPool.getRegistrar(roundEvent)) {
            if (roundEvent.eventLocation() == RoundEvent.PLAYER) roundRegistrar.addPlayerEvent(player, roundEvent);
            else roundRegistrar.addGlobalEvent(roundEvent);
        }


    }

    public void subEvent(int player, RoundEvent roundEvent) {
        for (RoundRegistrar<RoundEvent> roundRegistrar : roundRegistrarPool.getRegistrar(roundEvent)) {
            roundRegistrar.subPlayerEvent(player, roundEvent);
        }

    }

    public RoundProcess getRoundProcess(int player) {
        return roundProcesses[player];
    }


    public void playCard(int player, String message, TPool<Card> cards, Consumer<Card> action, boolean singleAbility) {
        CompletePlayer player1 = Util.getPlayer(player);
        ArrayList<Ability> abilities = new ArrayList<>();
        //添加技能注册
        //牌技能注册（可替换牌的技能）
        roundRegistrarPool.getRegistrar(AbilityEvent.class).handlePlayer(player, playAbilityEvent -> {
            if (playAbilityEvent.addAbilityOption() == null) return;
            for (Ability ability : playAbilityEvent.addAbilityOption()) {
                if (ability.getType() == Ability.PLAY_CARD) abilities.add(ability);
            }
        });
        //独立技能
        if (singleAbility) {
            roundRegistrarPool.getRegistrar(AbilityEvent.class).handlePlayer(player, playAbilityEvent -> {
                if (playAbilityEvent.addAbilityOption() == null) return;
                for (Ability ability : playAbilityEvent.addAbilityOption()) {
                    if (ability.getType() == Ability.SINGLE) abilities.add(ability);
                }
            });
        }
        while (cards.getPool() == null) {
            final Ability[] abilty = new Ability[1];
            Interactiveable interactiveable = new Interactiveable() {

                boolean cancel;
                boolean play;
                boolean ability;

                @Override
                public void cancelPlayCard() {
                    log.debug("取消出牌");
                    cards.setPool(null);
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
                    return abilities.stream().map((Ability ability1) -> {
                        ShowPlayCardAbility showPlayCardAbility = new ShowPlayCardAbility();
                        showPlayCardAbility.setId(ability1.getId());
                        showPlayCardAbility.setName(ability1.getName());
                        return showPlayCardAbility;
                    }).collect(Collectors.toList());
                }

                @Override
                public void playCard(int id) {
                    List<Card> handCard = player1.getHandCard();
                    Card card = Util.collectionCollectAndCheckId(handCard, id);
                    //检查
                    action.accept(card);
                    handCard.remove(card);
                    log.debug(player + "出牌:" + card);
                    play = true;
                    cards.setPool(card);
                }

                @Override
                public void cancel() {
                    cancelPlayCard();
                }

                @Override
                public CompleteEnum complete() {
//                    log.debug("完成出牌阶段");
                    return cancel || play || ability ? CompleteEnum.COMPLETE : CompleteEnum.NOEXECUTE;
                }
            };
            InteractiveMachine.addEventInContext(player, message, interactiveable).lock();

            //处理技能
            if (abilty[0] != null) {

                if (abilty[0].getType() == Ability.PLAY_CARD) {
                    MessageReceipter.personalInContext(player, "你使用牌技能{}", abilty[0].getName());
                    //使用了牌技能
                    cards.setPool(((Ability.PlayCardAbilityable) abilty[0].getAbilityable()).playCardAbility(abilty[0], action));
                    //若牌技能未返回牌，循环重新询问

                } else if (abilty[0].getType() == Ability.SINGLE) {
                    //使用了独立技能
                    MessageReceipter.personalInContext(player, "你使用独立技能{}", abilty[0].getName());

                    ((Ability.SingleAbilityable) abilty[0].getAbilityable()).singleAbility(abilty[0]);

                    //重新询问出牌
                }
            } else {
                //没使用牌技能
                if (cards.getPool() == null) {
                    //没用技能没出牌，，退出循环
                    break;
                }

            }
            if (cards.getPool() != null) MessageReceipter.personalInContext(player, "你出牌{}", cards.getPool());
        }

    }

    /**
     * 执行杀，仅仅只是反映执行的结果，不执行结果的处理（处理闪牌，杀成功掉血操作），需调用方处理
     *
     * @param operatePlayer 操作人
     * @param beShaPlayer   被杀玩家
     * @param shaCard       杀牌
     * @param shan          闪牌（被杀玩家所出的牌）
     * @return T杀成功（未被闪避）  F杀失败（被闪避，出闪闪避及其他闪避）
     */
    public boolean playSha(int operatePlayer, int beShaPlayer, Card shaCard, TPool<Card> shan) {
        //todo 杀前逻辑


        //执行闪询问
        playCard(beShaPlayer, "请出闪", shan, card -> {
            if (card.getNameId() != CardEnum.SHAN.getId()) throw new SgsApiException("指定牌不为闪");
        }, false);
        return shan.getPool() == null;
    }

    /**
     * 全局无懈可击检查，当锦囊牌对人生效前，执行此方法。通过此方法来阻断锦囊牌的生效。
     *
     * @throws DesktopRefuseException 无懈可击生效时，抛出此异常。
     */
    public void wxkjCheck() throws DesktopRefuseException {
        ArrayList<CompletePlayer> completePlayers = new ArrayList<>();
        //场上是否有无懈可击
        ContextManage.desk().foreachOnDesk(completePlayer -> {
            List<Card> handCard = completePlayer.getHandCard();
            if (handCard.stream().anyMatch(card -> card.getNameId() == CardEnum.WU_XIE_KE_JI.getId()))
                completePlayers.add(completePlayer);

        });
        if (!completePlayers.isEmpty()) MessageReceipter.globalInContext("等待使用无懈可击");
        //无懈可击出牌
        TPool<Card> playWhile = new TPool<>();
        for (CompletePlayer completePlayer : completePlayers) {
            playCard(completePlayer.getId(), "是否使用无懈可击", playWhile, card -> {
                if (card.getNameId() != CardEnum.WU_XIE_KE_JI.getId()) throw new SgsApiException("指定牌不为无懈可击");
                ExecuteCardDesktop.initCheck(card);
            }, false);
            //新增desktop（无懈可击使用desktop传递异常）
            if (playWhile.getPool() != null) {
                if (!completePlayers.isEmpty()) MessageReceipter.globalInContext("使用无懈可击");
                desktopStack.create(new ExecuteCardDesktop(completePlayer.getId(), playWhile.getPool()));
                desktopStack.remove();
                break;
            }
        }
        if (playWhile.getPool() == null)
            if (!completePlayers.isEmpty()) MessageReceipter.globalInContext("不使用无懈可击");
    }


    /**
     * 判定事件，获取判定牌
     *
     * @param operatePlayer 操作人
     * @return 判定牌
     */
    public Card decide(int operatePlayer) {
        //判定入侵（修改判定牌）
        List<Card> cards = ContextManage.cardManage().obtainCard(1);
        TPool<Card> cardTPool = new TPool<>();
        boolean b = roundRegistrarPool.getRegistrar(DecideInvadeEvent.class).handlePlayer(operatePlayer).anyMatch(decideInvadeEvent -> {
            Card card = decideInvadeEvent.decideInvade();
            if (card != null) {
                cardTPool.setPool(card);
                return true;
            }
            return false;
        });
        if (b) {
            ContextManage.cardManage().recoveryCard(cards);
            return cardTPool.getPool();
        }
        return cards.get(0);
    }

    /**
     * 查找目标事件，获取特定距离内的目标。在查找时，获取到的目标是处理监听事件（进攻事件，防御事件）后的目标。
     *
     * @param player       要查找玩家
     * @param card         触发牌
     * @param findDistance 查找距离
     * @return 目标玩家
     */
    public List<CompletePlayer> findTarget(int player, Card card, int findDistance) {
        //card（触发牌） 用为特定技能对特定牌的特殊处理
        ArrayList<CompletePlayer> completePlayers = new ArrayList<>();
        final int[] offenseDistance = {findDistance};
        //计算进攻事件距离;
        roundRegistrarPool.getRegistrar(OffenseEvent.class).handlePlayer(player, offenseEvent -> offenseDistance[0] += offenseEvent.offense());
        desk.foreachHaveDistanceOnDeskNoPlayer(player, (distance, player1) -> {
            final int[] defenseDistance = {0};
            //计算防御事件距离
            roundRegistrarPool.getRegistrar(DefenseEvent.class).handlePlayer(player1.getId(), defenseEvent -> defenseDistance[0] += defenseEvent.defense());
            distance += defenseDistance[0];
            //比较距离；
            if (distance <= offenseDistance[0]) completePlayers.add(player1);
        });
        if (card == null) return completePlayers;
        // 查找目标时的特殊事件处理(针对牌隐藏)
        List<CompletePlayer> collect = completePlayers.stream().filter(completePlayer -> !roundRegistrarPool.getRegistrar(CardTargetHideEvent.class).handlePlayer(completePlayer.getId()).anyMatch(cardTargetHideEvent -> cardTargetHideEvent.hide(card))).collect(Collectors.toList());
        return collect;
    }

    /**
     * 查找目标事件，获取除自身以外的目标。
     *
     * @param player 要查找玩家
     * @param card   触发牌
     * @return 目标玩家
     */
    public List<CompletePlayer> findTarget(int player, Card card) {
        ArrayList<CompletePlayer> completePlayers = new ArrayList<>();
        desk.foreachOnDeskNoPlayer(player, completePlayers::add);
        if (card == null) return completePlayers;
        List<CompletePlayer> collect = completePlayers.stream().filter(completePlayer -> !roundRegistrarPool.getRegistrar(CardTargetHideEvent.class).handlePlayer(completePlayer.getId()).anyMatch(cardTargetHideEvent -> cardTargetHideEvent.hide(card))).collect(Collectors.toList());
        return collect;
    }

    /**
     *
     */
    public static final int HAND_CARD = 1;
    public static final int EQUIP_CARD = 2;
    public static final int DECIDE_CARD = 3;

    /**
     * 牌失去事件，当牌失去时，请求此事件。（此方法无实际效果，仅调用使用）
     *
     * @param operatePlayer  操作玩家
     * @param lossCardPlayer 失牌玩家
     * @param lossCard       失去牌
     * @param lossLocation   失去位置
     */
    public void loseCard(int operatePlayer, int lossCardPlayer, Card lossCard, int lossLocation) {

        switch (lossLocation) {
            case HAND_CARD:
                MessageReceipter.personalInContext(lossCardPlayer, "你失去手牌{}", lossCard);
                break;
            case EQUIP_CARD:
                MessageReceipter.personalInContext(lossCardPlayer, "你失去装备牌{}", lossCard);
                //去掉装备牌的特殊效果
                BaseCard baseCard = CardEnum.getById(lossCard.getNameId()).getBaseCard();
                if (baseCard instanceof Loseable) ((Loseable) baseCard).lose(lossCardPlayer);
                break;
            case DECIDE_CARD:
                break;
        }
        //通知注册失牌事件玩家
        roundRegistrarPool.getRegistrar(LossCardEvent.class).handlePlayer(lossCardPlayer, lossCardEvent -> lossCardEvent.loseCard(operatePlayer, lossLocation));
    }

    /**
     * 加血事件，当加血时，请求此事件。（此方法无实际效果，仅调用使用）
     *
     * @param operatePlayer  操作玩家
     * @param addBloodPlayer 加血玩家
     * @param addBloodCard   加血牌
     */
    public void addBlood(int operatePlayer, int addBloodPlayer, Card addBloodCard) {

    }

    /**
     * 减血事件，当减血时，请求此事件。（此方法无实际效果，仅调用使用）
     *
     * @param operatePlayer  操作玩家
     * @param subBloodPlayer 减血玩家
     * @param subBloodCard   减血牌(可使用，可能为空)
     * @param subBloodNum    减血数
     */
    public void subBlood(int operatePlayer, int subBloodPlayer, TPool<Card> subBloodCard, int subBloodNum){
        MessageReceipter.personalInContext(subBloodPlayer,"受到来自{}的{}点伤害",operatePlayer,subBloodNum);
        if (Util.getPlayer(subBloodPlayer).getBlood() <= 0) {
            dying(subBloodPlayer,operatePlayer);
        }
        //通知受伤注册器
        roundRegistrarPool.getRegistrar(BeSubBloodEvent.class).handlePlayer(subBloodPlayer, beSubBloodEvent -> beSubBloodEvent.beSubBlood(operatePlayer, subBloodCard));
    }

    /**
     * 状态刷新事件，当状态刷新时，请求此事件。只关注非关键状态，主要根据状态更新关联值（此方法无实际效果，仅调用使用）
     *
     * @param operatePlayer 操作玩家
     * @param refreshPlayer 刷新玩家
     */
    public void statusRefresh(int operatePlayer, int refreshPlayer) {
        Util.getPlayer(refreshPlayer).getCompleteGeneral().getBaseGeneral().statusRefresh();
    }

    public void dying(int player,int operatePlayer) {
        CompletePlayer player1 = Util.getPlayer(player);
        TPool<Card> cardTPool = new TPool<>();
        playCard(player, "请出桃", cardTPool, card -> {
            if (card.getNameId() != CardEnum.TAO.getId()) throw new SgsApiException("不为桃");
        }, false);
        if (!cardTPool.isEmpty()) player1.setBlood(player1.getBlood() + 1);
        if (player1.getBlood() <= 0) {
            for (CompletePlayer completePlayer : findTarget(player, null)) {
                cardTPool.setPool(null);
                playCard(completePlayer.getId(), "请为" + player + "出桃", cardTPool, card -> {
                    if (card.getNameId() != CardEnum.TAO.getId()) throw new SgsApiException("不为桃");
                }, false);
                if (!cardTPool.isEmpty()) player1.setBlood(player1.getBlood() + 1);
                if (player1.getBlood() > 0) break;
            }
        }
        if (player1.getBlood() <= 0) {
          rewardAndSettlement(player,operatePlayer);
        }
    }

    private void rewardAndSettlement(int diePlayer,int operatePlayer){
        CompletePlayer player1 = Util.getPlayer(diePlayer);
        MessageReceipter.globalInContext("{}死亡,是{}",diePlayer,player1.getIdentity());
        boolean[] onDesk = desk.getOnDesk();
        if (player1.getIdentity()== IdentityEnum.ZG){
            //主公死了
            ArrayList<Integer> integers = new ArrayList<>();
            for (int i = 0; i < onDesk.length; i++) {
                if (diePlayer==i)continue;
                if (onDesk[i])integers.add(i);
            }
            if (integers.size()==1&&Util.getPlayer(integers.get(0)).getIdentity()==IdentityEnum.NJ){
                //内奸获胜
                MessageReceipter.globalInContext("内奸获胜");
                ContextManage.gameEngine().shutDown();
            }else {
                //反贼获胜
                MessageReceipter.globalInContext("反贼获胜");
                ContextManage.gameEngine().shutDown();
            }
        }int i = 0;
        for (; i < onDesk.length; i++) {
            if (diePlayer==i)continue;
            if (onDesk[i]){
                CompletePlayer completePlayer = Util.getPlayer(i);
                if (completePlayer.getIdentity()==IdentityEnum.FZ||completePlayer.getIdentity()==IdentityEnum.NJ){
                    break;
                }
            }
        }
        if (i== onDesk.length){
            //主公和忠臣获胜
            MessageReceipter.globalInContext("主公和忠臣获胜");
            ContextManage.gameEngine().shutDown();
        }
        desk.getoffDesk(diePlayer);
        //回收牌
        List<Card> handCard = player1.getHandCard();
        List<Card> decideCard = player1.getDecideCard();
        Card[] equipCard = player1.getEquipCard();
        List<Card> collect = Arrays.stream(equipCard).filter(Objects::nonNull).collect(Collectors.toList());
        ContextManage.cardManage().recoveryCard(handCard);
        ContextManage.cardManage().recoveryCard(decideCard);
        ContextManage.cardManage().recoveryCard(collect);

        //死的反贼
        if (player1.getIdentity()==IdentityEnum.FZ){
            CompletePlayer completePlayer = Util.getPlayer(operatePlayer);
            List<Card> cards = ContextManage.cardManage().obtainCard(3);
            completePlayer.getHandCard().addAll(cards);
            statusRefresh(operatePlayer,operatePlayer);
        }else {
            if (player1.getIdentity()==IdentityEnum.ZC){
                CompletePlayer completePlayer = Util.getPlayer(operatePlayer);
                if (completePlayer.getIdentity()==IdentityEnum.ZG){
                    List<Card> handCard1 = completePlayer.getHandCard();
                    Card[] equipCard1 = completePlayer.getEquipCard();
                    List<Card> collect1 = Arrays.stream(equipCard1).filter(Objects::nonNull).collect(Collectors.toList());
                    ContextManage.cardManage().recoveryCard(handCard1);
                    ContextManage.cardManage().recoveryCard(collect1);
                }
            }
        }
        if (index==diePlayer){
            throw new RoundErrorException("已阵亡");
        }else {
            throw new PlayerDieException("目标已阵亡");
        }

    }

}
