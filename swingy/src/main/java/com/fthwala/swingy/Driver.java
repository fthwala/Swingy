package com.fthwala.swingy;

import com.fthwala.swingy.controller.Game;

public class Driver {
    public static void main(String[] args) {
        if (args.length != 1){
            String report = String.format("ERROR: 1 argument required.\nUSAGE: java -jar swing.jar [VIEW MODE]");
            System.out.println(report);
            System.exit(1);
        } else {
            Game game = new Game(args[0]);
        }
    }
}