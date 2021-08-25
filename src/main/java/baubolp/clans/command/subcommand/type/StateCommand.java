package baubolp.clans.command.subcommand.type;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class StateCommand extends SubCommand {

    public StateCommand() {
        super("state");
    }

    @Override
    public String getDescription() {
        return "Change the opening status of your clan";
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

        if(user.hasPermission("state.update")) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your role can't do that!");
            return;
        }

        if(args.length != 2) {
            sender.sendMessage(Clans.PREFIX + Color.GREEN + "OPEN " + Color.GOLD + "INVITE " + Color.RED + " CLOSE");
            return;
        }

        String state = args[1];
        if(state.equalsIgnoreCase("OPEN") || state.equalsIgnoreCase("INVITE") || state.equalsIgnoreCase("CLOSE")) {
            sender.sendMessage(Clans.PREFIX + Color.GREEN + "You have changed your status to " + Color.YELLOW + state.toUpperCase());
            switch (state.toUpperCase()) {
                case "OPEN":
                    user.getClan().setState(Clan.OPEN);
                    break;
                case "INVITE":
                    user.getClan().setState(Clan.INVITE);
                    break;
                case "CLOSE":
                    user.getClan().setState(Clan.CLOSE);
                    break;
            }
        }else {
            sender.sendMessage(Clans.PREFIX + Color.GREEN + "OPEN " + Color.GOLD + "INVITE " + Color.RED + " CLOSE");
        }
    }
}
