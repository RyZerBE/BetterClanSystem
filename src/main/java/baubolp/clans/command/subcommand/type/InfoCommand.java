package baubolp.clans.command.subcommand.type;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.clan.ClanManager;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class InfoCommand extends SubCommand {

    public InfoCommand() {
        super("info");
    }

    @Override
    public String getDescription() {
        return "Get information about a clan";
    }

    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        UserManager userManager = Clans.getUserManager();
        ClanManager clanManager = Clans.getClanManager();
        if (!userManager.isRegistered(sender.getName())) return;
        User user = userManager.getUser(sender.getName());

        if ((!user.isInClan()) && args.length != 2) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Please use: " + Color.YELLOW + "/clan info <Name of Clan>");
            return;
        }

        String clanName = user.getClan().getName();
        if (args.length == 2)
            clanName = args[1];

        if (!clanManager.alreadyExist(clanName)) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The clan doesn't exist!");
            return;
        }

        Clan clan = clanManager.getClan(clanName);
        String clanTag = clan.getColor() + clan.getTag();
        String createdDate = clan.getCreatedDate();
        String displayMessage = clan.getDisplayMessage();
        String elo = clan.getElo() + " Elo";
        String owner = clan.getClanOwnerName();
        String state;
        switch (clan.getState()) {
            case Clan.OPEN:
                state = Color.GREEN + "OPEN";
                break;
            case Clan.INVITE:
                state = Color.GOLD + "ONLY INVITE";
                break;
            case Clan.CLOSE:
                state = Color.RED + "CLOSED";
                break;
            default:
                state = Color.DARK_RED + "???";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(Clans.PREFIX).append(Color.YELLOW).append("Information about ").append(clanName)
                .append("\n").append(Color.GOLD).append("ClanTag: ").append(clanTag)
                .append("\n").append(Color.GOLD).append("Owner: ").append(Color.YELLOW).append(owner)
                .append("\n").append(Color.GOLD).append("Created: ").append(Color.YELLOW).append(createdDate)
                .append("\n").append(Color.GOLD).append("Info: ").append(Color.YELLOW).append(displayMessage)
                .append("\n").append(Color.GOLD).append("Elo: ").append(Color.YELLOW).append(elo)
                .append("\n").append(Color.GOLD).append("State: ").append(Color.YELLOW).append(state)
                .append("\n").append(Color.GOLD).append("Members of the clan:");

        for(User clanMember : Clans.getUserManager().getUserHashMap().values()) {
            String onlineState = Color.DARK_GRAY + " (" + (clanMember.isOnline() ? Color.GREEN + "ONLINE" : Color.RED + "OFFLINE") + Color.DARK_GRAY + ")";
            builder.append(Color.YELLOW).append(clanMember.getPlayerName()).append(Color.GOLD).append(user.getRole().getName()).append(onlineState);
        }

        sender.sendMessage(builder.toString());
    }
}
