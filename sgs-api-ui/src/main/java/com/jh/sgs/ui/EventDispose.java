package com.jh.sgs.ui;

import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.interactive.*;
import com.jh.sgs.core.InteractiveEvent;

/**
 * 〈功能概述〉<br>
 *
 * @author:hp
 * @date: 2023/9/15 16:46
 */
public class EventDispose {


    private final InteractiveEvent interactiveEvent;
    private Player player;

    public EventDispose(InteractiveEvent interactiveEvent, Player player) {
        this.interactiveEvent = interactiveEvent;
        this.player = player;
    }

    public void dispose() {
        String message = interactiveEvent.getMessage();
        println("-------------------->");
        player.lableText(message);
        while (true) {
            if (analyse(interactiveEvent.interactive())) {
                try {
                    interactiveEvent.complete();
                    break;
                } catch (SgsApiException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                try {
                    interactiveEvent.cancel();
                    break;
                } catch (SgsApiException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        println("<--------------------");
    }

    private boolean analyse(Interactive interactive) {
        InteractiveEnum type = interactive.type();
        while (true) {
            try {
                switch (type) {
                    case XZYX:
                        return xzyx(interactive);
                    case CP:
                        return cp(interactive);
                    case QP:
                        return qp(interactive);
                    case GHCQSSQYXZP:
                        return ghcqssqyxzp(interactive);
                    case XZMB:
                        return xzmb(interactive);
                    case JNCP:
                        return jncp(interactive);
                    case WGFDXZP:
                        return wgfdxzp(interactive);
                    case XZP:
                        return xzp(interactive);
//            case GSF: return gsf(interactive);
                    case JNXZP:
                        return jnxzp(interactive);
                    case TOF:
                        return tof(interactive);
//                    case GX:
//                        return gx(interactive);
                    default:
                        println("系统未实现此事件");
                        return false;
                }
            } catch (SgsApiException e) {
                println(e.getMessage());
            }
        }
    }

//    private boolean gx(GX gx) {
//        while (true) {
//            println("牌：" + gx.targetCard());
//            println("输入位置(0顶  !0底)（-1完成操作）");
//            int i = player.waitValue();
//            if (i == Player.CANCAL) return false;
//            if (i == -1) break;
//            println("按顺序输入对应牌id");
//            int[] i1 = StartGame.inputer.inputInts();
//            if (i == 0) {
//                gx.putCardsTop(i1);
//            } else {
//                gx.putCardsBottom(i1);
//            }
//        }
//        return true;
//    }


    private boolean jnxzp(JNXZP jnxzp) {
        println("手牌：" + jnxzp.handCard());
        println("装备牌：" + jnxzp.equipCard());
        println("输入对应牌id（-1取消选择）");
        int i = player.waitValue();
        if (i == -1) jnxzp.cancelPlayCard();
        else jnxzp.setCard(i);
        return true;
    }

    private boolean tof(TOF tof) {
        player.tof();
        int i =  player.waitValue();
        tof.trueOrFalse(i != 0);
        return true;
    }

    private boolean xzp(XZP xzp) {
        println("可选择牌:" + xzp.targetCard());
        println("输入对应牌id（-1取消选择）");
        int i =  player.waitValue();
        if (i == -1) {
            xzp.cancelPlayCard();
            return true;
        }
        xzp.setCard(i);
        return true;
    }

    private boolean jncp(JNCP jncp) {
        println("手牌：" + jncp.handCard());
        println("技能：" + jncp.showAbility());
        println("输入对应手牌id,1000+技能id" + "（-1取消出牌）");
        int i =  player.waitValue();
        if (i == -1) jncp.cancelPlayCard();
        else if (i >= 1000) jncp.setAbility(i - 1000);
        else jncp.playCard(i);
        return true;
    }

    private boolean xzyx(XZYX xzyx) {
        println("可选择武将：" + xzyx.selectableGeneral());
        println("输入对应武将id");
        player.input();
        int i = player.waitValue();
        xzyx.setGeneral(i);
        return true;
    }

    private boolean cp(CP cp) {
        player.handCard(cp.handCard());
        player.cancel();
        int i =  player.waitValue();
        if (i == -1) cp.cancelPlayCard();
        else cp.playCard(i);
        return true;
    }

    private boolean qp(QP qp) {
        player.handCard(qp.handCard());
        int i1 = qp.discardNum();
        int[] a = new int[i1];
        while (i1 != 0) {
            println("输入第" + (qp.discardNum() - i1 + 1) + "张对应手牌id");
            int i =  player.waitValue();
                a[qp.discardNum() - i1] = i;
                i1--;

        }
        qp.disCard(a);
        return true;
    }

    private boolean ghcqssqyxzp(GHCQSSQYXZP ghcqssqyxzp) {
        println("手牌：" + ghcqssqyxzp.handCard());
        println("装备牌：" + ghcqssqyxzp.equipCard());
        println("判定牌：" + ghcqssqyxzp.decideCard());
        println("输入对应牌id");
        int i =  player.waitValue();
        ghcqssqyxzp.setCard(i);
        return true;
    }

    private boolean xzmb(XZMB xzmb) {
        println("可选择目标：" + xzmb.targetPlayer());
        println("输入对应目标id（-1取消选择目标）");
        player.input();
        int i =  player.waitValue();
        if (i == -1) xzmb.cancelTargetPlayer();
        else xzmb.setTargetPlayer(i);
        return true;
    }

    private boolean wgfdxzp(WGFDXZP wgfdxzp) {
        println("可选择牌:" + wgfdxzp.targetCard());
        println("输入对应牌id");
        int i =  player.waitValue();
        wgfdxzp.setCard(i);
        return true;
    }


    private void println(String s) {
        player.plaText("处理：" + s + "\n");
    }
}
