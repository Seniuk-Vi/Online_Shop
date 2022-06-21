package com.shop.command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private static Map<String, Command> commands;

    static {
        commands = new HashMap<>();

        commands.put("login", new LoginCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("addProduct", new AddProductCommand());
        commands.put("addCategory", new AddCategoryCommand());
        commands.put("addToCart", new AddToCartCommand());
        commands.put("addOrder", new AddOrderCommand());
        commands.put("editProduct", new EditProductCommand());
        commands.put("editOrderStatus", new EditOrderStatusCommand());
        commands.put("showHomePage", new ShowHomePageCommand());
        commands.put("showUserOrders", new ShowUserOrdersCommand());
        commands.put("showUser", new ShowUserCommand());
        commands.put("showUsers", new ShowUsersCommand());
        commands.put("showOrders", new ShowOrdersCommand());
        commands.put("logout", new LogoutCommand());
        // commands.put("")
    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);

    }
}
