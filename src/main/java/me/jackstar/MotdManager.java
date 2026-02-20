package me.jackstar.drakesmotd;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.CachedServerIcon;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MotdManager implements Listener {

    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private final JavaPlugin plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final Map<MotdState, List<String>> motdByState = new EnumMap<>(MotdState.class);
    private final Map<String, CachedServerIcon> iconCache = new java.util.concurrent.ConcurrentHashMap<>();
    private MotdState currentState = MotdState.LIVE;
    private String fallbackMotd = "<green>DrakesCore</green>";

    public MotdManager(JavaPlugin plugin) {
        this.plugin = plugin;
        saveDefaultConfigFile();
        reload();
    }

    public void reload() {
        org.bukkit.configuration.file.FileConfiguration config = loadConfig();
        fallbackMotd = config.getString("fallback-motd", fallbackMotd);
        currentState = parseState(config.getString("state", "LIVE"));

        for (MotdState state : MotdState.values()) {
            List<String> lines = config.getStringList("motd." + state.name().toLowerCase(Locale.ROOT));
            if (lines.isEmpty()) {
                lines = List.of(fallbackMotd);
            }
            motdByState.put(state, new ArrayList<>(lines));
        }
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        List<String> lines = motdByState.getOrDefault(currentState, List.of(fallbackMotd));
        String motdMini = String.join("\n", lines);
        Component motdComponent = miniMessage.deserialize(motdMini);
        event.setMotd(LEGACY_SERIALIZER.serialize(motdComponent));

        CachedServerIcon icon = resolveIconForState(currentState);
        if (icon != null) {
            event.setServerIcon(icon);
        }
    }

    private CachedServerIcon resolveIconForState(MotdState state) {
        String iconFileName = state.name().toLowerCase(Locale.ROOT) + ".png";
        CachedServerIcon stateIcon = loadIcon(iconFileName);
        if (stateIcon != null) {
            return stateIcon;
        }

        File iconDir = new File(plugin.getDataFolder(), "icons");
        File[] pngFiles = iconDir.listFiles((dir, name) -> name.toLowerCase(Locale.ROOT).endsWith(".png"));
        if (pngFiles == null || pngFiles.length == 0) {
            return null;
        }
        return loadIcon(pngFiles[0].getName());
    }

    private CachedServerIcon loadIcon(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return null;
        }
        CachedServerIcon cached = iconCache.get(fileName);
        if (cached != null) {
            return cached;
        }

        File iconDir = new File(plugin.getDataFolder(), "icons");
        if (!iconDir.exists() && !iconDir.mkdirs()) {
            return null;
        }

        File source = new File(iconDir, fileName);
        if (!source.exists()) {
            return null;
        }

        try {
            File iconToLoad = source;
            BufferedImage image = ImageIO.read(source);
            if (image == null) {
                return null;
            }
            if (image.getWidth() != 64 || image.getHeight() != 64) {
                BufferedImage resized = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = resized.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(image, 0, 0, 64, 64, null);
                g.dispose();

                File resizedFile = new File(iconDir, "resized_" + fileName);
                ImageIO.write(resized, "png", resizedFile);
                iconToLoad = resizedFile;
            }

            CachedServerIcon icon = Bukkit.loadServerIcon(iconToLoad);
            iconCache.put(fileName, icon);
            return icon;
        } catch (Exception ex) {
            plugin.getLogger().warning("Failed to load server icon '" + fileName + "': " + ex.getMessage());
            return null;
        }
    }

    private org.bukkit.configuration.file.FileConfiguration loadConfig() {
        File file = new File(plugin.getDataFolder(), "motd.yml");
        return org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
    }

    private void saveDefaultConfigFile() {
        if (plugin.getResource("motd.yml") != null) {
            plugin.saveResource("motd.yml", false);
        } else {
            File file = new File(plugin.getDataFolder(), "motd.yml");
            if (!file.exists()) {
                try {
                    if (!plugin.getDataFolder().exists()) {
                        plugin.getDataFolder().mkdirs();
                    }
                    file.createNewFile();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private MotdState parseState(String raw) {
        if (raw == null) {
            return MotdState.LIVE;
        }
        try {
            return MotdState.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            return MotdState.LIVE;
        }
    }
}
