package baubolp.clans;

import baubolp.clans.clan.ClanManager;
import baubolp.clans.command.ClanCommand;
import baubolp.clans.command.subcommand.SubCommandManager;
import dev.waterdog.waterdogpe.command.CommandMap;
import dev.waterdog.waterdogpe.plugin.Plugin;


public class Clans extends Plugin {

    public static String PREFIX;
    private static Clans instance;

    private SubCommandManager subCommandManager;
    private static ClanManager clanManager;

    @Override
    public void onEnable() {
        PREFIX = "§6§lClans §8| §r";
        instance = this;
        subCommandManager = new SubCommandManager(this);
        clanManager = new ClanManager();

        this.boot();
    }

    @Override
    public void onDisable() {
        Loader.onShutdown();
    }

    public void boot() {
        registerCommands();
        Loader.onStartup();
    }

    private void registerCommands() {
        CommandMap commandMap = this.getProxy().getCommandMap();
        commandMap.registerCommand(new ClanCommand());
    }

    public static Clans getInstance() {
        return instance;
    }

    public SubCommandManager getSubCommandManager() {
        return subCommandManager;
    }

    public static ClanManager getClanManager() {
        return clanManager;
    }

    public void runAsync(Runnable runnable) { this.getProxy().getScheduler().scheduleAsync(runnable); }
}
