package baubolp.clans.scheduler;

import baubolp.clans.Clans;
import baubolp.clans.queue.ClanWarQueueManager;

public class CheckQueueTask {

    public void start() {
        Clans.getInstance().getProxy().getScheduler().scheduleRepeating(() -> Clans.getQueueManager().findMatch(), 20);
    }


}
