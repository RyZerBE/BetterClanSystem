package baubolp.clans.command.subcommand;

import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;

public abstract class SubCommand {

    public final String name;
    public final boolean admin;

    public SubCommand(String name) {
        this.name = name;
        admin = false;
    }
    public SubCommand(String name, boolean isAdmin) {
        this.name = name;
        admin = isAdmin;
    }

    public boolean isAdminCommand() {
        return admin;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return "";
    }

    public abstract void execute(CommandSender sender, Command parent, String[] args);
}
