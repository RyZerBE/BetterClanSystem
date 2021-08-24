package baubolp.clans.command.subcommand.type;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.clan.ClanManager;
import baubolp.clans.command.subcommand.SubCommand;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateCommand extends SubCommand {

    public CreateCommand() {
        super("create");
    }

    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        if(args.length != 3) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Please use: " + Color.YELLOW + "/clan create <ClanName> <ClanTag>");
            return;
        }

        String clanName = args[1];
        String clanTag = args[2];

        ClanManager clanManager = Clans.getClanManager();

        if(clanManager.alreadyExist(clanName)) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The clan " + Color.YELLOW + clanName + Color.RED + " already exists!");
            return;
        }

        if(clanManager.tagAlreadyExist(clanTag)) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The clantag " + Color.YELLOW + clanTag + Color.RED + " is already in use!");
            return;
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.y HH:mm");
        String date = dateFormat.format(new Date());
        Clan clan = new Clan(clanName, clanTag, sender.getName(), "§e", date, 1000);
        clan.create();
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "Your clan " + Color.YELLOW + clanName + Color.GREEN + " were created!");
    }

    @Override
    public String getDescription() {
        return "Create your own clan";
    }
}
