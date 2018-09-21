package com.fthwala.swingy.controller;

import com.fthwala.swingy.model.Character;
import com.fthwala.swingy.model.Hero;
import com.fthwala.swingy.model.Villain;
import com.fthwala.swingy.model.Position;
import com.fthwala.swingy.view.Console;
import java.util.HashMap;
import java.util.Set;
import java.util.Random;
import java.util.*;


public class Map {
    private int level;
    private int gridSize;
    private int[][] grid;
    private HashMap<Integer, Character> characters;
    private static int numberOfCharacters = 0;
    private int heroSymbol;
    private final String[] meetOutcomes = {"RUN", "FIGHT"};

    public Map(int level, Character hero) {
        this.level = level;
        this.gridSize = this.calculateGridSize(this.level);
        this.grid = new int[this.gridSize][this.gridSize];
        this.characters = new HashMap<Integer, Character>();
        this.createCharacters(hero);
    }

    private void changeMap(){
        Hero hero = (Hero)this.characters.get(this.heroSymbol);
        hero.incrementLevel();
        
        Map.numberOfCharacters = 0;
        this.level = hero.getLevel();
        this.gridSize = this.calculateGridSize(this.level);
        this.grid = new int[this.gridSize][this.gridSize];
        this.characters = new HashMap<Integer, Character>();

        this.addHero(hero);
        this.addVillains();
    }

    private int calculateGridSize(int level){
        return ((level - 1) * 5 + 10 - (level % 2));
    }

    private void addHero(Character hero){
        int intialPosition = this.gridSize / 2;
        hero.setPosition(intialPosition, intialPosition);
        this.characters.put(++Map.numberOfCharacters, hero);
        this.heroSymbol = Map.numberOfCharacters;
        this.grid[intialPosition][intialPosition] = 1;
    }

    private void addVillains(){
        Random random = new Random();
        int numberOfVillains = this.level * 5;
        int totalCharacters = 1 + numberOfVillains;
        for (int i = 2; i <= totalCharacters; i++){
            Character villain = new Villain(this.level);
            while (true){
                int row = random.nextInt(this.gridSize);
                int column = random.nextInt(this.gridSize);
                if (this.grid[row][column] == 0){
                    villain.setPosition(row, column);
                    this.grid[row][column] = ++Map.numberOfCharacters;
                    break;
                }
            }
            this.characters.put(Map.numberOfCharacters, villain);
        }
    }

    private void createCharacters(Character hero){
        this.addHero(hero);
        this.addVillains();
    }

    public String[] getMetVillainsPosition(){
        String villainsPositions = null;
        Hero hero = (Hero)this.characters.get(this.heroSymbol);
        int row = hero.getRow();
        int column = hero.getColumn();

        if (this.grid[row - 1][column] > 0){
            villainsPositions = (row - 1) + "," + column;
        } else if (this.grid[row][column + 1] > 0){
            if (villainsPositions == null)
                villainsPositions = row + "," + (column + 1);
            else
                villainsPositions += "\n" + row + "," + (column + 1);
        }
        else if (this.grid[row][column - 1] > 0){
            if (villainsPositions == null)
                villainsPositions = row + "," + (column - 1);
            else
                villainsPositions += "\n" + row + "," + (column - 1);
        }
        else if (this.grid[row + 1][column] > 0){
            if (villainsPositions == null)
                villainsPositions = (row + 1) + "," + column;
            else
                villainsPositions += "\n" + (row + 1) + "," + column;
        }
        return villainsPositions.split("\n");
    }

    public String getMetVillainsPositionString(){
        String results = null;
        String villainsPositions[] = this.getMetVillainsPosition();

        for (int i = 0; i < villainsPositions.length; i++){
            String[] position = villainsPositions[i].split(",");
            if (results == null)
                results = String.format("\tVillain at [%s:%s]\n", position[0], position[1]);
            else
                results += String.format("\tVillain at [%s:%s]\n", position[0], position[1]);
        }

        return results;
    }

    public Boolean metVillain(){
        Boolean status = false;
        Hero hero = (Hero)this.characters.get(this.heroSymbol);
        int row = hero.getRow();
        int column = hero.getColumn();
        
        // North
        if (((row - 1) >= 0) && (this.grid[row - 1][column] > 0))
            status = true;
        // South
        else if (((row + 1) < this.gridSize) && (this.grid[row + 1][column] > 0))
            status = true;

        // East
        else if (((column + 1) < this.gridSize) && (this.grid[row][column + 1] > 0))
            status = true;

        // West
        else if (((column - 1) >= 0) && (this.grid[row][column - 1] > 0))
            status = true;

        

        return status;
    }

    public String meetOutcome(){
        String results = null;
        Hero hero = (Hero)this.characters.get(this.heroSymbol);
        int row = hero.getRow();
        int column = hero.getColumn();
        int prow = -1;
        int pcolumn = -1;
        
        if (hero.getPreviousPosition() == null){
            results = "FIGHT";
        } else {
            results = this.meetOutcomes[new Random().nextInt(2)];
            if (results.equals("RUN")){
                prow = hero.getPreviousPosition().getRow();
                pcolumn = hero.getPreviousPosition().getColumn();
                
                this.grid[row][column] = 0;
                hero.setPosition(prow, pcolumn);
                hero.makePreviousPositionNull();
                this.grid[prow][pcolumn] = 1;
            }
        }

        return results;
    }

    private String matchStatus(int hero, int villain){
        if (hero > villain){
            return "WIN";
        } else if (hero == villain){
            String[] results = {"WIN", "LOSS"};
            return results[new Random().nextInt(results.length)];
        } else {
            return "LOSS";
        }
    }

    public String fight(){
        String[] villains = this.getMetVillainsPosition();
        Hero hero = (Hero)this.characters.get(this.heroSymbol);
        Villain currentVillain = null;
        int totalWeapon = 0;
        int totalArmor = 0;
        int totalHelm = 0;
        String[] attackOrDefend = {"ATTACK", "DEFEND"};
        Random random = new Random();
        String heroMode = attackOrDefend[random.nextInt(attackOrDefend.length)];
        String fightStatus = null;
        HashMap<Integer, Position> villainsSymbols = new HashMap<>();

        for (int i=0; i < villains.length; i++){
            String[] position = villains[i].split(",");
            int row = Integer.parseInt(position[0]);
            int column = Integer.parseInt(position[1]);
            int villainSymbol = this.grid[row][column];
            villainsSymbols.put(villainSymbol, new Position(row, column));
            currentVillain = (Villain)this.characters.get(villainSymbol);

            totalWeapon += currentVillain.getWeaponValue();
            totalArmor += currentVillain.getArmorValue();
            totalHelm += currentVillain.getHelmValue();
        }

        if (heroMode.equals("ATTACK")){
            int heroAttack = hero.getAttackValue() * (hero.getHitPoints() / villains.length);
            int villainsDefence = totalHelm + totalArmor;
            fightStatus = this.matchStatus(heroAttack, villainsDefence);
        } else {
            int heroDefence = hero.getDefenceValue() * (hero.getHitPoints() / villains.length);
            int villainsAttack = totalWeapon + totalArmor;
            fightStatus = this.matchStatus(heroDefence, villainsAttack);
        }

        if (fightStatus.equals("WIN")){
            int weapon = totalWeapon / villains.length;
            int armor = totalArmor / villains.length;
            int helm = totalHelm / villains.length;

            hero.takeArtifacts(weapon, armor, helm);
            Set<Integer> keys = villainsSymbols.keySet();
            int[] keysArray = new int[keys.size()];

            int index = 0;
            for(Integer element : keys) { 
                keysArray[index++] = element.intValue();
            }

            for (int i=0; i < keysArray .length; i++){
                int currentSymbol = keysArray[i];
                this.characters.remove(currentSymbol);
                Position p = (Position)villainsSymbols.get(currentSymbol);
                this.grid[p.getRow()][p.getColumn()] = 0;
            }
        }

        return fightStatus;
    }

    private String navigation(String direction){
        String status = null;
        Console console = new Console();
        Hero hero = (Hero)this.characters.get(this.heroSymbol);
        int row = hero.getRow();
        int column = hero.getColumn();
        Scanner sc = new Scanner(System.in);
        String move = null;
        while (true)
        {

            if (direction.equals("N") || direction.equals("n")){
                if (row == 0){
                    status = "END";
                } else {
                    this.grid[row][column] = 0;
                    hero.changePosition(-1, 0);
                    this.grid[hero.getRow()][hero.getColumn()] = 1;
                    status = "CONTINUE";
                }
            } else if (direction.equals("E") || direction.equals("e")){
                if (hero.getColumn() == (this.gridSize - 1)){
                    status = "END";
                } else {
                    this.grid[hero.getRow()][hero.getColumn()] = 0;
                    hero.changePosition(0, 1);
                    this.grid[hero.getRow()][hero.getColumn()] = 1;
                    status = "CONTINUE";
                }
            } else if (direction.equals("W") || direction.equals("w")){
                if (hero.getColumn() == 0){
                    status = "END";
                } else {
                    this.grid[hero.getRow()][hero.getColumn()] = 0;
                    hero.changePosition(0, -1);
                    this.grid[hero.getRow()][hero.getColumn()] = 1;
                    status = "CONTINUE";
                }
            } else if (direction.equals("S") || direction.equals("s")){
                if (hero.getRow() == (this.gridSize - 1)){
                    status = "END";
                } else {
                    this.grid[hero.getRow()][hero.getColumn()] = 0;
                    hero.changePosition(1, 0);
                    this.grid[hero.getRow()][hero.getColumn()] = 1;
                    status = "CONTINUE";
                }
            }
            else{
                System.out.println("You entred an invalide control the game ends");
                System.exit(1);
                //console.run();
                // this.move(sc.nextLine());
                //move(direction);
            }
            return status;
        }

        
    }

    public String move(String direction){
        //Console console = new Console(); 
        String move = this.navigation(direction);
        //if ( direction.equals("S") || direction.equals("s") || direction.equals("N") || direction.equals("n") || direction.equals("E") || direction.equals("e") || direction.equals("W") || direction.equals("w")){
            if (move.equals("END")){
            if (this.level < 5){
                this.changeMap();
                move = "LEVEL UP";
            }
        //}else{
           // console.run();
        //}
    }
        
        return move;
    }

    public String toString(){
        String map = "";

        for (int i = 0; i < gridSize; i++){
            map += "|";
            for (int j = 0; j < gridSize; j++){
                map += String.format("%2d|", this.grid[i][j]);
            }
            map += "\n";
        }
        return map;
    }
}