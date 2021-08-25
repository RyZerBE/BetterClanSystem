package baubolp.clans.command.subcommand.type;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class KickCommand extends SubCommand {

    public KickCommand() {
        super("kick");
    }

    @Override
    public String getDescription() {
        return "Kick a player from your clan";
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

        if(user.hasPermission("member.kick")) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your role can't do that!");
            return;
        }
        if(args.length != 2) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Please use: " + Color.YELLOW + "/clan kick <Name of Member>");
            return;
        }

        String playerName = args[1];
        if(!userManager.isRegistered(sender.getName())) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The player " + Color.YELLOW + playerName + Color.RED + " isn't registered!");
            return;
        }
        User kickUser = userManager.getUser(playerName);
        Clan clan = kickUser.getClan();

        if(clan == null) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The player has no clan!");
            return;
        }

        if(!clan.getName().equals(user.getClan().getName())) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The player isn't in your clan!");
            return;
        }

        if(user.getRole().getPriority() <= kickUser.getRole().getPriority()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You cannot kick a player who has the same or a higher role!");
            return;
        }

        kickUser.leaveClan(true);
        sender.sendMessage(Clans.PREFIX + Color.RED + "You kicked the player " + Color.YELLOW + playerName);
    }
}
