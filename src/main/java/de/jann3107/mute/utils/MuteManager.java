package de.jann3107.mute.utils;

import de.jann3107.mute.Mute;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class MuteManager {
    public void mutePlayer(String uuid, String time) {
        incrementTimesMuted(uuid);
        notifyPlayer(uuid, time);
        uuid = uuid.replaceAll("-", "");
        try {
            Mute.instance.mysql.getConnection().prepareStatement("INSERT INTO mute (pluuid, mutetime) VALUES ('" + uuid + "', '" + time + "')").execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void notifyPlayer(String uuid, String time) {
        if(Mute.instance.getConfig().getBoolean("notify")){
            Timestamp timestamp = new Timestamp(Long.valueOf(time));
            Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getPlayer().sendMessage("§cDu wurdest bis " + timestamp + "gemutet!");
        }
    }
    public void mutePlayerForHours(String uuid, int hours) {
        mutePlayer(uuid, String.valueOf(System.currentTimeMillis() + (hours * 60 * 60 * 1000)));
    }
    public boolean isMuted(String uuid) {
        Long unmuted = getTimeMillisWhenUnmuted(uuid);
        return unmuted > System.currentTimeMillis();
    }
    public long getTimeMillisWhenUnmuted(String uuid) {
        uuid = uuid.replaceAll("-", "");
        try {
            return Long.parseLong(Mute.instance.mysql.getConnection().prepareStatement("SELECT mutetime FROM mute WHERE pluuid='" + uuid + "'").executeQuery().getString("mutetime"));
        } catch (SQLException e) {
            return 0;
        }
    }
    public void unmutePlayer(String uuid) {
        uuid = uuid.replaceAll("-", "");
        try {
            Mute.instance.mysql.getConnection().prepareStatement("DELETE FROM mute WHERE pluuid='" + uuid + "'").execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getTimesMuted(String uuid) {
        uuid = uuid.replaceAll("-", "");
        try {
            return Mute.instance.mysql.getConnection().prepareStatement("SELECT timesmuted FROM mute WHERE pluuid='" + uuid + "'").executeQuery().getInt("timesmuted");
        } catch (SQLException e) {
            return 0;
        }
    }
    public void incrementTimesMuted(String uuid) {
        uuid = uuid.replaceAll("-", "");
        try {
            Mute.instance.mysql.getConnection().prepareStatement("UPDATE mute SET timesmuted='" + (getTimesMuted(uuid) + 1) + "' WHERE pluuid='" + uuid + "'").execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
