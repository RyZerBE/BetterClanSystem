package baubolp.clans.command.subcommand.type.clan;

import baubolp.clans.Clans;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class ClanRequestCommand extends SubCommand {

    public ClanRequestCommand() {
        super("requests");
    }

    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        UserManager userManager = Clans.getUserManager();
        if(!userManager.isRegistered(sender.getName())) return;
        User user = userManager.getUser(sender.getName());

        if(user.getRequests().size() == 0) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You have no clan requests");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Clans.PREFIX).append(Color.GOLD).append("Your clan requests:");
        for(String clanName : user.getRequests()) {
            stringBuilder.append("\n").append(Color.YELLOW).append(clanName);
        }

        sender.sendMessage(stringBuilder.toString());
    }

    @Override
    public String getDescription() {
        return "See your open clan requests";
    }
}
