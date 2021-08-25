package baubolp.clans.clan;

import baubolp.clans.Clans;
import baubolp.clans.player.User;
import baubolp.clans.util.MySQL;

import java.util.Map;

public class Clan {

    public static final int OPEN = 2;
    public static final int INVITE = 1;
    public static final int CLOSE = 0;

    public String name;
    public String tag;
    public String clan_owner;
    public String color;
    public String createdDate;
    public String message;
    public int state;
    public int elo;

    public Clan(String name, String tag, String clan_owner, String color, String created, String message, int state, int elo) {
        this.name = name;
        this.tag = tag;
        this.clan_owner = clan_owner;
        this.color = color;
        this.elo = elo;
        this.createdDate = created;
        this.state = state;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public int getElo() {
        return elo;
    }

    public String getClanOwnerName() {
        return clan_owner;
    }

    public User getOwnerUser() {
        User user = Clans.getUserManager().getUser(getClanOwnerName());
        if (!user.isOnline()) return null;
        return user;
    }

    public String getColor() {
        return color;
    }

    public String getDisplayMessage() {
        return message;
    }

    public void setDisplayMessage(String message) {
        String finalMessage = message.replace("setmessage ", "");
        this.message = finalMessage;
        MySQL.createAsync(mySQL -> {
            mySQL.execute("UPDATE `Clans` SET message=' + " + finalMessage + "' WHERE clan_name='" + this.name + "'");
        }, e -> Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!"), null);
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        MySQL.createAsync(mySQL -> {
            mySQL.execute("UPDATE `Clans` SET status=' + " + state + "' WHERE clan_name='" + this.name + "'");
        }, e -> Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!"), null);
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public void setClanOwner(String owner) {
        this.clan_owner = owner;
    }

    public void setColor(String color) {
        this.color = color;
        MySQL.createAsync(mySQL -> {
            mySQL.execute("UPDATE `Clans` SET color=' + " + color + "' WHERE clan_name='" + this.name + "'");
        }, e -> Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!"), null);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void create() {
        MySQL.createAsync(mySQL -> {
            mySQL.execute("INSERT INTO `Clans`(`clan_name`, `clan_tag`, `clan_owner`) VALUES ('" + this.name + "','" + this.tag + "','" + this.clan_owner + "')");
            mySQL.execute("UPDATE `ClanUsers` SET role='Leader' WHERE playername='" + this.clan_owner + "'");
            mySQL.execute("UPDATE `ClanUsers` SET clan_name='" + this.name + "' WHERE playername='" + this.clan_owner + "'");
        }, e -> Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!"), null);
        ClanManager clanManager = Clans.getClanManager();
        clanManager.addClan(this);
    }

    public void delete() {
        MySQL.createAsync(mySQL -> {
            mySQL.execute("DELETE FROM `Clans` WHERE clan_name='" + this.name + "'");

            for(User user : Clans.getUserManager().getUserHashMap().values()) {
                if(!user.isInClan()) continue;
                mySQL.execute("UPDATE `ClanUsers` SET clan_name='' WHERE playername='" + user.getPlayer().getName() + "'");
                mySQL.execute("UPDATE `ClanUsers` SET role='Member' WHERE playername='" + user.getPlayer().getName() + "'");
                user.leaveClan(false);
                user.setRole("Member", false);
            }
        }, e -> Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!"), null);
        ClanManager clanManager = Clans.getClanManager();
        clanManager.removeClan(this);
    }

    public void sendMessage(String message) {
        for (Map.Entry entry : Clans.getUserManager().getUserHashMap().entrySet()) {
            User user = (User) entry.getValue();
            if (user.isInClan()) {
                if (user.isOnline()) {
                    if (user.getClan().getName().equals(this.getName())) {
                        user.getPlayer().sendMessage(message);
                    }
                }
            }
        }
    }
}