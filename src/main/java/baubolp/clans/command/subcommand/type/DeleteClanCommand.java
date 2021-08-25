package baubolp.clans.command.subcommand.type;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class DeleteClanCommand extends SubCommand {

    public DeleteClanCommand() {
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

        if(!user.isLeader()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The clan isn't yours!");
            return;
        }

        Clan clan = user.getClan();
        clan.delete();
        sender.sendMessage(Clans.PREFIX + Color.RED + "Your deleted your clan");
    }

    @Override
    public String getDescription() {
        return "Delete your clan";
    }
}
