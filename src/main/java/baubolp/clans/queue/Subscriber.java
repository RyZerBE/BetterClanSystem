package baubolp.clans.queue;

import baubolp.clans.Clans;
import baubolp.clans.player.User;
import dev.waterdog.waterdogpe.logger.Color;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.util.List;

public class Subscriber {

    public String clanName;
    public List<String> subscribers;
    public boolean FUN;

    public Subscriber(String clanName, List<String> subscribers, boolean FUN) {
        this.clanName = clanName;
        this.subscribers = subscribers;
        this.FUN = FUN;
    }

    public List<String> getSubscribers() {
        return subscribers;
    }

    public String getClanName() {
        return clanName;
    }

    public boolean isFUN() {
        return FUN;
    }

    public void addToQueue() {
        String type;
        if(isFUN()) {
            type = Color.DARK_GREEN + "FUN";
            Clans.getQueueManager().addToFunQueue(this);
        } else {
            Clans.getQueueManager().addToEloQueue(this);
            type = Color.DARK_AQUA + "ELO";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(Clans.PREFIX).append(Color.GREEN).append("Your clan has joined the ClanWar queue:").append("\n")
                .append(Clans.PREFIX).append(Color.AQUA).append(String.join(", ", getSubscribers())).append("\n")
                .append(Clans.PREFIX).append(Color.GRAY).append("Type: ").append(type);

        for(String playerName : getSubscribers()) {
            User user = Clans.getUserManager().getUser(playerName);
            if(!user.isOnline()) continue;

            user.getPlayer().sendMessage(builder.toString());
        }
    }

    public void removeFromQueue(boolean LEAVE) {
        for(String playerName : getSubscribers()) {
            User user = Clans.getUserManager().getUser(playerName);
            if (!user.isOnline()) continue;

            if (LEAVE)
                user.getPlayer().sendMessage(Clans.PREFIX + Color.RED + "One participant has gone offline. Your clan has been removed from the queue!");
            else
                user.getPlayer().sendMessage(Clans.PREFIX + Color.RED + "Your clan has left the ClanWar queue.");
        }
        Clans.getQueueManager().removeFromEloQueue(this);
        Clans.getQueueManager().removeFromFunQueue(this);
    }
}
