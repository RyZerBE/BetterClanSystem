package baubolp.clans.player;

import baubolp.clans.Clans;
import baubolp.clans.role.Role;
import baubolp.clans.role.RoleManager;
import baubolp.clans.util.MySQL;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.util.HashMap;

public class UserManager {

    public HashMap<String, User> userHashMap;

    public UserManager() {
        this.userHashMap = new HashMap<>();
    }

    public HashMap<String, User> getUserHashMap() {
        return userHashMap;
    }

    public User getUser(String playerName) {
        return this.userHashMap.get(playerName);
    }

    public void addUser(User user) {
        this.userHashMap.put(user.getPlayer().getName(), user);
    }

    public void addUser(String playerName, User user) {
        this.userHashMap.put(playerName, user);
    }

    public void removeUser(User user) {
        this.removeUser(user.getPlayer().getName());
    }

    public void removeUser(String playerName) {
        this.userHashMap.remove(playerName);
    }

    public boolean isRegistered(String playerName) {
        return this.userHashMap.containsKey(playerName);
    }

    public void register(ProxiedPlayer player) {
        User user = new User(player.getName(), null, Clans.getRoleManager().getRole("Member"));
        MySQL.createAsync(mySQL -> {
            mySQL.execute("INSERT INTO `ClanUsers`(`playername`) VALUES ('" + player.getName() + "')");
        }, e -> Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!"), null);
        this.addUser(user);
    }
}
