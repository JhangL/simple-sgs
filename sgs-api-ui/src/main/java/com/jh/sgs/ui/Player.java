/*
 * Created by JFormDesigner on Fri Sep 15 14:45:14 GMT+08:00 2023
 */

package com.jh.sgs.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author hp
 */
public class Player extends JPanel {
    public boolean submitWait=false;
    
    public Player() {
        initComponents();
        button21.setPreferredSize(new Dimension(100,85));
        handcards.revalidate();
    }

    private void submit(ActionEvent e) {
        if (submitWait){
            submitWait=false;
        }
    }
    public void gloText(String s){
        glo.append(s);
        if (glo.getText().length()>20){
        }
    }


    private void gloInputMethodTextChanged(InputMethodEvent e) {
        System.out.println();
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - unknown
        name = new JLabel();
        equip1 = new JButton();
        scrollPane1 = new JScrollPane();
        handcards = new JPanel();
        button21 = new JButton();
        scrollPane2 = new JScrollPane();
        glo = new JTextArea();
        scrollPane3 = new JScrollPane();
        pla = new JTextArea();
        label2 = new JLabel();
        label3 = new JLabel();
        panel2 = new JPanel();
        tofT = new JButton();
        tofF = new JButton();
        equip2 = new JButton();
        equip3 = new JButton();
        equip4 = new JButton();
        skill2 = new JButton();
        skill1 = new JButton();
        input = new JTextField();
        submit = new JButton();
        label1 = new JLabel();

        //======== this ========
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing.
        border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e" , javax. swing .border . TitledBorder. CENTER
        ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "D\u0069al\u006fg", java .awt . Font
        . BOLD ,12 ) ,java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener(
        new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062or\u0064er"
        .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
        setLayout(null);

        //---- name ----
        name.setText("text");
        name.setVerticalAlignment(SwingConstants.TOP);
        name.setHorizontalAlignment(SwingConstants.CENTER);
        add(name);
        name.setBounds(540, 320, 104, 140);

        //---- equip1 ----
        equip1.setText("text");
        add(equip1);
        equip1.setBounds(0, 355, 85, 105);

        //======== scrollPane1 ========
        {
            scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

            //======== handcards ========
            {
                handcards.setMaximumSize(null);
                handcards.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                handcards.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

                //---- button21 ----
                button21.setText("text");
                handcards.add(button21);
            }
            scrollPane1.setViewportView(handcards);
        }
        add(scrollPane1);
        scrollPane1.setBounds(0, 460, 645, 115);

        //======== scrollPane2 ========
        {

            //---- glo ----
            glo.setEditable(false);
            glo.addInputMethodListener(new InputMethodListener() {
                @Override
                public void caretPositionChanged(InputMethodEvent e) {}
                @Override
                public void inputMethodTextChanged(InputMethodEvent e) {
                    gloInputMethodTextChanged(e);
                }
            });
            scrollPane2.setViewportView(glo);
        }
        add(scrollPane2);
        scrollPane2.setBounds(0, 25, 320, 295);

        //======== scrollPane3 ========
        {

            //---- pla ----
            pla.setLineWrap(true);
            pla.setEditable(false);
            scrollPane3.setViewportView(pla);
        }
        add(scrollPane3);
        scrollPane3.setBounds(325, 25, 320, 295);

        //---- label2 ----
        label2.setText("\u5168\u5c40\uff1a");
        add(label2);
        label2.setBounds(0, 0, 100, 25);

        //---- label3 ----
        label3.setText("\u73a9\u5bb6\uff1a");
        add(label3);
        label3.setBounds(325, 0, 100, 25);

        //======== panel2 ========
        {
            panel2.setLayout(null);

            //---- tofT ----
            tofT.setText("yes");
            panel2.add(tofT);
            tofT.setBounds(5, 0, 80, 45);

            //---- tofF ----
            tofF.setText("no");
            panel2.add(tofF);
            tofF.setBounds(5, 55, 80, 45);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel2.getComponentCount(); i++) {
                    Rectangle bounds = panel2.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel2.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel2.setMinimumSize(preferredSize);
                panel2.setPreferredSize(preferredSize);
            }
        }
        add(panel2);
        panel2.setBounds(340, 355, 90, 105);

        //---- equip2 ----
        equip2.setText("text");
        add(equip2);
        equip2.setBounds(85, 355, 85, 105);

        //---- equip3 ----
        equip3.setText("text");
        add(equip3);
        equip3.setBounds(170, 355, 85, 105);

        //---- equip4 ----
        equip4.setText("text");
        add(equip4);
        equip4.setBounds(255, 355, 85, 105);

        //---- skill2 ----
        skill2.setText("text");
        add(skill2);
        skill2.setBounds(445, 415, 90, 45);

        //---- skill1 ----
        skill1.setText("text");
        add(skill1);
        skill1.setBounds(445, 355, 90, 43);
        add(input);
        input.setBounds(130, 320, 330, 35);

        //---- submit ----
        submit.setText("\u63d0\u4ea4");
        submit.addActionListener(e -> submit(e));
        add(submit);
        submit.setBounds(470, 320, 65, 35);

        //---- label1 ----
        label1.setText("-100\u53d6\u6d88");
        add(label1);
        label1.setBounds(10, 320, 120, 35);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - unknown
    public JLabel name;
    public JButton equip1;
    public JScrollPane scrollPane1;
    public JPanel handcards;
    private JButton button21;
    public JScrollPane scrollPane2;
    public JTextArea glo;
    public JScrollPane scrollPane3;
    public JTextArea pla;
    public JLabel label2;
    public JLabel label3;
    public JPanel panel2;
    public JButton tofT;
    public JButton tofF;
    public JButton equip2;
    public JButton equip3;
    public JButton equip4;
    public JButton skill2;
    public JButton skill1;
    public JTextField input;
    public JButton submit;
    public JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
