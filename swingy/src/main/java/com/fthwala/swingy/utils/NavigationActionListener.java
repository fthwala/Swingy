package com.fthwala.swingy.utils;

import com.fthwala.swingy.controller.Map;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener; //Interface
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;


public class NavigationActionListener implements ActionListener {
    private Map map;
    private JTextArea displayScreen;
    private JButton[] navigationButtons;
    private JButton[] fightOrRunButtons;

    public NavigationActionListener(Map map, JTextArea displayScreen, JButton[] navigationButtons, JButton[] fightOrRunButtons){
        this.map = map;
        this.displayScreen = displayScreen;
        this.navigationButtons = navigationButtons;
        this.fightOrRunButtons = fightOrRunButtons;
    }

    public void check(){
        if (this.map.metVillain()){
            this.navigationButtons[0].setEnabled(false);
            this.navigationButtons[1].setEnabled(false);
            this.navigationButtons[2].setEnabled(false);
            this.navigationButtons[3].setEnabled(false);
            this.fightOrRunButtons[0].setEnabled(true);
            this.fightOrRunButtons[1].setEnabled(true);
            JOptionPane.showMessageDialog(null, "You met the following villain(s)\nFight OR Run");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String direction = this.navigationButtons[0].getText();
        String move = null;


        if (direction.equals("UP")){
            move = this.map.move("N");
            if (move.equals("END")){
                JOptionPane.showMessageDialog(null, "\nGAME\nGame ended. You finished the game.");
                System.exit(1);
            } else if (move.equals("LEVEL UP")){
                JOptionPane.showMessageDialog(null, "\nGAME\nLevel Up. You survived this stage.");
            }
            displayScreen.setText(this.map.toString());
            this.check();
        } else if (direction.equals("DOWN")){
            move = this.map.move("S");
            if (move.equals("END")){
                JOptionPane.showMessageDialog(null, "\nGAME\nGame ended. You finished the game.");
                System.exit(1);
            } else if (move.equals("LEVEL UP")){
                JOptionPane.showMessageDialog(null, "\nGAME\nLevel Up. You survived this stage.");
            }
            displayScreen.setText(this.map.toString());
            this.check();
        } else if (direction.equals("LEFT")){
            move = this.map.move("W");
            if (move.equals("END")){
                JOptionPane.showMessageDialog(null, "\nGAME\nGame ended. You finished the game.");
                System.exit(1);
            } else if (move.equals("LEVEL UP")){
                JOptionPane.showMessageDialog(null, "\nGAME\nLevel Up. You survived this stage.");
            }
            displayScreen.setText(this.map.toString());
            this.check();
        } else if (direction.equals("RIGHT")){
            move = this.map.move("E");
            if (move.equals("END")){
                JOptionPane.showMessageDialog(null, "\nGAME\nGame ended. You finished the game.");
                System.exit(1);
            } else if (move.equals("LEVEL UP")){
                JOptionPane.showMessageDialog(null, "\nGAME\nLevel Up. You survived this stage.");
            }
            displayScreen.setText(this.map.toString());
            this.check();
        }
    }
    
}