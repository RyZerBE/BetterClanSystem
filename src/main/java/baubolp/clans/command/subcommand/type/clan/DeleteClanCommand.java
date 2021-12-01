package baubolp.clans.command.subcommand.type.clan;

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
        super("delete");
    }

    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        UserManager userManager = Clans.getUserManager();
        if(!userManager.isRegistered(sender.getName())) return;
        User user = userManager.getUser(sender.getName());

        if(args.length >= 2 && sender.hasPermission("clan.admin")) {
            Clan clan = Clans.getClanManager().getClan(args[1]);
            if(clan == null) {
                sender.sendMessage(Clans.PREFIX + Color.RED + "The clan aren't valid!");
                return;
            }

            if(args.length == 3) {
                if(args[2].equals("confirm")) {
                    clan.delete();
                    sender.sendMessage(Clans.PREFIX + Color.RED + "You deleted the clan " + Color.YELLOW + clan.getName() + Color.RED + " successfully!");
                }else {
                    sender.sendMessage(Clans.PREFIX + Color.RED + "Are you sure you want to delete the clan " + Color.YELLOW + clan.getName() + Color.RED + "?");
                    sender.sendMessage(Clans.PREFIX + Color.YELLOW + "Use /clan delete " + clan.getName() + " confirm are you sure");
                }
            }
            return;
        }

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
        sender.sendMessage(Clans.PREFIX + Color.RED + "You deleted your clan successfully!");
    }

    @Override
    public String getDescription() {
        return "Delete your clan";
    }
}
