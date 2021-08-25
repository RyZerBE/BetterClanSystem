package baubolp.clans.command.subcommand.type;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.clan.ClanManager;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class JoinClanCommand extends SubCommand {

    public JoinClanCommand() {
        super("join");
    }

    @Override
    public String getDescription() {
        return "Join a clan without invite";
    }

    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        UserManager userManager = Clans.getUserManager();
        ClanManager clanManager = Clans.getClanManager();

        if(!userManager.isRegistered(sender.getName())) return;
        User user = userManager.getUser(sender.getName());

        if(user.isInClan()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You are already in the clan " + Color.YELLOW + user.getClan().getName());
            return;
        }

        if(args.length != 2) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Please use: " + Color.YELLOW + "/clan join <Name of Clan>");
            return;
        }

        String clanName = args[1];
        if (!clanManager.alreadyExist(clanName)) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The clan doesn't exist!");
            return;
        }

        Clan clan = clanManager.getClan(clanName);
        if(clan.getState() == Clan.OPEN) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The clan isn't open for everyone!");
            return;
        }

        user.setClan(clan, true);
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "Your joined the clan " + Color.YELLOW + clanName);
    }
}
