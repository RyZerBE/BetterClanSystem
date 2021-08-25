package baubolp.clans.command.subcommand.type;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class InviteCommand extends SubCommand {
    public InviteCommand() {
        super("invite");
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

        if(!user.hasPermission("member.invite")) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your role can't do that!");
            return;
        }
        if(args.length != 2) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Please use: " + Color.YELLOW + "/clan invite <Name of Player>");
            return;
        }

        String playerName = args[1];
        if(!userManager.isRegistered(sender.getName())) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The player " + Color.YELLOW + playerName + Color.RED + " isn't registered!");
            return;
        }
        User inviteUser = userManager.getUser(playerName);
        Clan clan = inviteUser.getClan();

        if(clan != null) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The player is already in the clan " + Color.YELLOW + clan.getName());
            return;
        }

        if(inviteUser.alreadySentRequest(user.getClan().getName())) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The player has already been invited to join your clan!");
            return;
        }


        if(user.getClan().getState() == Clan.CLOSE) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You can't send the player an invitation because your clan is closed");
            return;
        }

        inviteUser.addClanRequest(user.getClan(), true);
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "The player has been invited to join your clan");
    }

    @Override
    public String getDescription() {
        return "Invite a player in your clan";
    }
}
