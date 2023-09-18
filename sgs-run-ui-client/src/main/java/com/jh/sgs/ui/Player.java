/*
 * Created by JFormDesigner on Fri Sep 15 14:45:14 GMT+08:00 2023
 */

package com.jh.sgs.ui;

import com.jh.sgs.base.enums.IdentityEnum;
import com.jh.sgs.base.enums.SuitEnum;
import com.jh.sgs.base.pojo.*;
import com.jh.sgs.base.pool.TPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hp
 */
public class Player extends JPanel {

    public static final int CANCAL = -100;
    public boolean submitWait = false;
    public boolean waitints = false;
    public TPool<String> pool = new TPool<>();

    public boolean handBool = false;
    public boolean equipBool = false;
    public List<ShowPlayCardAbility> abilityPool;
    private int playerId;
    private Main main;

    public Player(int i, Main main) {
        playerId = i;
        this.main = main;
        initComponents();
        closeInPut();
    }

    public void flushData(ShowCompletePlayer showCompletePlayer) {
        ShowCompletePlayer player1 = showCompletePlayer;
        List<Card> handCard = player1.getHandCard();
        handcards.removeAll();
        handcards.updateUI();
        for (Card card : handCard) {
            CardButton cardButton = new CardButton(this, card);
            cardButton.setEnabled(handBool);
            handcards.add(cardButton);
        }
        handcards.revalidate();
        Card[] equipCard = player1.getEquipCard();
        JButton[] a = {equip1, equip2, equip3, equip4};
        for (int i = 0; i < equipCard.length; i++) {
            Card card = equipCard[i];
            JButton jButton = a[i];
            if (card == null) {
                jButton.setText("空");
                jButton.setToolTipText(null);
                jButton.setName("");
                jButton.setEnabled(false);
            } else {
                jButton.setEnabled(equipBool);
                jButton.setText(Util.toHtml(card.getNum() + "\n" + SuitEnum.getByIndex(card.getSuit()).getName() + "\n\n" + card.getName()));
                jButton.setToolTipText(card.getRemark());
                jButton.setName(String.valueOf(2000 + card.getNameId()+card.getId()*10000));
                if (abilityPool != null&&!equipBool) {
                    for (ShowPlayCardAbility showPlayCardAbility : abilityPool) {
                        if (showPlayCardAbility.getId() == card.getNameId()) {
                            jButton.setEnabled(true);
                            break;
                        }
                    }
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(player1.getName()).append(" ").append(player1.getCountry()).append("\n");
        Skill[] skills = player1.getSkills();
        if (skills != null) {
            JButton[] v = {skill1, skill2};
            for (int i = 0; i < skills.length; i++) {
                Skill skill = skills[i];
                JButton jButton = v[i];
                jButton.setText(Util.toHtml( skill.getName()));
                jButton.setEnabled(false);
                jButton.setName(String.valueOf(1000 + skill.getId()));
                jButton.setToolTipText(skill.getRemake());
                if (abilityPool != null) {
                    for (ShowPlayCardAbility showPlayCardAbility : abilityPool) {
                        if (showPlayCardAbility.getId() == skill.getId()) {
                            jButton.setEnabled(true);
                            break;
                        }
                    }
                }
            }
            if (skills.length==1)skill2.setVisible(false);
        }
        IdentityEnum identity = player1.getIdentity();
        if (identity != null) stringBuilder.append(identity.name()).append("\n");
        stringBuilder.append(player1.getBlood()).append("/").append(player1.getMaxBlood());
        name.setText(Util.toHtml(stringBuilder.toString()));
        List<Card> decideCard = player1.getDecideCard();
        panel1.removeAll();
        panel1.updateUI();
        for (Card card : decideCard) {
            JLabel jLabel = new JLabel(card.getName());
            jLabel.setToolTipText(card.getRemark());
            panel1.add(jLabel);
        }
        panel1.revalidate();
    }

    private void closeInPut() {
        synchronized (this) {
            tofT.setEnabled(false);
            tofF.setEnabled(false);
            input.setEditable(false);
            submit2.setEnabled(false);
            label1.setText("");
            handBool = false;
            equipBool = false;
            abilityPool = null;
        }
    }

    public void tof() {
        synchronized (this) {
            tofT.setEnabled(true);
            tofF.setEnabled(true);
        }
    }

    public void input() {
        synchronized (this) {
            input.setEditable(true);
        }
    }

    public void handCard() {
        synchronized (this) {
            handBool = true;
        }
    }
    public void equipCard() {
        synchronized (this) {
            equipBool = true;
        }
    }

    public void ability(List<ShowPlayCardAbility> showPlayCardAbility) {
        synchronized (this) {
            abilityPool = showPlayCardAbility;
        }
    }

    public void cancel() {
        synchronized (this) {
            submit2.setEnabled(true);
        }
    }

    public String waitValue() {
        submitWait = true;
        while (submitWait) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return value();
    }

    public String waitValues() {
        submitWait = true;
        waitints = true;
        while (submitWait) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return value();
    }

    private void tip(String a) {
        new Thread(() -> {
            String text = label1.getText();
            Color foreground = label1.getForeground();
            input.setText(a);
            input.setForeground(Color.RED);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            input.setText(text);
            input.setForeground(foreground);
        }).start();
    }

    public void submit(ActionEvent e) {
        if (submitWait) {
            if ("".equals(input.getText())) return;
            try {
                pool.setPool(input.getText());
            } catch (Exception e1) {
                tip("请输入数字");
                input.setText("");
                return;
            }
            input.setText("");
            closeInPut();
            submitWait = false;
            waitints = false;
        }
    }

    public String value() {
        String pool1 = pool.getPool();
        pool.setPool(null);
        return pool1;
    }

    public void click(int v) {
        if (!waitints) input.setText(v + "");
        else {
            String text = input.getText();
            if ("".equals(text)) {
                input.setText(v + "");
                return;
            }
            String[] split = text.split(",");
            ArrayList<String> strings = new ArrayList<>();
            boolean a = true;
            for (String s : split) {
                if (v == Integer.parseInt(s)) {
                    a = false;
                    continue;
                }
                strings.add(s);
            }
            if (a) strings.add(String.valueOf(v));
            input.setText(String.join(",", strings));
        }
    }

    public void lableText(String s) {
        label1.setText(s);
    }

    public void plaText(String s) {
        pla.append(s);
        if (pla.getText().length() > 500) {
            pla.replaceRange("", 0, pla.getText().length() - 500);
        }
        pla.selectAll();
    }

    public void gloText(String s) {
        glo.append(s);
        if (glo.getText().length() > 500) {
            glo.replaceRange("", 0, glo.getText().length() - 500);
        }
        glo.selectAll();
    }


    private void tofT(ActionEvent e) {
        input.setText(1 + "");
        submit(e);
    }

    private void tofF(ActionEvent e) {
        input.setText(0 + "");
        submit(e);
    }

    public void submit2(ActionEvent e) {
        input.setText(-1 + "");
        submit(e);
    }

    private void skill1(ActionEvent e) {
        String name1 = ((JButton) e.getSource()).getName();
        if (equipBool){
            input.setText(name1.substring(0,name1.length()-4));
        }else {
            input.setText(name1.substring(name1.length()-4));
        }
    }

    public void choose(List<Card> cards, List<General> generals, List<ShowPlayer>  showPlayers) {
        Choose choose = new Choose(main, this, cards,generals,showPlayers);
        choose.setVisible(true);
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        name = new JLabel();
        equip1 = new JButton();
        scrollPane1 = new JScrollPane();
        handcards = new JPanel();
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
        submit2 = new JButton();
        panel1 = new JPanel();
        label4 = new JLabel();

        //======== this ========
        setLayout(null);

        //---- name ----
        name.setHorizontalAlignment(SwingConstants.CENTER);
        add(name);
        name.setBounds(540, 320, 104, 140);

        //---- equip1 ----
        equip1.addActionListener(e -> skill1(e));
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
                handcards.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
            }
            scrollPane1.setViewportView(handcards);
        }
        add(scrollPane1);
        scrollPane1.setBounds(0, 460, 645, 115);

        //======== scrollPane2 ========
        {

            //---- glo ----
            glo.setEditable(false);
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
            tofT.setText("\u662f");
            tofT.addActionListener(e -> tofT(e));
            panel2.add(tofT);
            tofT.setBounds(5, 0, 80, 45);

            //---- tofF ----
            tofF.setText("\u5426");
            tofF.addActionListener(e -> tofF(e));
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
        equip2.addActionListener(e -> skill1(e));
        add(equip2);
        equip2.setBounds(85, 355, 85, 105);

        //---- equip3 ----
        equip3.addActionListener(e -> skill1(e));
        add(equip3);
        equip3.setBounds(170, 355, 85, 105);

        //---- equip4 ----
        equip4.addActionListener(e -> skill1(e));
        add(equip4);
        equip4.setBounds(255, 355, 85, 105);

        //---- skill2 ----
        skill2.addActionListener(e -> skill1(e));
        add(skill2);
        skill2.setBounds(445, 415, 90, 45);

        //---- skill1 ----
        skill1.addActionListener(e -> skill1(e));
        add(skill1);
        skill1.setBounds(445, 355, 90, 43);
        add(input);
        input.setBounds(295, 320, 110, 35);

        //---- submit ----
        submit.setText("\u63d0\u4ea4");
        submit.addActionListener(e -> submit(e));
        add(submit);
        submit.setBounds(405, 320, 65, 35);

        //---- label1 ----
        label1.setForeground(new Color(0x3333ff));
        add(label1);
        label1.setBounds(175, 320, 120, 35);

        //---- submit2 ----
        submit2.setText("\u53d6\u6d88");
        submit2.addActionListener(e -> submit2(e));
        add(submit2);
        submit2.setBounds(470, 320, 65, 35);

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        }
        add(panel1);
        panel1.setBounds(30, 325, 140, 30);

        //---- label4 ----
        label4.setText("\u5224\uff1a");
        add(label4);
        label4.setBounds(5, 320, 30, 30);

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
    public JLabel name;
    public JButton equip1;
    public JScrollPane scrollPane1;
    public JPanel handcards;
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
    public JButton submit2;
    public JPanel panel1;
    private JLabel label4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
