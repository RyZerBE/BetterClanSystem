package baubolp.clans.command.subcommand.type.clan;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.clan.ClanManager;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
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
        UserManager userManager = Clans.getUserManager();
        if(!userManager.isRegistered(sender.getName())) return;
        User user = userManager.getUser(sender.getName());

        if(user.isInClan()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You are already in the clan " + Color.YELLOW + user.getClan().getName());
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

        if(clanName.length() > 16) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your clan name is too big!");
            return;
        }

        if(clanTag.length() > 5) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your clan tag is too big!");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.y HH:mm");
        String date = dateFormat.format(new Date());
        Clan clan = new Clan(clanName.replace(" ", ""), clanTag, sender.getName(), "§e", date, "Only advertising for your clan is allowed!", Clan.INVITE, 1000);
        clan.create();
        user.setClan(clan, false);
        user.setRole("Leader", false);
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "Your clan " + Color.YELLOW + clanName + Color.GREEN + " were created!");
    }

    @Override
    public String getDescription() {
        return "Create your own clan";
    }
}
