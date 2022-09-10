package de.jann3107.mute.listener;

import de.jann3107.mute.Mute;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerPlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent e) {
        if(e.getPlayer().hasPermission("mute.bypass")) return;
        if(Mute.instance.muteManager.isMuted(e.getPlayer().getUniqueId().toString())) {
            e.getPlayer().sendMessage("§cDu bist gemutet!");
            e.setCancelled(true);
            return;
        }
        String[] words = ((TextComponent) e.originalMessage()).content().split(" ");
        for(String word : words) {
            if(Mute.instance.blackListWordManager.isBlackListed(word)) {
                e.getPlayer().sendMessage("§cDu darfst keine Beleidigungen schreiben!");
                e.setCancelled(true);
                return;
            }
        }
    }
}
