package baubolp.clans.command.subcommand.type.clan;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class ResetEloCommand extends SubCommand {

    public ResetEloCommand() {
        super("resetelo", true);
    }
    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        if(!sender.hasPermission("clan.admin")) return;

        if(args.length < 2) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "/clan resetelo <Clan>");
            return;
        }

        int i = 0;
        if(args[1].equals("*")) {
            for (Clan clan : Clans.getClanManager().getClans()) {
                if(clan != null) {
                    i++;
                    clan.updateElo(1000, true);
                    sender.sendMessage(Clans.PREFIX + Color.GREEN + "Elo of " + Color.YELLOW + clan.getName() + Color.GREEN + " reset to " + Color.YELLOW + "1000 Elo");
                }
            }

            sender.sendMessage(Clans.PREFIX + Color.GREEN + "Elo of " + Color.YELLOW + i + " Clans" + Color.GREEN + " reset!");
            return;
        }

        Clan clan = Clans.getClanManager().getClan(args[1]);
        if(clan == null) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The clan aren't valid!");
            return;
        }

        clan.updateElo(1000, true);
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "Elo of " + Color.YELLOW + clan.getName() + Color.GREEN + " reset to " + Color.YELLOW + "1000 Elo");
    }
}
