package baubolp.clans.command.subcommand.type.queue;

import baubolp.clans.Clans;
import baubolp.clans.command.subcommand.SubCommand;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class ToggleCommand extends SubCommand {

    public ToggleCommand() {
        super("toggle");
    }

    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        if(!sender.hasPermission("clan.admin")) return;

        if(Clans.getQueueManager().join) {
            Clans.getQueueManager().join = false;
            sender.sendMessage(Clans.PREFIX + Color.RED + "Die CW-Queue wurde deaktiviert.");
        }else {
            Clans.getQueueManager().join = true;
            sender.sendMessage(Clans.PREFIX + Color.GREEN + "Die CW-Queue wurde aktiviert.");
        }
    }
}
