package baubolp.clans.command.subcommand.type.clan;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class AcceptCommand extends SubCommand {

    public AcceptCommand() {
        super("accept");
    }

    @Override
    public String getDescription() {
        return "Accept a clan request";
    }


    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        UserManager userManager = Clans.getUserManager();
        if(!userManager.isRegistered(sender.getName())) return;
        User user = userManager.getUser(sender.getName());

        if(args.length != 2) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Please use: " + Color.YELLOW + "/clan accept <Name of Clan>");
            return;
        }

        String clanName = args[1];

        if(user.isInClan()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You are already in the clan " + Color.YELLOW + user.getClan().getName());
            return;
        }

        if(!user.alreadySentRequest(clanName)) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You have not received a request from the clan " + Color.YELLOW + clanName);
            return;
        }

        if(!Clans.getClanManager().alreadyExist(clanName)) {
            user.removeClanRequest(clanName, true);
            sender.sendMessage(Clans.PREFIX + Color.RED + "The clan does not exist anymore!");
            return;
        }

        Clan clan = Clans.getClanManager().getClan(clanName);
        if(clan.getState() == Clan.CLOSE) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You can't accept the invitation because the clan is closed!");
            return;
        }

        user.removeClanRequest(clan, true);
        user.setClan(clan, true);
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "You accepted the request");
    }
}
