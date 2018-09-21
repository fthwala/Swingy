package com.fthwala.swingy.utils;

import com.fthwala.swingy.controller.Map;
import com.fthwala.swingy.view.GUI;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;


public class FightOrRunActionListener implements ActionListener {
    private Map map;
    private JTextArea displayScreen;
    private JButton[] fightOrRunButtons;
    private JButton[] navigationButtons;

    public FightOrRunActionListener(Map map, JTextArea displayScreen, JButton[] fightOrRunButtons, JButton[] navigationButtons){
        this.map = map;
        this.displayScreen = displayScreen;
        this.fightOrRunButtons = fightOrRunButtons;
        this.navigationButtons = navigationButtons;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        this.fightOrRunButtons[0].setEnabled(false);
        this.fightOrRunButtons[1].setEnabled(false);
        String meetOutcome = this.fightOrRunButtons[0].getText();

        if (meetOutcome.equals("RUN")){
            meetOutcome = this.map.meetOutcome();
        } 
        
        if (meetOutcome.equals("FIGHT")){
            String fightResults = this.map.fight();
            JOptionPane.showMessageDialog(null, "\tYOU " + fightResults);
            if (fightResults.equals("LOSS")){
                System.exit(1);
            }
            JOptionPane.showMessageDialog(null, GUI.hero.heroStats());
        }

        this.navigationButtons[0].setEnabled(true);
        this.navigationButtons[1].setEnabled(true);
        this.navigationButtons[2].setEnabled(true);
        this.navigationButtons[3].setEnabled(true);
        displayScreen.setText(this.map.toString());
    }
    
}