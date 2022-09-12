package de.jann3107.mute.commands;

import de.jann3107.mute.Mute;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

public class CommandMuteInfo implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("mute.info")) {
            if(args.length == 0){
                if(Mute.instance.muteManager.isMuted(((Player) sender).getUniqueId().toString())){
                    String unmute = Mute.instance.muteManager.getTimestampWhenUnmuted(((Player) sender).getUniqueId().toString());
                    sender.sendMessage("§aDu bist bis " + unmute + " gemutet!");
                } else {
                    sender.sendMessage("§aDu bist nicht gemutet!");
                }
            } else {
                OfflinePlayer player = Mute.instance.getServer().getOfflinePlayer(args[0]);
                if(player != null){
                    if(Mute.instance.muteManager.isMuted(player.getUniqueId().toString())){
                        String unmute = Mute.instance.muteManager.getTimestampWhenUnmuted(player.getUniqueId().toString());
                        sender.sendMessage("§aDer Spieler " + player.getName() + " ist bis " + unmute + " gemutet!");
                    } else {
                        sender.sendMessage("§aDer Spieler " + player.getName() + " ist nicht gemutet!");
                    }
                } else {
                    sender.sendMessage("§cDer Spieler " + args[0] + " wurde nicht gefunden!");
                }
            }
        } else {
            sender.sendMessage("§cDazu hast du keine Rechte!");
        }
        return true;
    }
}
