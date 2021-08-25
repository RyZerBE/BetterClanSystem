package baubolp.clans.command.subcommand.type;

import baubolp.clans.Clans;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class LeaveCommand extends SubCommand {
    public LeaveCommand() {
        super("leave");
    }

    @Override
    public String getDescription() {
        return "Leave your current clan";
    }

    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        UserManager userManager = Clans.getUserManager();
        if(!userManager.isRegistered(sender.getName())) return;
        User user = userManager.getUser(sender.getName());

        if(!user.isInClan()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You are clanless");
            return;
        }

        if(user.isLeader()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You can't leave your own clan!");
            return;
        }

        user.leaveClan(true);
        sender.sendMessage(Clans.PREFIX + Color.RED + "Your left the clan");
    }
}
