package de.jann3107.mute.commands;

import de.jann3107.mute.Mute;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandMute implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("mute.mute")) {
            if (args.length == 2) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                String time = args[1];
                switch (time){
                    case "10min":
                        Mute.instance.muteManager.mutePlayer(target.getUniqueId().toString(), String.valueOf(System.currentTimeMillis() + (10 * 60 * 1000)));
                        break;
                    case "1h":
                        Mute.instance.muteManager.mutePlayerForHours(target.getUniqueId().toString(), 1);
                        break;
                    case "1d":
                        Mute.instance.muteManager.mutePlayerForHours(target.getUniqueId().toString(), 24);
                        break;
                    case "1w":
                        Mute.instance.muteManager.mutePlayerForHours(target.getUniqueId().toString(), 24 * 7);
                        break;
                    case "1m":
                        Mute.instance.muteManager.mutePlayerForHours(target.getUniqueId().toString(), 24 * 30);
                        break;
                    case "1y":
                        Mute.instance.muteManager.mutePlayerForHours(target.getUniqueId().toString(), 24 * 365);
                        break;
                    default:
                        sender.sendMessage("§cInvalid time! Bitte nutze 10min, 1h, 1d, 1w, 1m oder 1y");
                        break;
                }
                if (target != null) {
                    sender.sendMessage("§aDer Spieler " + target.getName() + " wurde gemutet!");
                } else {
                    sender.sendMessage("§cDer Spieler " + args[0] + " wurde nicht gefunden!");
                }
            } else {
                sender.sendMessage("§c/mute <Spieler> <Zeit>");
            }
        } else {
            sender.sendMessage("§cDazu hast du keine Rechte!");
        }
        return true;
    }
}
