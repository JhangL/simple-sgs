/*
 * Created by JFormDesigner on Fri Sep 15 19:22:21 CST 2023
 */

package com.jh.sgs.ui;

import com.jh.sgs.base.enums.SuitEnum;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pojo.General;
import com.jh.sgs.base.pojo.ShowPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author jh
 */
public class CardButton extends JButton {
    private  ShowPlayer showPlayer;
    private Player player;

    private Card card;
    private General general;

    public CardButton(Player player, Card card) {
        this.player = player;
        this.card = card;
        initComponents();
        setPreferredSize(new Dimension(80, 95));
        String s;
        if (card.getSuit() == -1) {
            s = "隐藏";
        } else {
            s = card.getNum() + "\n" + SuitEnum.getByIndex(card.getSuit()).getName() + "\n\n" + card.getName();
        }
        setText(Util.toHtml(s));
        setToolTipText(card.getRemark());
    }

    public CardButton(Player player, General general) {
        this.player = player;
        this.general = general;
        initComponents();
        setPreferredSize(new Dimension(80, 95));
        setText(Util.toHtml(general.getName()));
    }

    public CardButton(Player player, ShowPlayer showPlayer) {
        this.player = player;
        this.showPlayer = showPlayer;
        initComponents();
        setPreferredSize(new Dimension(80, 95));
        setText(Util.toHtml(showPlayer.getName()));
    }

    private void initComponents() {
        addActionListener(this::click);
    }

    public void click(ActionEvent e) {
        if (card != null) {
            player.click(card.getId());
        } else if (general != null) {
            player.click(general.getId());
        } else if (showPlayer != null) {
            player.click(showPlayer.getId());
        }
    }
}
