package baubolp.clans.clan;

import java.util.HashMap;
import java.util.Map;

public class ClanManager {

    public HashMap<String, Clan> clanHashMap;

    public ClanManager() {
        this.clanHashMap = new HashMap<>();
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
        return this.clanHashMap.containsKey(clanName);
    }

    public Clan getClan(String clanName) {
        return this.clanHashMap.get(clanName);
    }

    public boolean tagAlreadyExist(String clanTag) {
        for(Map.Entry entry : clanHashMap.entrySet()) {
            Clan clan = (Clan) entry.getValue();
            if(clan.getTag().equals(clanTag)) return true;
        }
        return false;
    }
}
