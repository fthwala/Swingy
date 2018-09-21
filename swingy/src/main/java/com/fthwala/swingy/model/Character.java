package com.fthwala.swingy.model;

public class Character {
    protected Position position;

    public int getRow(){
        return this.position.getRow();
    }

    public int getColumn(){
        return this.position.getColumn();
    }

    public void setPosition(int row, int column){
        if (this.position == null)
            this.position = new Position(row, column);
        else{
            this.position.setRow(row);
            this.position.setColumn(column);
        }
    }
}