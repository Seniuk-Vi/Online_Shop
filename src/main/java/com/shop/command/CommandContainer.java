package com.shop.command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private static Map<String, Command> commands;

    static{
        commands = new HashMap<>();

        commands.put("login",new LoginCommand());
        commands.put("registration",new RegistrationCommand());
        commands.put("addProduct",new AddProductCommand());
        commands.put("addCategory",new AddCategoryCommand());
        commands.put("showProducts",new DisplayTableCommand());
       // commands.put("")
    }
    public static Command getCommand(String commandName) {
        return commands.get(commandName);

    }
}
