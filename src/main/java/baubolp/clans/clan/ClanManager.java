package baubolp.clans.clan;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ClanManager {

    public Map<String, Clan> clanHashMap;

    public ClanManager() {
        this.clanHashMap = new TreeMap<String, Clan>(String.CASE_INSENSITIVE_ORDER);
    }

    public void addClan(Clan clan) {
        this.clanHashMap.put(clan.getName(), clan);
    }

    public void removeClan(Clan clan) {
        this.removeClan(clan.getName());
    }

    public void removeClan(String name) {
        this.clanHashMap.remove(name);
    }

    public boolean alreadyExist(String clanName){
        return this.clanHashMap.get(clanName) != null;
    }

    public Clan getClan(String clanName) {
        return this.clanHashMap.get(clanName);
    }

    public ArrayList<Clan> getClans() {
        ArrayList<Clan> clans = new ArrayList<>();
        for(Map.Entry entry : clanHashMap.entrySet()) {
            clans.add((Clan) entry.getValue());
        }

        return clans;
    }

    public boolean tagAlreadyExist(String clanTag) {
        for(Map.Entry entry : clanHashMap.entrySet()) {
            Clan clan = (Clan) entry.getValue();
            if(clan.getTag().equalsIgnoreCase(clanTag)) return true;
        }
        return false;
    }
}
