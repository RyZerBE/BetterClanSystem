package baubolp.clans.player;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.role.Role;
import baubolp.clans.role.RoleManager;
import baubolp.clans.util.MySQL;
import dev.waterdog.waterdogpe.logger.Color;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

public class User {

    public ProxiedPlayer player;
    public String playerName;
    public Clan clan;
    public Role role;

    public User(String playerName, Clan clan, Role role) {
        this.player = Clans.getInstance().getProxy().getPlayer(playerName);
        this.clan = clan;
        this.role = role;
        this.playerName = playerName;
    }

    public boolean isInClan() {
        return this.getClan() != null;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Clan getClan() {
        return clan;
    }

    public Role getRole() {
        return role;
    }

    public boolean isOnline() {
        return this.getPlayer() != null;
    }

    public boolean isLeader() {
        return this.role.getPriority() == Clans.getRoleManager().getLeaderPriority();
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }

    public void online(ProxiedPlayer player) {
        this.player = player;
        if (this.isInClan()) {
            this.getClan().sendMessage(Clans.PREFIX + Color.YELLOW + player.getName() + Color.GREEN + " is now online!");
        }
    }

    public void offline() {
        if (this.isInClan()) {
            this.getClan().sendMessage(Clans.PREFIX + Color.YELLOW + player.getName() + Color.RED + " is now offline!");
        }
        this.player = null;
    }

    public void setRole(Role role, boolean mysql) {
        this.setRole(role.getName(), mysql);
    }

    public void setRole(String roleName, boolean mysql) {
        RoleManager roleManager = Clans.getRoleManager();
        if(!roleManager.existRole(roleName)) return;
        this.role = roleManager.getRole(roleName);

        if (mysql) {
            String playerName = this.getPlayer().getName();
            MySQL.createAsync(mySQL -> {
                mySQL.execute("UPDATE `ClanUsers` SET role='" + roleName + "' WHERE playername='" + playerName + "'");
            }, e -> {
                Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!");
                if (this.isOnline())
                    this.getPlayer().sendMessage(Clans.PREFIX + Color.RED + "Connection to clan database failed!");
            }, null);
        }
    }

    public void leaveClan(boolean mysql) {
        Clan oldClan = this.clan;
        String playerName = this.getPlayer().getName();
        if (oldClan != null) {
            oldClan.sendMessage(Clans.PREFIX + Color.YELLOW + playerName + Color.RED + " left the clan");
        }
        this.clan = null;

        if (mysql) {
            MySQL.createAsync(mySQL -> {
                mySQL.execute("UPDATE `ClanUsers` SET clan_name='' WHERE playername='" + playerName + "'");
            }, e -> {
                Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!");
                if (this.isOnline())
                    this.getPlayer().sendMessage(Clans.PREFIX + Color.RED + "Connection to clan database failed!");
            }, null);
        }
    }

    public void setClan(Clan clan, boolean mysql) {
        String clanName = clan.getName();
        this.clan = clan;
        if (mysql) {
            String playerName = this.getPlayer().getName();
            clan.sendMessage(Clans.PREFIX + Color.YELLOW + playerName + Color.GREEN + " joined the clan!");
            MySQL.createAsync(mySQL -> {
                mySQL.execute("UPDATE `ClanUsers` SET clan_name='" + clanName + "' WHERE playername='" + playerName + "'");
            }, e -> {
                Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!");
                if (this.isOnline())
                    this.getPlayer().sendMessage(Clans.PREFIX + Color.RED + "Connection to clan database failed!");
            }, null);
        }
    }

    public boolean hasPermission(String permission) {
        if(this.role == null) return true;

        return this.role.hasPermission(permission) || this.role.getPriority() == Clans.getRoleManager().LEADER_PRIORITY;
    }
}
