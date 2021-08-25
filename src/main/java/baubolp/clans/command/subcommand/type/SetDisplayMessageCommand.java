package baubolp.clans.command.subcommand.type;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class SetDisplayMessageCommand extends SubCommand {

    public SetDisplayMessageCommand() {
        super("setmessage");
    }

    @Override
    public String getDescription() {
        return "Set the message for your clan info";
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

        if(!user.hasPermission("displayMessage.update")) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your role can't do that!");
            return;
        }

        if(args.length < 2) {
            sender.sendMessage(Clans.PREFIX + Color.GREEN + "You have reset your clan information");
            user.getClan().setDisplayMessage("");
            return;
        }

        String message = String.join(" ", args) + Color.RESET;
        if(message.length() > 30) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your message is too big!");
            return;
        }

        user.getClan().setDisplayMessage(message);
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "You have updated your clan information");
    }
}
