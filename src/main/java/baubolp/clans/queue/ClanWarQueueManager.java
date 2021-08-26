package baubolp.clans.queue;


import baubolp.clans.Clans;
import baubolp.clans.player.User;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClanWarQueueManager {

    public List<Subscriber> queue;
    public List<Subscriber> funQueue;
    public int queueTick;

    public ClanWarQueueManager() {
        this.queue = new ArrayList<>();
        this.funQueue = new ArrayList<>();
        this.queueTick = 0;
    }

    public List<Subscriber> getQueue() {
        return queue;
    }

    public List<Subscriber> getFunQueue() {
        return funQueue;
    }

    public void findMatch() {
        this.queueTick++;
        List<Subscriber> opponents = new ArrayList<>();
        if(this.queue.size() >= 2) {
            for(Subscriber subscriber : new ArrayList<>(this.queue)) {
                if(opponents.size() == 2) break;
                opponents.add(subscriber);
                this.queue.remove(subscriber);
            }
            
            if(opponents.size() == 2) {
                boolean ABORT = false;
                for (Subscriber subscriber : opponents) {
                    for (String playerName : subscriber.getSubscribers()) {
                        User user = Clans.getUserManager().getUser(playerName);
                        if(!user.isOnline()) {
                            ABORT = true;
                            break;
                        }
                        user.getPlayer().sendMessage(Clans.PREFIX + Color.GREEN + "Match found! Prepare Match-Server...");
                    }
                    if(ABORT) {
                        subscriber.removeFromQueue(true);
                    }
                }

                //TODO: INSERT INTO QUEUE DATABASE + Start Server
            }
        }else {
            if(this.queueTick % 30 == 0) {
                for(Subscriber subscriber : this.queue) {
                    for(String playerName : subscriber.getSubscribers()) {
                        User user = Clans.getUserManager().getUser(playerName);
                        if(!user.isOnline()) {
                            subscriber.removeFromQueue(true);
                            break;
                        }

                        user.getPlayer().sendMessage(Clans.PREFIX + Color.GREEN + "Searching opponent...");
                    }
                }
            }
        }
    }

    public void addToEloQueue(Subscriber subscriber) {
        if(this.queue.contains(subscriber)) return;
        this.queue.add(subscriber);
    }

    public void addToFunQueue(Subscriber subscriber) {
        if(this.queue.contains(subscriber)) return;
        this.funQueue.add(subscriber);
    }

    public void removeFromEloQueue(Subscriber subscriber) {
        if(!this.queue.contains(subscriber)) return;
        this.queue.remove(subscriber);
    }

    public void removeFromFunQueue(Subscriber subscriber) {
        if(!this.queue.contains(subscriber)) return;
        this.funQueue.remove(subscriber);
    }
}