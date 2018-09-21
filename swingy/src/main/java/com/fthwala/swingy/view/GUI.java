package com.fthwala.swingy.view;

import com.fthwala.swingy.utils.CreateHeroActionListener;
import com.fthwala.swingy.utils.SelectHeroActionListener;
import com.fthwala.swingy.utils.NavigationActionListener;
import com.fthwala.swingy.utils.FightOrRunActionListener;
import com.fthwala.swingy.model.Hero;
import com.fthwala.swingy.utils.Tools;
import com.fthwala.swingy.controller.Map;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;

public class GUI implements ViewMode {
    private final String name = "GUI";
    public static Hero hero = new Hero();

    private static JFrame introWindow;
    private static JFrame createHeroWindow;
    private static JFrame selectHeroWindow;
    private static JFrame gameWindow;

    public GUI(){}

    public void init(){
        // Window
        GUI.introWindow = new JFrame(this.name + " MODE");
        GUI.introWindow.setSize(500, 120);
        GUI.introWindow.setLocationRelativeTo(null);
        GUI.introWindow.setVisible(true);

        // Buttons & Events
        JButton createHeroButton = new JButton("Create Hero");
        createHeroButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                GUI.introWindow.dispose();
                GUI.createHero();
            }
        });
        JButton selectHeroButton = new JButton("Select Hero");
        selectHeroButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                GUI.introWindow.dispose();
                GUI.selectHero();
            }
        });
        
        // Layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createHeroButton);
        buttonPanel.add(selectHeroButton);
        GUI.introWindow.add(buttonPanel, BorderLayout.CENTER);
        GUI.introWindow.pack();
    }

    public static void createHero(){
        // Window
        GUI.createHeroWindow = new JFrame("CREATE HERO");
        GUI.createHeroWindow.setSize(500, 120);
        GUI.createHeroWindow.setLocationRelativeTo(null);
        GUI.createHeroWindow.setVisible(true);

        // Components
        JLabel nameLabel = new JLabel("Enter Name");
        JTextField nameInput = new JTextField(20);
        JLabel classLabel = new JLabel("Enter Class");
        JTextField classInput = new JTextField(20);

        JLabel levelLabel = new JLabel("Choose Level");
        JComboBox levelInput = new JComboBox(new String[]{"1", "2", "3", "4", "5", "6"});

        JLabel experienceLabel = new JLabel("Choose Experience");
        JComboBox experienceInput = new JComboBox(new String[]{"5", "10", "15", "20"});

        JLabel attackLabel = new JLabel("Choose Attack");
        JComboBox attackInput = new JComboBox(new String[]{"5", "10", "15", "20"});

        JLabel defenceLabel = new JLabel("Choose Defence");
        JComboBox defenceInput = new JComboBox(new String[]{"5", "10", "15", "20"});

        JLabel hitPointsLabel = new JLabel("Choose Hit Points");
        JComboBox hitPointsInput = new JComboBox(new String[]{"5", "10", "15", "20"});

        // Buttons & Events
        JButton createHeroButton = new JButton("Create Hero");
        createHeroButton.addActionListener(new CreateHeroActionListener(GUI.createHeroWindow, GUI.hero, nameInput, classInput, levelInput, experienceInput, attackInput,defenceInput, hitPointsInput));

         // Layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        JPanel namePanel = new JPanel();
        namePanel.add(nameLabel);
        namePanel.add(nameInput);
        detailsPanel.add(namePanel);
        JPanel classPanel = new JPanel();
        classPanel.add(classLabel);
        classPanel.add(classInput);
        detailsPanel.add(classPanel);
        JPanel levelPanel = new JPanel();
        levelPanel.add(levelLabel);
        levelPanel.add(levelInput);
        detailsPanel.add(levelPanel);
        JPanel experiencePanel = new JPanel();
        experiencePanel.add(experienceLabel);
        experiencePanel.add(experienceInput);
        detailsPanel.add(experiencePanel);
        JPanel attackPanel = new JPanel();
        attackPanel.add(attackLabel);
        attackPanel.add(attackInput);
        detailsPanel.add(attackPanel);
        JPanel defencePanel = new JPanel();
        defencePanel.add(defenceLabel);
        defencePanel.add(defenceInput);
        detailsPanel.add(defencePanel);
        JPanel hitPointsPanel = new JPanel();
        hitPointsPanel.add(hitPointsLabel);
        hitPointsPanel.add(hitPointsInput);
        detailsPanel.add(hitPointsPanel);


        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(createHeroButton, BorderLayout.SOUTH);
        GUI.createHeroWindow.add(mainPanel);
        GUI.createHeroWindow.pack();
    }

    public static void selectHero(){
        // Data
        String[] heroesTemp = Tools.listOfHeroes(Tools.getSavedHeroes(Hero.FILENAME));
        int len = heroesTemp.length;

        // Window
        GUI.selectHeroWindow = new JFrame("SELECT HERO");
        GUI.selectHeroWindow.setSize(500, 120);
        GUI.selectHeroWindow.setLocationRelativeTo(null);
        GUI.selectHeroWindow.setVisible(true);

        // Components
        JComboBox heroesOptions = new JComboBox(heroesTemp);
  

        // Buttons & Events
        JButton selectHeroButton = new JButton("Select Hero");
        selectHeroButton.addActionListener(new SelectHeroActionListener(GUI.selectHeroWindow, GUI.hero, heroesOptions));

        // Layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(heroesOptions, BorderLayout.NORTH);
        mainPanel.add(selectHeroButton, BorderLayout.SOUTH);

        GUI.selectHeroWindow.add(mainPanel);
        GUI.selectHeroWindow.pack();
    }

    public static void game(){
        final Map map = new Map(GUI.hero.getLevel(), GUI.hero);
        String move = null;

        

        // Window
        GUI.gameWindow = new JFrame("SWINGY");
        GUI.gameWindow.setSize(1200, 900);
        GUI.gameWindow.setLocationRelativeTo(null);
        GUI.gameWindow.setVisible(true);

        // Components
        final JTextArea displayScreen = new JTextArea();
        displayScreen.setEditable(false);
        displayScreen.setText(map.toString());

        // Buttons & Events
        JButton upButton = new JButton("UP");
        upButton.setEnabled(false);
        JButton downButton = new JButton("DOWN");
        downButton.setEnabled(false);
        JButton leftButton = new JButton("LEFT");
        leftButton.setEnabled(false);
        JButton rightButton = new JButton("RIGHT");
        rightButton.setEnabled(false);
        JButton fightButton = new JButton("FIGHT");
        fightButton.setEnabled(false);
        JButton runButton = new JButton("RUN");
        runButton.setEnabled(false);


        upButton.addActionListener(new NavigationActionListener(map, displayScreen, new JButton[]{upButton, downButton, leftButton, rightButton}, new JButton[]{fightButton, runButton}));
        downButton.addActionListener(new NavigationActionListener(map, displayScreen, new JButton[]{downButton, upButton, leftButton, rightButton}, new JButton[]{fightButton, runButton}));
        leftButton.addActionListener(new NavigationActionListener(map, displayScreen, new JButton[]{leftButton, upButton, downButton, rightButton}, new JButton[]{fightButton, runButton}));
        rightButton.addActionListener(new NavigationActionListener(map, displayScreen, new JButton[]{rightButton, upButton, downButton, leftButton}, new JButton[]{fightButton, runButton}));
        fightButton.addActionListener(new FightOrRunActionListener(map, displayScreen, new JButton[]{fightButton, runButton}, new JButton[]{upButton, downButton, leftButton, rightButton}));
        runButton.addActionListener(new FightOrRunActionListener(map, displayScreen, new JButton[]{runButton, fightButton}, new JButton[]{upButton, downButton, leftButton, rightButton}));

        // Layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel navigationButtonsPanel = new JPanel();
        navigationButtonsPanel.add(leftButton);
        navigationButtonsPanel.add(upButton);
        navigationButtonsPanel.add(downButton);
        navigationButtonsPanel.add(rightButton);

        JPanel fightOrFlightButtonsPanel = new JPanel();
        fightOrFlightButtonsPanel.add(fightButton);
        fightOrFlightButtonsPanel.add(runButton);


        
        mainPanel.add(displayScreen);
        mainPanel.add(navigationButtonsPanel);
        mainPanel.add(fightOrFlightButtonsPanel);

        GUI.gameWindow.add(mainPanel);
        GUI.gameWindow.pack();

        if (map.metVillain()){
            fightButton.setEnabled(true);
            runButton.setEnabled(true);
            JOptionPane.showMessageDialog(null, "You met the following villain(s)\nFight OR Run");
        } else {
            upButton.setEnabled(true);
            downButton.setEnabled(true);
            leftButton.setEnabled(true);
            rightButton.setEnabled(true);
        }
    }

    public void run(){
        this.init();
    }
}