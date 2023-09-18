/*
 * Created by JFormDesigner on Sun Sep 17 20:33:51 CST 2023
 */

package com.jh.sgs.ui;

import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pojo.General;
import com.jh.sgs.base.pojo.ShowPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * @author jh
 */
public class Choose extends JDialog {
    Player player;

    public Choose(Window owner, Player player, List<Card> cards, List<General> generals, List<ShowPlayer> showPlayers) {
        super(owner);
        setTitle("选择牌");
        this.player = player;
        initComponents();
        if (cards != null) {
            for (Card card : cards) {
                CardButton cardButton = new CardButton(player, card);
                cardButton.setEnabled(true);
                panel1.add(cardButton);
            }
        } else if (generals!=null){
            for (General general : generals) {
                CardButton cardButton = new CardButton(player, general);
                cardButton.setEnabled(true);
                panel1.add(cardButton);
            }
        }else {
            for (ShowPlayer showPlayer : showPlayers) {
                CardButton cardButton = new CardButton(player, showPlayer);
                cardButton.setEnabled(true);
                panel1.add(cardButton);
            }
        }

        panel1.revalidate();
    }

    private void ok(ActionEvent e) {
        if ("".equals(player.input.getText())) return;
        player.submit(e);
        this.dispose();
    }

    private void cancel(ActionEvent e) {
        player.submit2(e);
        this.dispose();
    }

    private void thisWindowClosing(WindowEvent e) {
        cancel(null);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        panel1 = new JPanel();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BorderLayout());

                //======== scrollPane1 ========
                {
                    scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                    scrollPane1.setMinimumSize(new Dimension(500, 114));
                    scrollPane1.setPreferredSize(new Dimension(500, 114));
                    scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

                    //======== panel1 ========
                    {
                        panel1.setMinimumSize(new Dimension(500, 110));
                        panel1.setPreferredSize(null);
                        panel1.setMaximumSize(null);
                        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    }
                    scrollPane1.setViewportView(panel1);
                }
                contentPanel.add(scrollPane1, BorderLayout.CENTER);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(e -> ok(e));
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.addActionListener(e -> cancel(e));
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    public JPanel panel1;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
