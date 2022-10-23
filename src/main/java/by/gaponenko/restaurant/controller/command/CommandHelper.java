package by.gaponenko.restaurant.controller.command;

import by.gaponenko.restaurant.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandHelper {
    private static final CommandHelper instance = new CommandHelper();

    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandHelper() {
        commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.UPDATE_PERSONAL_INFO, new UpdatePersonalInfoCommand());
        commands.put(CommandName.GET_MENU, new GetMenuCommand());
        commands.put(CommandName.CHANGE_LOCALIZATION, new ChangeLocalizationCommand());
        commands.put(CommandName.ADD_DISH_TO_ORDER, new AddDishToOrderCommand());
        commands.put(CommandName.REMOVE_DISH_FROM_ORDER, new RemoveDishFromOrderCommand());
        commands.put(CommandName.CHECKOUT, new CheckoutCommand());
        commands.put(CommandName.DELETE_ORDER, new DeleteOrderCommand());
        commands.put(CommandName.MAKE_ORDER, new MakeOrderCommand());
        commands.put(CommandName.GO_TO_ORDER_CONFIRMATION, new GoToOrderConfirmationCommand());
        commands.put(CommandName.GO_TO_ORDER_COOKING, new GoToOrderCookingCommand());
        commands.put(CommandName.CONFIRM_ORDER, new ConfirmOrderCommand());
        commands.put(CommandName.COOK_ORDER, new CookOrderCommand());
        commands.put(CommandName.GET_HISTORY_OF_ORDERS, new GetHistoryOfOrdersCommand());
        commands.put(CommandName.SIGN_OUT, new SignOutCommand());
        commands.put(CommandName.GET_USER, new GetUserCommand());
        commands.put(CommandName.EDIT_USER, new EditUserCommand());
        commands.put(CommandName.ADD_NEW_USER, new AddNewUserCommand());
        commands.put(CommandName.REMOVE_USER, new RemoveUserCommand());
        commands.put(CommandName.GET_ORDERS, new GetOrdersCommand());
        commands.put(CommandName.FIND_DISHES_BY, new FindDishesByCommand());
        commands.put(CommandName.EDIT_DISH, new EditDishCommand());
        commands.put(CommandName.REMOVE_DISH_FROM_MENU, new RemoveDishFromMenuCommand());
        commands.put(CommandName.ADD_DISH_TO_MENU, new AddDishToMenuCommand());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
    }

    public static CommandHelper getInstance() {
        return instance;
    }

    public Command getCommand(String commandName) {
        CommandName name = CommandName.valueOf(commandName.toUpperCase());
        Command command;
        if (null != name) {
            command = commands.get(name);
        } else {
            command = commands.get(CommandName.NO_SUCH_COMMAND);
        }
        return command;
    }

}
