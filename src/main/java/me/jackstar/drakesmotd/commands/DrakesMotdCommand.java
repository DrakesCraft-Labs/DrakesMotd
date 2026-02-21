package me.jackstar.drakesmotd.commands;

import me.jackstar.drakesmotd.MotdManager;
import me.jackstar.drakesmotd.MotdState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DrakesMotdCommand implements CommandExecutor {

    private final MotdManager motdManager;

    public DrakesMotdCommand(MotdManager motdManager) {
        this.motdManager = motdManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!sender.hasPermission("drakesmotd.admin")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if (args.length == 0) {
            usage(sender, label);
            return true;
        }

        if ("reload".equalsIgnoreCase(args[0])) {
            motdManager.reload();
            sender.sendMessage(ChatColor.GREEN + "DrakesMotd reloaded.");
            return true;
        }

        if ("state".equalsIgnoreCase(args[0])) {
            if (args.length == 1 || "show".equalsIgnoreCase(args[1])) {
                sender.sendMessage(ChatColor.YELLOW + "Current MOTD state: " + ChatColor.WHITE
                        + motdManager.getCurrentState().name());
                return true;
            }

            MotdState target = parseState(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Invalid state. Valid: " + validStates());
                return true;
            }

            boolean persisted = motdManager.setCurrentState(target, true);
            if (!persisted) {
                sender.sendMessage(ChatColor.RED + "State changed in memory but could not persist to motd.yml.");
            } else {
                sender.sendMessage(ChatColor.GREEN + "MOTD state changed to " + ChatColor.WHITE + target.name()
                        + ChatColor.GREEN + ".");
            }
            return true;
        }

        usage(sender, label);
        return true;
    }

    private MotdState parseState(String raw) {
        if (raw == null) {
            return null;
        }
        try {
            return MotdState.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private String validStates() {
        return Arrays.stream(MotdState.values()).map(Enum::name).collect(Collectors.joining(", "));
    }

    private void usage(CommandSender sender, String label) {
        sender.sendMessage(ChatColor.YELLOW + "/" + label + " reload");
        sender.sendMessage(ChatColor.YELLOW + "/" + label + " state <LIVE|BETA|MAINTENANCE>");
        sender.sendMessage(ChatColor.YELLOW + "/" + label + " state show");
    }
}
