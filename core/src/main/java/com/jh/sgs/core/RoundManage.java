package com.jh.sgs.core;

import com.jh.sgs.core.exception.DesktopRefuseException;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.interfaces.DefenseEvent;
import com.jh.sgs.core.interfaces.OffenseEvent;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CardEnum;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.InteractiveEnum;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log4j2
public class RoundManage {


    private Desk desk;
    @Getter
    private Desktop.Stack desktopStack;

    private RoundProcess[] roundProcesses;

    private RoundRegistrar<OffenseEvent> offenseRegistrar = new RoundRegistrar<>();
    private RoundRegistrar<DefenseEvent> defenseRegistrar = new RoundRegistrar<>();


    RoundManage(Desk desk) {
        this.desk = desk;
        desktopStack = new Desktop.Stack();
        roundProcesses = new RoundProcess[desk.size()];
    }

    public void init() {
        desk.foreach((integer, completePlayer) -> roundProcesses[integer] = completePlayer.getCompleteGeneral().getBaseGeneral());
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
        boolean[] playWhile = new boolean[1];
        for (CompletePlayer completePlayer : completePlayers) {
            ContextManage.interactiveMachine().addEvent(completePlayer.getId(), "是否使用无懈可击", new Interactiveable() {

                boolean a, b;

                @Override
                public List<Card> handCard() {
                    return Util.collectionCloneToList(completePlayer.getHandCard());
                }

                @Override
                public void cancelPlayCard() {
                    log.debug("取消出牌");
                    playWhile[0] = false;
                    b = true;
                }

                @Override
                public void playCard(int id) {
                    Set<Card> handCard = completePlayer.getHandCard();
                    Card card = Util.collectionCollectAndCheckId(handCard, id);
                    if (card.getNameId() != CardEnum.WU_XIE_KE_JI.getId())
                        throw new SgsApiException("指定牌不为无懈可击");
                    ContextManage.desktopStack().create(completePlayer.getId(), card);
                    handCard.remove(card);
                    log.debug(completePlayer.getId() + "出牌:" + card);
                    a = true;
                    playWhile[0] = true;
                }

                @Override
                public void cancel() {
                    cancelPlayCard();
                }

                @Override
                public InteractiveEvent.CompleteEnum complete() {
                    return a || b ? InteractiveEvent.CompleteEnum.COMPLETE : InteractiveEvent.CompleteEnum.NOEXECUTE;
                }

                @Override
                public InteractiveEnum type() {
                    return InteractiveEnum.CP;
                }
            });
            ContextManage.interactiveMachine().lock();
            //新增desktop（无懈可击使用desktop传递异常）
            if (playWhile[0]) {
                if (!completePlayers.isEmpty()) ContextManage.messageReceipt().global("使用无懈可击");
                desktopStack.remove();
                break;
            }
        }
        if (!playWhile[0]) if (!completePlayers.isEmpty()) ContextManage.messageReceipt().global("不使用无懈可击");
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
