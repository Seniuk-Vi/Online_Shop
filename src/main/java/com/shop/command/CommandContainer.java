package com.shop.command;

import com.shop.command.admin.BlockUserCommand;
import com.shop.command.admin.DeleteProductCommand;
import com.shop.command.admin.EditOrderStatusCommand;
import com.shop.command.admin.EditProductCommand;
import com.shop.command.admin.EditProductShowCommand;
import com.shop.command.admin.UnblockUserCommand;
import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static Map<String, Command> commands = new HashMap();

    public CommandContainer() {
    }

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
        commands.put("showOrders", new ShowOrdersCommand());
        commands.put("logout", new LogoutCommand());
    }
}
