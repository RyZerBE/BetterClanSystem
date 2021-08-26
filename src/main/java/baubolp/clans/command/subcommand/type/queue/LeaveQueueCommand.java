package baubolp.clans.command.subcommand.type.queue;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class LeaveQueueCommand extends SubCommand {

    public LeaveQueueCommand() {
        super("leave");
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

        if(!user.hasPermission("queue.join")) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your role can't do that!");
            return;
        }

        Clan clan = user.getClan();
        if(!clan.joinedQueue()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your clan aren't joined the queue.");
            return;
        }

        clan.getSubscriber().removeFromQueue(false);
        clan.leaveQueue();
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "You left the ClanWar queue successfully");
    }
}
