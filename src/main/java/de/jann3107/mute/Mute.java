package de.jann3107.mute;

import de.jann3107.mute.commands.CommandMute;
import de.jann3107.mute.commands.CommandMuteInfo;
import de.jann3107.mute.commands.CommandUnmute;
import de.jann3107.mute.listener.ListenerPlayerChat;
import de.jann3107.mute.utils.BlackListWordManager;
import de.jann3107.mute.utils.MuteManager;
import de.jann3107.mute.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Mute extends JavaPlugin {

    public static Mute instance;
    public MySQL mysql;
    public MuteManager muteManager;
    public BlackListWordManager blackListWordManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();
        mysql = new MySQL(getConfig().getString("mysql.host", "localhost"), getConfig().getString("mysql.database", "mute"), getConfig().getString("mysql.user", "mutepl"), getConfig().getString("mysql.password", "password123"), getConfig().getInt("mysql.port", 3306));
        mysql.connect();
        mysql.init();
        muteManager = new MuteManager();
        blackListWordManager = new BlackListWordManager();
        Bukkit.getPluginManager().registerEvents(new ListenerPlayerChat(), this);
        getCommand("mute").setExecutor(new CommandMute());
        getCommand("unmute").setExecutor(new CommandUnmute());
        getCommand("muteinfo").setExecutor(new CommandMuteInfo());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
