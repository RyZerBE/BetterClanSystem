package baubolp.clans.clan;

import baubolp.clans.Clans;
import baubolp.clans.util.MySQL;

public class Clan {

    public String name;
    public String tag;
    public String clan_owner;
    public String color;
    public String createdDate;
    public int elo;

    public Clan(String name, String tag, String clan_owner, String color, String created, int elo) {
        this.name = name;
        this.tag = tag;
        this.clan_owner = clan_owner;
        this.color = color;
        this.elo = elo;
        this.createdDate = created;
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

    public String getColor() {
        return color;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public void setClanOwner(String owner) {
        this.clan_owner = owner;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void create() {
        MySQL.createAsync(mySQL -> {
            //todo: fix mysql query
            mySQL.execute("INSERT INTO `Clans`(`clan_name`, `clan_tag´, `clan_owner`) VALUES ('" + this.name + "','" + this.tag + "','" + this.clan_owner + "')");
        }, e -> Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!"), null);
        ClanManager clanManager = Clans.getClanManager();
        clanManager.addClan(this);
    }
}
