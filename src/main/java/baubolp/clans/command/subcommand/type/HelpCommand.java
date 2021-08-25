package baubolp.clans.command.subcommand.type;

import baubolp.clans.Clans;
import baubolp.clans.command.subcommand.SubCommand;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class HelpCommand extends SubCommand {

    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        StringBuilder message = new StringBuilder();
        message.append(Clans.PREFIX).append(Color.YELLOW).append("Help list of Clans:");
        for (SubCommand subCommand : Clans.getInstance().getSubCommandManager().getSubCommands(parent)) {
            message.append("\n" + "/clan ").append(subCommand.getName())
                    .append(" ").append(Color.DARK_GRAY)
                    .append("| ").append(Color.YELLOW).append(subCommand.getDescription());
        }

        sender.sendMessage(message.toString());
    }

    @Override
    public String getDescription() {
        return "See the list of clan commands";
    }
}
