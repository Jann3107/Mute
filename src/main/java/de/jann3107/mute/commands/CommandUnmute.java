package de.jann3107.mute.commands;

import de.jann3107.mute.Mute;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandUnmute implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("mute.unmute")) {
            if (args.length == 1) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                if (player != null) {
                    Mute.instance.muteManager.unmutePlayer(player.getUniqueId().toString());
                    sender.sendMessage("§aDer Spieler " + player.getName() + " wurde entmutet!");
                } else {
                    sender.sendMessage("§cDer Spieler " + args[0] + " wurde nicht gefunden!");
                }
            } else {
                sender.sendMessage("§c/unmute <Spieler>");
            }
        } else {
            sender.sendMessage("§cDazu hast du keine Rechte!");
        }
        return true;
    }
}
