package de.jann3107.mute.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private String host;
    private String database;
    private String user;
    private String password;
    private int port;

    private Connection connection;

    public MySQL(final String host, final String database, final String user, final String password, final int port) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(getConnectionString(), user, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getConnectionString() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {}
    }

    public void init() {
        // Create a table named "mute" if not exists
        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `mute` (pluuid VARCHAR(32), mutetime TEXT(14))").execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
