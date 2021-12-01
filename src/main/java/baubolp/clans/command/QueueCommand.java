package baubolp.clans.command;

import baubolp.clans.Clans;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.command.subcommand.SubCommandManager;
import baubolp.clans.command.subcommand.type.queue.JoinQueueCommand;
import baubolp.clans.command.subcommand.type.queue.LeaveQueueCommand;
import baubolp.clans.command.subcommand.type.queue.ToggleCommand;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class QueueCommand extends Command {

    public QueueCommand() {
        super("queue");
        SubCommandManager subCommandManager = Clans.getInstance().getSubCommandManager();
        subCommandManager.registerSubCommand(this, new JoinQueueCommand());
        subCommandManager.registerSubCommand(this, new LeaveQueueCommand());
        subCommandManager.registerSubCommand(this, new ToggleCommand());
    }

    @Override
    public boolean onExecute(CommandSender sender, String label, String[] args) {
        if(args.length < 1) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Unknown Queue subcommand!");
            return true;
        }

        String subCommandName = args[0];
        SubCommandManager manager = Clans.getInstance().getSubCommandManager();
        if(!manager.isRegistered(this, subCommandName)) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Unknown command. Please use /clan help");
            return true;
        }

        SubCommand subCommand = manager.getSubCommandByName(this, subCommandName);
        subCommand.execute(sender, this, args);
        return true;
    }
}
