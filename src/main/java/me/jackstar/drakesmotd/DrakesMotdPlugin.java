package me.jackstar.drakesmotd;

import org.bukkit.plugin.java.JavaPlugin;

public class DrakesMotdPlugin extends JavaPlugin {

    private MotdManager motdManager;

    @Override
    public void onEnable() {
        if (getResource("motd.yml") != null) {
            saveResource("motd.yml", false);
        }
        motdManager = new MotdManager(this);
        getServer().getPluginManager().registerEvents(motdManager, this);
    }
}
