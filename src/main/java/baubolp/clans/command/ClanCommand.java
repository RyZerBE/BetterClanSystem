package baubolp.clans.command;

import baubolp.clans.Clans;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.command.subcommand.SubCommandManager;
import baubolp.clans.command.subcommand.type.CreateCommand;
import baubolp.clans.command.subcommand.type.HelpCommand;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class ClanCommand extends Command {

    public ClanCommand() {
        super("clan");
        Clans.getInstance().getSubCommandManager().registerSubCommand(this, new HelpCommand());
        Clans.getInstance().getSubCommandManager().registerSubCommand(this, new CreateCommand());
    }

    @Override
    public boolean onExecute(CommandSender sender, String label, String[] args) {
        if(args.length < 1) {
            ProxyServer.getInstance().dispatchCommand(sender, "clan help");
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
