package de.jann3107.mute.utils;

import de.jann3107.mute.Mute;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MuteManager {
    public void mutePlayer(String uuid, String time) {
        incrementTimesMuted(uuid);
        uuid = uuid.replaceAll("-", "");
        try {
            //is there a entry for this player?
            Boolean entry = Mute.instance.mysql.getConnection().prepareStatement("SELECT * FROM mute WHERE pluuid = '" + uuid + "'").executeQuery().next();
            if(!entry){
                // add new entry
                Mute.instance.mysql.getConnection().prepareStatement("INSERT INTO mute (pluuid, mutetime) VALUES ('" + uuid + "', '" + time + "')").execute();
            } else {
                // Update entry
                Mute.instance.mysql.getConnection().prepareStatement("UPDATE mute SET mutetime = '" + time + "' WHERE pluuid = '" + uuid + "'").execute();
            }
            notifyPlayer(uuid, String.valueOf(getTimeMillisWhenUnmuted(uuid)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void notifyPlayer(String uuid, String time) {
        if(Mute.instance.getConfig().getBoolean("notify")){
            Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getPlayer().sendMessage("§cDu wurdest bis " + getTimestampWhenUnmuted(time) + " gemutet!");
        }
    }
    public void mutePlayerForHours(String uuid, int hours) {
        mutePlayer(uuid, String.valueOf(System.currentTimeMillis() + (hours * 60 * 60 * 1000)));
    }
    public boolean isMuted(String uuid) {
        Long unmuted = getTimeMillisWhenUnmuted(uuid);
        if(unmuted == null) return false;
        return unmuted > System.currentTimeMillis();
    }
    public long getTimeMillisWhenUnmuted(String uuid) {
        uuid = uuid.replaceAll("-", "");
        try {
            ResultSet rs = Mute.instance.mysql.getConnection().prepareStatement("SELECT mutetime FROM mute WHERE pluuid='" + uuid + "'").executeQuery();
            rs.next();
            return Long.parseLong(rs.getString("mutetime"));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public String getTimestampWhenUnmuted(String uuid) {
        return getTimestampWhenUnmuted(getTimeMillisWhenUnmuted(uuid));
    }
    public String getTimestampWhenUnmuted(Long timemillis) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yy");
        Instant in = Instant.ofEpochMilli(timemillis);
        LocalDateTime now = LocalDateTime.ofInstant(in, ZoneId.systemDefault());
        return dtf.format(now);
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
            ResultSet rs = Mute.instance.mysql.getConnection().prepareStatement("SELECT timesmuted FROM mute WHERE pluuid='" + uuid + "'").executeQuery();
            rs.next();
            return rs.getInt("timesmuted");
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
