package me.playgamesgo.betterregions.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.playgamesgo.betterregions.BetterRegions;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseManager {
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    public DatabaseManager init() {
        if (BetterRegions.config.mysql) {
            initMySQL();
        } else {
            initSQLite(BetterRegions.instance);
        }
        initDefaultTables();

        return this;
    }

    private void initMySQL() {
        config.setJdbcUrl("jdbc:mariadb://" + BetterRegions.config.mysqlHost + ":" + BetterRegions.config.mysqlPort + "/" + BetterRegions.config.mysqlDatabase);
        config.setUsername(BetterRegions.config.mysqlUsername);
        config.setPassword(BetterRegions.config.mysqlPassword);
        config.setDriverClassName("org.mariadb.jdbc.Driver");

        ds = new HikariDataSource(config);
    }

    private void initSQLite(Plugin plugin) {
        File file = new File(plugin.getDataFolder(), "regions.db");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "An error occurred while creating the database file", e);
            }
        }

        config.setJdbcUrl("jdbc:sqlite:" + file.getAbsolutePath());
        ds = new HikariDataSource(config);
    }

    private void initDefaultTables() {
        try (Connection connection = getConnection()) {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS regions (name VARCHAR(255) PRIMARY KEY, " +
                    "owner VARCHAR(255), world VARCHAR(255), min_x INT, min_y INT, min_z INT, max_x INT, max_y INT, max_z INT, flags TEXT)");
        } catch (SQLException e) {
            BetterRegions.instance.getLogger().log(Level.SEVERE, "An error occurred while creating the default tables", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
