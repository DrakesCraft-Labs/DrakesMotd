package me.jackstar.drakesmotd;

import me.jackstar.drakesmotd.commands.DrakesMotdCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DrakesMotdPlugin extends JavaPlugin {

    private static final String[] DRAGON_BANNER = {
            "              / \\  //\\",
            "      |\\___/|      /   \\//  \\\\",
            "      /O  O  \\__  /    //  | \\ \\",
            "     /     /  \\/_/    //   |  \\  \\",
            "     \\_^_\\'/   \\/_   //    |   \\   \\"
    };

    private MotdManager motdManager;

    @Override
    public void onEnable() {
        logDragonBanner("DrakesMotd");
        logLoading("Saving default motd config");
        File motdFile = new File(getDataFolder(), "motd.yml");
        if (!motdFile.exists() && getResource("motd.yml") != null) {
            saveResource("motd.yml", false);
        }
        logLoading("Initializing MOTD manager");
        motdManager = new MotdManager(this);
        logLoading("Registering ping listener");
        getServer().getPluginManager().registerEvents(motdManager, this);
        PluginCommand motdCommand = getCommand("drakesmotd");
        if (motdCommand != null) {
            motdCommand.setExecutor(new DrakesMotdCommand(motdManager));
        } else {
            getLogger().warning("Command 'drakesmotd' not found in plugin.yml.");
        }
        getLogger().info("[Ready] DrakesMotd enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("[Shutdown] DrakesMotd disabled.");
    }

    private void logLoading(String step) {
        getLogger().info("[Loading] " + step + "...");
    }

    private void logDragonBanner(String pluginName) {
        getLogger().info("========================================");
        getLogger().info(" " + pluginName + " - loading");
        for (String line : DRAGON_BANNER) {
            getLogger().info(line);
        }
        getLogger().info("========================================");
    }
}
