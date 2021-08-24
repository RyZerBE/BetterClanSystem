package baubolp.clans.command.subcommand;

import baubolp.clans.Clans;
import dev.waterdog.waterdogpe.command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubCommandManager {

    public HashMap<Command, List<SubCommand>> subCommands;
    public Clans clans;

    public SubCommandManager(Clans clans) {
        this.subCommands = new HashMap<>();
        this.clans = clans;
    }

    public HashMap<Command, List<SubCommand>> getSubCommands() {
        return subCommands;
    }

    public void registerSubCommand(Command owner, SubCommand subCommand) {
        if(!this.subCommands.containsKey(owner)) {
            this.subCommands.put(owner, new ArrayList<>());
        }
        this.subCommands.get(owner).add(subCommand);
    }

    public boolean isRegistered(Command command, String subCommandName) {
        return getSubCommandByName(command, subCommandName) != null;
    }

    public List<SubCommand> getSubCommands(Command command) {
        return this.subCommands.get(command);
    }

    public SubCommand getSubCommandByName(Command command, String subCommandName) {
        for(SubCommand subCommand : getSubCommands(command)) {
            if(subCommandName.equals(subCommand.getName()))
                return subCommand;
        }
        return null;
    }

    public Clans getClanSystem() {
        return clans;
    }
}
