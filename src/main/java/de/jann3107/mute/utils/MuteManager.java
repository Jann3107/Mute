package de.jann3107.mute.utils;

import de.jann3107.mute.Mute;

import java.sql.SQLException;

public class MuteManager {
    public void mutePlayer(String uuid, String time) {
        uuid = uuid.replaceAll("-", "");
        try {
            Mute.instance.mysql.getConnection().prepareStatement("INSERT INTO mute (pluuid, mutetime) VALUES ('" + uuid + "', '" + time + "')").execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void mutePlayerForHours(String uuid, int hours) {
        mutePlayer(uuid, String.valueOf(System.currentTimeMillis() + (hours * 60 * 60 * 1000)));
    }
    public boolean isMuted(String uuid) {
        uuid = uuid.replaceAll("-", "");
        try {
            return Mute.instance.mysql.getConnection().prepareStatement("SELECT * FROM mute WHERE pluuid='" + uuid + "'").executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
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
}
