package com.jh.sgs.uiServer;

import com.jh.sgs.StartGameInUIServer;
import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.interactive.*;
import com.jh.sgs.base.interfaces.InteractiveEvent;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pojo.ShowPlayCardAbility;
import com.jh.sgs.ui.TcpObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈功能概述〉<br>
 *
 * @author:hp
 * @date: 2023/9/15 16:46
 */
public class EventDispose {


    private final InteractiveEvent interactiveEvent;
    private UiServer uiServer;
    private int playerIndex;

    public EventDispose(InteractiveEvent interactiveEvent, UiServer uiServer) {
        this.interactiveEvent = interactiveEvent;
        this.uiServer = uiServer;
        playerIndex = interactiveEvent.player();
    }

    public void dispose() {
        String message = interactiveEvent.message();
        println("-------------------->");
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_M);
        tcpObject.setMessage(message);
        uiServer.request(playerIndex, tcpObject);
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
        handCard();
        cancel();
        println("装备牌：" + jnxzp.equipCard());
        input();
        println("输入对应牌id（-1取消选择）");
        int i = waitValue();
        if (i == -1) jnxzp.cancelPlayCard();
        else jnxzp.setCard(i);
        return true;
    }

    private boolean tof(TOF tof) {
        tof1();
        int i = waitValue();
        tof.trueOrFalse(i != 0);
        return true;
    }

    private boolean xzp(XZP xzp) {
        choose(xzp.targetCard());
        int i = waitValue();
        if (i == -1) {
            xzp.cancelPlayCard();
            return true;
        }
        xzp.setCard(i);
        return true;
    }

    private boolean jncp(JNCP jncp) {
        handCard();
        ability(jncp.showAbility());
        cancel();
        int i = waitValue();
        if (i == -1) jncp.cancelPlayCard();
        else if (i >= 1000) jncp.setAbility(i - 1000);
        else jncp.playCard(i);
        return true;
    }

    private boolean xzyx(XZYX xzyx) {
        println("可选择武将：" + xzyx.selectableGeneral());
        println("输入对应武将id");
        input();
        int i = waitValue();
        xzyx.setGeneral(i);
        return true;
    }

    private boolean cp(CP cp) {
        handCard();
        cancel();
        int i = waitValue();
        if (i == -1) cp.cancelPlayCard();
        else cp.playCard(i);
        return true;
    }


    private boolean qp(QP qp) {
        handCard();
        int[] a = waitValues();
        qp.disCard(a);
        return true;
    }


    private boolean ghcqssqyxzp(GHCQSSQYXZP ghcqssqyxzp) {
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(ghcqssqyxzp.handCard());
        cards.addAll(ghcqssqyxzp.equipCard());
        cards.addAll(ghcqssqyxzp.decideCard());
        choose(cards);
        println("手牌：" + ghcqssqyxzp.handCard());
        println("装备牌：" + ghcqssqyxzp.equipCard());
        println("判定牌：" + ghcqssqyxzp.decideCard());
        println("输入对应牌id");
        int i = waitValue();
        ghcqssqyxzp.setCard(i);
        return true;
    }

    private boolean xzmb(XZMB xzmb) {
        println("可选择目标：" + xzmb.targetPlayer());
        println("输入对应目标id（-1取消选择目标）");
        input();
        int i = waitValue();
        if (i == -1) xzmb.cancelTargetPlayer();
        else xzmb.setTargetPlayer(i);
        return true;
    }


    private boolean wgfdxzp(WGFDXZP wgfdxzp) {
        choose(wgfdxzp.targetCard());
        int i = waitValue();
        wgfdxzp.setCard(i);
        return true;
    }


    private void println(String s) {
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_PM);
        tcpObject.setPlayerMessage("处理：" + s + "\n");
        uiServer.request(playerIndex, tcpObject);
    }

    private int waitValue() {
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_OP);
        tcpObject.setOperate(TcpObject.O_WAIT);
        tcpObject.setShowCompletePlayer(StartGameInUIServer.run.getPlayer(playerIndex));
        TcpObject tcpObject1 = uiServer.requestWaitHandle(playerIndex, tcpObject);
        return Integer.parseInt(tcpObject1.getValue());
    }
    private int[] waitValues() {
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_OP);
        tcpObject.setOperate(TcpObject.O_WAITS);
        tcpObject.setShowCompletePlayer(StartGameInUIServer.run.getPlayer(playerIndex));
        TcpObject tcpObject1 = uiServer.requestWaitHandle(playerIndex, tcpObject);
        String value = tcpObject1.getValue();
        String[] split = value.split(",");
        int[] ints = new int[split.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i]= Integer.parseInt(split[i]);
        }
        return ints;
    }
    private void input() {
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_OP);
        tcpObject.setOperate(TcpObject.O_INOUT);
        uiServer.request(playerIndex, tcpObject);
    }

    private void handCard() {
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_OP);
        tcpObject.setOperate(TcpObject.O_HANDCARD);
        uiServer.request(playerIndex, tcpObject);
    }
    private void ability(List<ShowPlayCardAbility> abilities) {
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_OP);
        tcpObject.setOperate(TcpObject.O_ABILITY);
        tcpObject.setShowPlayCardAbility(abilities);
        uiServer.request(playerIndex, tcpObject);
    }

    private void cancel() {
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_OP);
        tcpObject.setOperate(TcpObject.O_CANCEL);
        uiServer.request(playerIndex, tcpObject);
    }
    private void tof1() {
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_OP);
        tcpObject.setOperate(TcpObject.O_TOF);
        uiServer.request(playerIndex, tcpObject);
    }
    private void choose(List<Card> cards) {
        TcpObject tcpObject = new TcpObject();
        tcpObject.setType(TcpObject.T_OP);
        tcpObject.setOperate(TcpObject.O_CHOOSE);
        tcpObject.setChoose(cards);
        uiServer.request(playerIndex, tcpObject);
    }
}
