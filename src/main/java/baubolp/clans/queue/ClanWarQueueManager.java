package baubolp.clans.queue;


import BauboLP.CloudBridge.CloudBridge;
import BauboLP.Communication.Packets.CreateServerPacket;
import baubolp.clans.Clans;
import baubolp.clans.player.User;
import baubolp.clans.util.MySQL;
import dev.waterdog.waterdogpe.logger.Color;

import java.util.ArrayList;
import java.util.List;

public class ClanWarQueueManager {

    public List<Subscriber> queue;
    public List<Subscriber> funQueue;
    public int queueTick;
    public boolean join = true;

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
                String playerList = "";
                boolean ABORT = false;
                for (Subscriber subscriber : opponents) {
                    if (!playerList.isEmpty()) playerList += ":";

                    playerList += String.join(":", subscriber.getSubscribers());
                    for (String playerName : subscriber.getSubscribers()) {
                        User user = Clans.getUserManager().getUser(playerName);
                        if (!user.isOnline()) {
                            ABORT = true;
                            break;
                        }
                        user.getPlayer().sendMessage(Clans.PREFIX + Color.GREEN + "Match found! Prepare Match-Server...");
                        user.getPlayer().sendTitle("§aMatch found!", "§7Are you ready?");
                    }

                    subscriber.removeFromQueue(ABORT);
                }

                if(ABORT) {
                    for (Subscriber subscriber : opponents) {
                        for (String playerName : subscriber.getSubscribers()) {
                            User user = Clans.getUserManager().getUser(playerName);
                            if (!user.isOnline()) continue;

                            user.getPlayer().sendMessage(Clans.PREFIX + Color.RED + "Please rejoin the queue. One of the clans does not have enough fighters...");
                        }
                    }
                    return;
                }

                CreateServerPacket createServerPacket = new CreateServerPacket();
                createServerPacket.addData("groupName", "EloCWBW");
                createServerPacket.addData("count", "1");

                String finalPlayerList = playerList;
                MySQL.createAsync(current -> {
                    current.execute("INSERT INTO `Queue`(`players`, `type`) VALUES ('" + finalPlayerList + "', 'elo')");
                }, e -> Clans.getInstance().getProxy().getLogger().info("Connection to clan database failed!"), null);
                CloudBridge.getCloud().sendPacket(createServerPacket);
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
