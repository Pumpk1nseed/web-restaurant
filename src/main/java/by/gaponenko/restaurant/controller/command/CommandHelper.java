package by.gaponenko.restaurant.controller.command;

import by.gaponenko.restaurant.controller.command.impl.AuthorizationCommand;
import by.gaponenko.restaurant.controller.command.impl.NoSuchCommand;
import by.gaponenko.restaurant.controller.command.impl.RegistrationCommand;
import by.gaponenko.restaurant.controller.command.impl.UpdateUserInfoCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandHelper {
    private static final CommandHelper instance = new CommandHelper();

    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandHelper() {
        commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.UPDATE_USER_INFO, new UpdateUserInfoCommand());
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
