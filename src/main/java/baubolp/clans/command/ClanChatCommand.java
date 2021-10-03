package baubolp.clans.command;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.clan.ClanManager;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class ClanChatCommand extends Command {

    public ClanChatCommand() {
        super("cc");
    }

    @Override
    public boolean onExecute(CommandSender sender, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "/cc <Nachricht>");
            return true;
        }

        UserManager userManager = Clans.getUserManager();
        ClanManager clanManager = Clans.getClanManager();

        if (!userManager.isRegistered(sender.getName())) return true;
        User user = userManager.getUser(sender.getName());
        if (!user.isInClan()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You are clanless");
            return true;
        }

        Clan clan = user.getClan();
        StringBuilder message = new StringBuilder();
        for (String arg : args) message.append(arg).append(" ");

        String finalMessage = message.toString();
        clan.sendMessage(Clans.PREFIX + "&b" + sender.getName() + " &8» &e" + finalMessage);
        return true;
    }
}
