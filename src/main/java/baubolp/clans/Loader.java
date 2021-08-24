package baubolp.clans;

import baubolp.clans.clan.Clan;
import baubolp.clans.util.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Loader {

    public static void onStartup() {
        MySQL.createAsync(mySQL -> {
            ResultSet result = mySQL.executeQuery("SELECT * FROM Clans");
            try {
                while (result.next()) {
                    String clanName = result.getString("clan_name");
                    String clanTag = result.getString("clan_tag");
                    String clanOwner = result.getString("clan_owner");
                    String color = result.getString("color");
                    String created = result.getString("created");
                    int elo = result.getInt("elo");

                    Clan clan = new Clan(clanName, clanTag, clanOwner, color, created, elo);
                    Clans.getClanManager().addClan(clan);
                    System.out.println(clanName);
                }
                mySQL.closeSet(result);
            }catch (SQLException e) {e.printStackTrace();}
        }, e -> Clans.getInstance().getProxy().getLogger().error("Connection to clan database failed!"), null);
    }

    public static void onShutdown() {

    }
}
