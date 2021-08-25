package baubolp.clans.role;

import java.util.HashMap;
import java.util.Map;

public class RoleManager {

    public HashMap<String, Role> roleHashMap;
    public int LEADER_PRIORITY = 0;

    public RoleManager() {
        this.roleHashMap = new HashMap<>();
    }

    public HashMap<String, Role> getRoleHashMap() {
        return roleHashMap;
    }

    public boolean existRole(String roleName) {
        return this.roleHashMap.containsKey(roleName);
    }

    public Role getRole(String name) {
        return this.roleHashMap.get(name);
    }

    public void addRole(Role role) {
        this.roleHashMap.put(role.getName(), role);
    }

    public void removeRole(Role role) {
        this.removeRole(role.getName());
    }

    public void removeRole(String roleName) {
        this.roleHashMap.remove(roleName);
    }

    public int getLeaderPriority() {
        if(this.LEADER_PRIORITY == 0) {
            for(Map.Entry entry : this.getRoleHashMap().entrySet()) {
                Role role = (Role) entry.getValue();
                if(role.getPriority() > this.LEADER_PRIORITY)
                    this.LEADER_PRIORITY = role.priority;
            }
        }

        return this.LEADER_PRIORITY;
    }
}
