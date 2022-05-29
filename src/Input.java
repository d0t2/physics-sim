import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

public class Input extends JPanel {
    private BallSystem system;
    private JPanel optionP, fieldP, ballP, actionP;
    private JButton clearB;
    private JRadioButton pickRB, delRB, addRB;
    private JCheckBox moveCB, ppCB;
    private JSlider dragSld, gravitySld, chargeSld;

    public Input(BallSystem s) {
        system = s;

        init_optionP();
        init_ballP();
        init_fieldP();
        init_actionP();

        add(optionP);
        add(ballP);
        add(fieldP);
        add(actionP);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void init_optionP() {
        optionP = new JPanel();
        ButtonGroup toolsG = new ButtonGroup();

        addRB = new JRadioButton();
        addRB.setSelected(true);
        addRB.setActionCommand("0");
        addRB.setIcon(new ImageIcon(
                Input.class.getResource("icons/+_OFF.png")));
        addRB.setSelectedIcon(new ImageIcon(
                Input.class.getResource("icons/+_ON.png")));
        addRB.addActionListener(new buttonsListener());
        toolsG.add(addRB);

        delRB = new JRadioButton();
        delRB.setActionCommand("1");
        delRB.setIcon(new ImageIcon(
                Input.class.getResource("icons/X_OFF.png")));
        delRB.setSelectedIcon(new ImageIcon(
                Input.class.getResource("icons/X_ON.png")));
        delRB.addActionListener(new buttonsListener());
        toolsG.add(delRB);

        pickRB = new JRadioButton();
        pickRB.setActionCommand("2");
        pickRB.setIcon(new ImageIcon(
                Input.class.getResource("icons/HAND_OFF.png")));
        pickRB.setSelectedIcon(new ImageIcon(
                Input.class.getResource("icons/HAND_ON.png")));
        pickRB.addActionListener(new buttonsListener());
        toolsG.add(pickRB);

        optionP.add(addRB);
        optionP.add(delRB);
        optionP.add(pickRB);
        optionP.setMaximumSize(optionP.getMinimumSize());
    }

    public void init_ballP() {
        ballP = new JPanel();
        JPanel colorP = new JPanel();
        JPanel chargeP = new JPanel();
        ballP.setLayout(new BoxLayout(ballP, BoxLayout.Y_AXIS));

        ArrayList<JRadioButton> colorBList = new ArrayList<JRadioButton>();
        for (int i = 0; i < 7; i++)
            colorBList.add(new JRadioButton());
        colorBList.get(0).setSelected(true);

        ButtonGroup colorG = new ButtonGroup();
        int i = 0;
        String[] iconNames = {"RED", "ORANGE", "YELLOW",
                "GREEN", "CYAN", "BLUE", "PURPLE"};
        for (JRadioButton b : colorBList) {
            b.setIcon(new ImageIcon(
                    Input.class.getResource("icons/" + iconNames[i] + "_NP.png")));
            b.setSelectedIcon(new ImageIcon(
                    Input.class.getResource("icons/" + iconNames[i] + "_P.png")));
            b.addActionListener(new colorListener());
            b.setActionCommand("" + i);
            colorG.add(b);
            colorP.add(b);
            i++;
        }

        moveCB = new JCheckBox();
        moveCB.setSelected(true);
        moveCB.setIcon(new ImageIcon(
                Input.class.getResource("icons/MOVE_LOCK.png")));
        moveCB.setPressedIcon(new ImageIcon(
                Input.class.getResource("icons/MOVE_MID.png")));
        moveCB.setSelectedIcon(new ImageIcon(
                Input.class.getResource("icons/MOVE_UNLOCK.png")));
        moveCB.addActionListener(new buttonsListener());

        chargeSld = new JSlider(JSlider.HORIZONTAL, -10, 10, 0);
        chargeSld.setUI(new CustomSld(chargeSld));
        chargeSld.addChangeListener(new sliderListener());
        JLabel chargeL = new JLabel();
        chargeL.setIcon(new ImageIcon(
                Input.class.getResource("icons/CHARGE.png")));

        colorP.add(moveCB);
        chargeP.add(chargeL);
        chargeP.add(chargeSld);
        ballP.add(colorP);
        ballP.add(chargeP);
        ballP.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        ballP.setMaximumSize(ballP.getMinimumSize());
    }

    public void init_fieldP() {
        fieldP = new JPanel();
        JPanel fieldGP = new JPanel();
        JPanel fieldDP = new JPanel();

        fieldP.setLayout(new BoxLayout(fieldP, BoxLayout.Y_AXIS));

        gravitySld = new JSlider(JSlider.HORIZONTAL, -10, 10, 0);
        gravitySld.setUI(new CustomSld(gravitySld));
        gravitySld.addChangeListener(new sliderListener());
        JLabel gravityL = new JLabel();
        gravityL.setIcon(new ImageIcon(
                Input.class.getResource("icons/GRAVITY.png")));

        dragSld = new JSlider(JSlider.HORIZONTAL, 0, 20, 0);
        dragSld.setUI(new CustomSld(dragSld));
        dragSld.addChangeListener(new sliderListener());
        JLabel dragL = new JLabel();
        dragL.setIcon(new ImageIcon(
                Input.class.getResource("icons/DRAG.png")));

        fieldGP.add(gravityL);
        fieldGP.add(gravitySld);
        fieldDP.add(dragL);
        fieldDP.add(dragSld);
        fieldP.add(fieldGP);
        fieldP.add(fieldDP);

        fieldP.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        fieldP.setMaximumSize(ballP.getMinimumSize());
    }

    public void init_actionP() {
        actionP = new JPanel();

        ppCB = new JCheckBox();
        ppCB.setSelected(true);
        ppCB.setActionCommand("pause");
        ppCB.setIcon(new ImageIcon(
                Input.class.getResource("icons/PP_PAUSE.png")));
        ppCB.setPressedIcon(new ImageIcon(
                Input.class.getResource("icons/PP_MID.png")));
        ppCB.setSelectedIcon(new ImageIcon(
                Input.class.getResource("icons/PP_PLAY.png")));
        ppCB.addActionListener(new buttonsListener());

        clearB = new JButton();
        clearB.setBorder(null);
        clearB.setOpaque(false);
        clearB.setContentAreaFilled(false);
        clearB.setIcon(new ImageIcon(
                Input.class.getResource("icons/TRASH_OFF.png")));
        clearB.setPressedIcon(new ImageIcon(
                Input.class.getResource("icons/TRASH_ON.png")));
        clearB.addActionListener(new buttonsListener());


        actionP.add(ppCB);
        actionP.add(clearB);
        actionP.setMaximumSize(ballP.getMinimumSize());
    }

    private class buttonsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object test = e.getSource();

            if (test.equals(clearB))
                system.reset();
            if (test.equals(moveCB))
                system.setNxtMoves(((JCheckBox)test).isSelected());
            if (test.equals(ppCB))
                system.setPaused(!((JCheckBox)test).isSelected());
            if (test.equals(addRB) || test.equals(delRB) || test.equals(pickRB))
                system.setToolState(Integer.parseInt(e.getActionCommand()));

        }
    }

    private class colorListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            system.setNxtColor(Integer.parseInt(e.getActionCommand()));
        }
    }

    private class sliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            Object test = e.getSource();

            if (test.equals(chargeSld))
                system.setNxtQ(chargeSld.getValue());
            if (test.equals(gravitySld))
                system.setGravity(gravitySld.getValue());
            if (test.equals(dragSld))
                system.setDrag(dragSld.getValue());
        }
    }

    private static class CustomSld extends BasicSliderUI {
        public CustomSld(JSlider slider) {
            super(slider);
        }

        public void paintTrack(Graphics g) {
            Rectangle t = trackRect;
            Rectangle th = thumbRect;
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(6));
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(t.x - th.width,
                    t.y + t.height / 2,
                    t.x + t.width,
                    t.y + t.height / 2);

            if (slider.getMinimum() != 0) {
                g2.setColor(Color.GRAY);
                g2.drawLine(t.x - th.width,
                        t.y + t.height / 2,
                        t.x + t.width / 2,
                        t.y + t.height / 2);
            }
        }

        public void paintThumb(Graphics g) {
            Rectangle t = thumbRect;
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(new Color(237, 237, 237));
            g2.fillRect(t.x, t.y, t.width, t.height);
            g2.setColor(new Color(191, 191, 191));
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(t.x + 1, t.y, t.width - 3, t.height);
            slider.repaint();
        }
    }
}
