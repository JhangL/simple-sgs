package com.jh.sgs.text;

import com.jh.sgs.StartGame;
import com.jh.sgs.base.enums.IdentityEnum;
import com.jh.sgs.base.enums.InteractiveEnum;
import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.interactive.*;
import com.jh.sgs.base.interfaces.InteractiveEvent;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pojo.ShowCompletePlayer;

import java.util.Arrays;
import java.util.List;

public class EventDispose {
    private final InteractiveEvent interactiveEventer;
    private int player;

    public EventDispose(InteractiveEvent interactiveEventer) {
        this.interactiveEventer = interactiveEventer;
    }

    public static void disposal(InteractiveEvent interactiveEventer) {
        new EventDispose(interactiveEventer).dispose();
    }

    public void dispose() {
        String message = interactiveEventer.message();
        player = interactiveEventer.player();
        {
            ShowCompletePlayer player1 = StartGame.messageRequest.getPlayer(player);
            IdentityEnum identity = player1.getIdentity();
            int blood = player1.getBlood();
            int maxBlood = player1.getMaxBlood();
            Card[] equipCard = player1.getEquipCard();
            List<Card> handCard = player1.getHandCard();
            List<Card> decideCard = player1.getDecideCard();
            println(33, 2, player1.getName() + "  " + player1.getCountry());
            println(33, 2, "体力：" + blood + "/" + maxBlood + "    身份：" + identity);
            println(33, 2, "手牌：" + handCard.toString());
            println(33, 2, "装备：" + Arrays.toString(equipCard) + "  判定：" + decideCard.toString());
        }
        println(31, 0, "-------------------->");
        println(34, 1, message);
        while (true) {
            if (analyse(interactiveEventer.interactive())) {
                try {
                    interactiveEventer.complete();
                    break;
                } catch (SgsApiException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                try {
                    interactiveEventer.cancel();
                    break;
                } catch (SgsApiException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        println(31, 0, "<--------------------");
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
                    case GX:
                        return gx(interactive);
                    default:
                        System.out.println("系统未实现此事件");
                        return false;
                }
            } catch (SgsApiException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private boolean gx(GX gx) {
        while (true) {
            println("牌：" + gx.targetCard());
            println("输入位置(0顶  !0底)（-1完成操作）");
            int i = StartGame.inputer.inputInt();
            if (i == Inputer.CANCAL) return false;
            if (i == -1) break;
            println("按顺序输入对应牌id");
            int[] i1 = StartGame.inputer.inputInts();
            if (i == 0) {
                gx.putCardsTop(i1);
            } else {
                gx.putCardsBottom(i1);
            }
        }
        return true;
    }

    private boolean jnxzp(JNXZP jnxzp) {
        println("手牌：" + jnxzp.handCard());
        println("装备牌：" + jnxzp.equipCard());
        println("输入对应牌id（-1取消选择）");
        int i = StartGame.inputer.inputInt();
        if (i == Inputer.CANCAL) return false;
        if (i == -1) jnxzp.cancelPlayCard();
        else jnxzp.setCard(i);
        return true;
    }

    private boolean tof(TOF tof) {
        println("输入是或否（0否  !0是）");
        int i = StartGame.inputer.inputInt();
        if (i == Inputer.CANCAL) return false;
        tof.trueOrFalse(i != 0);
        return true;
    }

    private boolean xzp(XZP xzp) {
        println("可选择牌:" + xzp.targetCard());
        println("输入对应牌id（-1取消选择）");
        int i = StartGame.inputer.inputInt();
        if (i == Inputer.CANCAL) return false;
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
        int i = StartGame.inputer.inputInt();
        if (i == Inputer.CANCAL) return false;
        if (i == -1) jncp.cancelPlayCard();
        else if (i >= 1000) jncp.setAbility(i - 1000);
        else jncp.playCard(i);
        return true;
    }

    private boolean xzyx(XZYX xzyx) {
        println("可选择武将：" + xzyx.selectableGeneral());
        println("输入对应武将id");
        int i = StartGame.inputer.inputInt();
        if (i == Inputer.CANCAL) return false;
        else xzyx.setGeneral(i);
        return true;
    }

    private boolean cp(CP cp) {
        println("手牌：" + cp.handCard());
        println("输入对应手牌id（-1取消出牌）");
        int i = StartGame.inputer.inputInt();
        if (i == Inputer.CANCAL) return false;
        if (i == -1) cp.cancelPlayCard();
        else cp.playCard(i);
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

    private boolean wgfdxzp(WGFDXZP wgfdxzp) {
        println("可选择牌:" + wgfdxzp.targetCard());
        println("输入对应牌id");
        int i = StartGame.inputer.inputInt();
        if (i == Inputer.CANCAL) return false;
        wgfdxzp.setCard(i);
        return true;
    }


    private void println(String message) {
        System.out.println("player" + player + ":  " + message);
    }

    private void println(int fColor, int fontType, String message) {
        Util.printlnColor(fColor, fontType, "player" + player + ":  " + message);
    }
}
