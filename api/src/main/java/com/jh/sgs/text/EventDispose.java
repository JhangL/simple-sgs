package com.jh.sgs.text;

import com.jh.sgs.StartGame;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.*;
import com.jh.sgs.core.pojo.InteractiveEnum;

public class EventDispose {
    private final InteractiveEvent interactiveEvent;
    private int player;

    public EventDispose(InteractiveEvent interactiveEvent) {
        this.interactiveEvent = interactiveEvent;
    }

    public static void disposal(InteractiveEvent interactiveEvent) {
        new EventDispose(interactiveEvent).dispose();
    }

    public void dispose() {
        String message = interactiveEvent.getMessage();
        player = interactiveEvent.getPlayer();
        println(message);
        if (analyse(interactiveEvent.interactive())) {
            interactiveEvent.complete();
        } else {
            interactiveEvent.cancel();
        }
    }

    private boolean analyse(Interactive interactive) {
        InteractiveEnum type = interactive.type();
        switch (type) {
//            case XZYX: return xzyx(interactive);
            case CP:
                return cp(interactive);
//            case QP: return qp(interactive);
            case GHCQSSQYXZP: return ghcqssqyxzp(interactive);
            case XZMB: return xzmb(interactive);
//            case WGFDXZP: return wgfdxzp(interactive);
            default:
                System.out.println("系统未实现此事件");
                return false;
        }
    }

    private boolean xzyx(XZYX xzyx) {
        println("可选择武将：" + xzyx.selectableGeneral());
        println("输入对应武将id");
        int i = StartGame.inputer.inputInt();
        if (i == Inputer.CANCAL) return false;
        xzyx.setGeneral(i);
        return true;
    }

    private boolean cp(CP cp) {
        println("手牌：" + cp.handCard());
        println("输入对应手牌id（-1取消出牌）");
        while (true) {
            int i = StartGame.inputer.inputInt();
            if (i == Inputer.CANCAL) return false;
            if (i == -1) cp.cancelPlayCard();
            else {
                try {
                    cp.playCard(i);
                } catch (SgsApiException e) {
                    System.err.println(e.getMessage());
                    continue;
                }
            }
            break;
        }
        return true;
    }

    private boolean qp(QP qp) {
        println("手牌：" + qp.handCard());
        int i1 = qp.discardNum();
        int[] a = new int[i1];
        while (i1 != 0) {
            println("输入第" + (qp.discardNum() - i1 + 1) + "张对应手牌id");
            int i = StartGame.inputer.inputInt();
            if (i == Inputer.CANCAL) return false;
            else {
                a[qp.discardNum() - i1] = i;
                i1--;
            }
        }
        qp.disCard(a);
        return true;
    }

    private boolean ghcqssqyxzp(GHCQSSQYXZP ghcqssqyxzp) {
        println("手牌：" + ghcqssqyxzp.handCard());
        println("装备牌：" + ghcqssqyxzp.equipCard());
        println("判定牌：" + ghcqssqyxzp.decideCard());
        println("输入对应牌id");
        int i = StartGame.inputer.inputInt();
        if (i == Inputer.CANCAL) return false;
        ghcqssqyxzp.setCard(i);
        return true;
    }

    private boolean xzmb(XZMB xzmb) {
        println("可选择目标：" + xzmb.targetPlayer());
        println("输入对应目标id（-1取消选择目标）");
        int i = StartGame.inputer.inputInt();
        if (i == Inputer.CANCAL) return false;
        if (i == -1) xzmb.cancelTargetPlayer();
        else xzmb.setTargetPlayer(i);
        return true;
    }

    private boolean wgfdxzp(WGFDXZP wgfdxzp){
        println("可选择牌:"+wgfdxzp.targetCard());
        println("输入对应牌id");
        int i = StartGame.inputer.inputInt();
        if (i == Inputer.CANCAL) return false;
        wgfdxzp.setCard(i);
        return true;
    }


    private void println(String message) {
        System.out.println("player" + player + ":" + message);
    }
}
