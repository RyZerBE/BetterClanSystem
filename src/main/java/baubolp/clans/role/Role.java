package baubolp.clans.role;

import java.util.List;

public class Role {

    public String name;
    public int priority;
    public List<String> permissions;

    public Role(String name, int priority, List<String> permissions) {
        this.name = name;
        this.priority = priority;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }
    public int getPriority() {
        return priority;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String permission) {
        return this.permissions.contains(permission);
    }
}
