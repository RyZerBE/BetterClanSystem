package baubolp.clans.command.subcommand;

import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;

public abstract class SubCommand {

    public final String name;

    public SubCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return "";
    }

    public abstract void execute(CommandSender sender, Command parent, String[] args);
}
