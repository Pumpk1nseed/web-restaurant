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
        commands.put(CommandName.UPDATE_USER_INFO, new UpdateUserInfoCommand());
        commands.put(CommandName.GET_MENU, new GetMenuCommand());
        commands.put(CommandName.CHANGE_LOCALIZATION, new ChangeLocalizationCommand());
        commands.put(CommandName.ADD_DISH_TO_ORDER, new AddDishToOrderCommand());
        commands.put(CommandName.REMOVE_DISH_FROM_ORDER, new RemoveDishFromOrderCommand());
        commands.put(CommandName.CHECKOUT, new CheckoutCommand());
        commands.put(CommandName.DELETE_ORDER, new DeleteOrderCommand());
        commands.put(CommandName.MAKE_ORDER, new MakeOrderCommand());
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
