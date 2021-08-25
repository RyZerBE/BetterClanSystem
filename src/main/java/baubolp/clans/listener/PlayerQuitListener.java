package baubolp.clans.listener;

import baubolp.clans.Clans;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

public class PlayerQuitListener {

    public void onQuit(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        UserManager userManager = Clans.getUserManager();
        if(userManager.isRegistered(player.getName())) return;

        User user = userManager.getUser(player.getName());
        user.offline();
    }

    public void register() {
        Clans.getInstance().getProxy().getEventManager().subscribe(PlayerDisconnectEvent.class, this::onQuit);
    }
}
