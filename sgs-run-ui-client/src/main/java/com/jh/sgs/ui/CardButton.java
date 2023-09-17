/*
 * Created by JFormDesigner on Fri Sep 15 19:22:21 CST 2023
 */

package com.jh.sgs.ui;

import com.jh.sgs.base.enums.SuitEnum;
import com.jh.sgs.base.pojo.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author jh
 */
public class CardButton extends JButton {
    private Player player;

    private Card card;
    public CardButton(Player player,Card card) {
        this.player=player;
        this.card=card;
        initComponents();
        setPreferredSize(new Dimension(80,95));
        setText(Util.toHtml(card.getNum()+"\n"+ SuitEnum.getByIndex(card.getSuit()).getName()+"\n\n"+card.getName()));
        setToolTipText(card.getRemark());
    }
    private void initComponents() {
        addActionListener(this::click);
    }
    public void click(ActionEvent e) {
        player.click(card.getId());
    }
}
