package com.shop.command;

import com.shop.command.admin.*;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static Map<String, Command> commands = new HashMap();


    public static Command getCommand(String commandName) {
        return (Command)commands.get(commandName);
    }

    static {
        commands.put("login", new LoginCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("addProduct", new AddProductCommand());
        commands.put("addCategory", new AddCategoryCommand());
        commands.put("addToCart", new AddToCartCommand());
        commands.put("addOrder", new AddOrderCommand());
        commands.put("editProduct", new EditProductCommand());
        commands.put("editProductShow", new EditProductShowCommand());
        commands.put("deleteProduct", new DeleteProductCommand());
        commands.put("editOrderStatus", new EditOrderStatusCommand());
        commands.put("showHomePage", new ShowHomePageCommand());
        commands.put("showUserOrders", new ShowUserOrdersCommand());
        commands.put("deleteProductFromCart", new DeleteProductFromCartCommand());
        commands.put("increaseQuantity", new IncreaseQuantityCommand());
        commands.put("decreaseQuantity", new DecreaseQuantityCommand());
        commands.put("showUser", new ShowUserCommand());
        commands.put("blockUser", new BlockUserCommand());
        commands.put("unblockUser", new UnblockUserCommand());
        commands.put("updateUserData", new UpdateUserDataCommand());
        commands.put("showUsers", new ShowUsersCommand());
        commands.put("showProduct", new ShowProductCommand());
        commands.put("showOrders", new ShowOrdersCommand());
        commands.put("logout", new LogoutCommand());
    }
}
