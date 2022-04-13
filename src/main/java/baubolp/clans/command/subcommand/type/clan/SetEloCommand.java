package baubolp.clans.command.subcommand.type.clan;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.command.subcommand.SubCommand;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class SetEloCommand extends SubCommand {

    public SetEloCommand() {
        super("setelo", true);
    }
    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        if(!sender.hasPermission("clan.admin")) return;

        if(args.length < 3) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "/clan setelo <Clan> <Elo>");
            return;
        }

        Clan clan = Clans.getClanManager().getClan(args[1]);
        int elo = Integer.parseInt(args[2]);

        if(clan == null) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The clan aren't valid!");
            return;
        }

        clan.updateElo(elo, true);
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "Elo of " + Color.YELLOW + clan.getName() + Color.GREEN + " set to " + Color.YELLOW + elo + " Elo");
    }
}