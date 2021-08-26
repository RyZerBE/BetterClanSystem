package baubolp.clans.command.subcommand.type.queue;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import baubolp.clans.queue.Subscriber;
import baubolp.clans.role.RoleManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinQueueCommand extends SubCommand {

    public JoinQueueCommand() {
        super("join");
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

        if(!user.hasPermission("queue.join")) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your role can't do that!");
            return;
        }

        if(args.length != 3) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Please use: " + Color.YELLOW + "/queue join Player1,Player2,Player3,Player4 <FUN|ELO>");
            return;
        }

        String type = args[2];
        List<String> players = new ArrayList<>(Arrays.asList(args[1].split(",")));
        Clan clan = user.getClan();

        if(players.size() != 4) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You need 4 players from your clan to join the ClanWar Queue!");
            return;
        }

        boolean STOP = false;
        for(String playerName : players) {
            User clanMember = Clans.getUserManager().getUser(playerName);
            if(!clanMember.isOnline()) {
                STOP = true;
                break;
            }
        }

        if(STOP) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The specified players must be online to join in a ClanWar!");
            return;
        }

        if(clan.joinedQueue()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your clan already joined the queue!");
            return;
        }

        if(!type.equalsIgnoreCase("ELO") && !type.equalsIgnoreCase("FUN")) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Unknown Type.");
            sender.sendMessage(Clans.PREFIX + Color.GRAY + "Types: " + Color.DARK_GREEN + "FUN " + Color.GRAY + "OR " + Color.AQUA + "ELO");
            return;
        }

        boolean fun = true;
        if(type.equalsIgnoreCase("ELO")) fun = false;

        Subscriber subscriber = new Subscriber(clan.getName(), players, fun);
        clan.joinQueue(subscriber);
        subscriber.addToQueue();
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "Your clan joined the ClanWar queue");
    }
}