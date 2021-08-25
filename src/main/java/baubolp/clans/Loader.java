package baubolp.clans;

import baubolp.clans.clan.Clan;
import baubolp.clans.listener.PlayerJoinListener;
import baubolp.clans.listener.PlayerQuitListener;
import baubolp.clans.player.User;
import baubolp.clans.role.Role;
import baubolp.clans.util.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Loader {

    public static void onStartup() {
        //CACHE ALL CLAN STUFF
        MySQL.createAsync(mySQL -> {
            ResultSet result = mySQL.executeQuery("SELECT * FROM Clans");
            ResultSet playerResult = mySQL.executeQuery("SELECT * FROM ClanUsers");
            ResultSet rolesResult = mySQL.executeQuery("SELECT * FROM ClanRoles");
            ResultSet requestsResult = mySQL.executeQuery("SELECT * FROM ClanRequests");
            //CLANS
            try {
                while (result.next()) {
                    String clanName = result.getString("clan_name");
                    String clanTag = result.getString("clan_tag");
                    String clanOwner = result.getString("clan_owner");
                    String color = result.getString("color");
                    String created = result.getString("created");
                    String displayMessage = result.getString("message");
                    int state = result.getInt("status");
                    int elo = result.getInt("elo");

                    Clan clan = new Clan(clanName, clanTag, clanOwner, color, created, displayMessage, state, elo);
                    Clans.getClanManager().addClan(clan);
                }
                mySQL.closeSet(result);
                //ROLES
                while(rolesResult.next()) {
                    String role_name = rolesResult.getString("role_name");
                    int priority = rolesResult.getInt("priority");
                    String permissions = rolesResult.getString("permissions");

                    Role role = new Role(role_name, priority, new ArrayList<>(Arrays.asList(permissions.split(":"))));
                    Clans.getRoleManager().addRole(role);
                }
                mySQL.closeSet(rolesResult);
                //PLAYERS
                while(playerResult.next()) {
                    String clan_name = playerResult.getString("clan_name");
                    String playerName = playerResult.getString("playername");
                    String role = playerResult.getString("role");

                    User user = new User(playerName, null, Clans.getRoleManager().getRole("Member"));
                    if(Clans.getClanManager().alreadyExist(clan_name) && !clan_name.equals("")) {
                        Clan clan = Clans.getClanManager().getClan(clan_name);
                        user.setClan(clan, false);
                    }

                    user.setRole(role, false);
                    Clans.getUserManager().addUser(playerName, user);
                }
                mySQL.closeSet(playerResult);
                //REQUESTS
                while(requestsResult.next()) {
                    String clan_name = requestsResult.getString("clan_name");
                    String playerName = requestsResult.getString("playername");

                    User user = Clans.getUserManager().getUser(playerName);
                    if(user == null) continue;
                    if(user.alreadySentRequest(clan_name)) continue;

                    user.requests.add(clan_name);
                }
                mySQL.closeSet(requestsResult);
            }catch (SQLException e) {e.printStackTrace();}
        }, e -> Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!"), null);

        //EVENTS
        new PlayerJoinListener().register();
        new PlayerQuitListener().register();
    }

    public static void onShutdown() {

    }
}
