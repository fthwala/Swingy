package com.fthwala.swingy.utils;

import com.fthwala.swingy.model.Hero;
import com.fthwala.swingy.view.GUI;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class CreateHeroActionListener implements ActionListener {
    private JFrame createHeroWindow;
    private Hero heroRef;
    private JTextField nameInput;
    private JTextField classInput;
    private JComboBox levelInput;
    private JComboBox experienceInput;
    private JComboBox attackInput;
    private JComboBox defenceInput;
    private JComboBox hitPointsInput;

    public CreateHeroActionListener(JFrame createHeroWindow, Hero hero, JTextField nameInput, JTextField classInput, JComboBox levelInput, JComboBox experienceInput, JComboBox attackInput, JComboBox defenceInput, JComboBox hitPointsInput){
        this.createHeroWindow = createHeroWindow;
        this.heroRef = hero;
        this.nameInput = nameInput;
        this.classInput = classInput;
        this.levelInput = levelInput;
        this.experienceInput = experienceInput;
        this.attackInput = attackInput;
        this.defenceInput = defenceInput;
        this.hitPointsInput = hitPointsInput;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (nameInput.getText().equals("") || classInput.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please fill in all fields");
        } else {
            String nameData  = this.nameInput.getText();
            String classData = this.classInput.getText();
            int levelData  = Integer.parseInt(String.valueOf(this.levelInput.getSelectedItem()));
            int experienceData  = Integer.parseInt(String.valueOf(this.experienceInput.getSelectedItem())); //long
            int attackData  = Integer.parseInt(String.valueOf(this.attackInput.getSelectedItem()));
            int defenceData  = Integer.parseInt(String.valueOf(this.defenceInput.getSelectedItem()));
            int hitPointsData  = Integer.parseInt(String.valueOf(this.hitPointsInput.getSelectedItem()));
            //this.heroRef = new Hero(nameData, classData, levelData, experienceData, attackData, defenceData, hitPointsData);
            this.heroRef.setHero(nameData, classData, levelData, experienceData, attackData, defenceData, hitPointsData);
            
            this.createHeroWindow.dispose();
            GUI.game();
        }
    }
}