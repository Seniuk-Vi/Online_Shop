package com.shop.command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private static Map<String, Command> commands;

    static{
        commands = new HashMap<>();

        commands.put("login",new LoginCommand());
       // commands.put("")
    }
    public static Command getCommand(String commandName) {
        return commands.get(commandName);

    }
}
