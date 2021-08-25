package baubolp.clans.listener;

import baubolp.clans.Clans;
import baubolp.clans.player.User;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

public class PlayerJoinListener {

    public void onJoin(PlayerLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if(!Clans.getUserManager().isRegistered(player.getName())) {
            Clans.getUserManager().register(player);
        }

        User user = Clans.getUserManager().getUser(player.getName());
        user.online(player);
    }

    public void register() {
        Clans.getInstance().getProxy().getEventManager().subscribe(PlayerLoginEvent.class, this::onJoin);
    }
}
