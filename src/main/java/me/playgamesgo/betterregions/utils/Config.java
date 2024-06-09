package me.playgamesgo.betterregions.utils;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Variable;
import lombok.Getter;

@Getter
public class Config extends OkaeriConfig {
    @Variable("auto-save-interval")
    @Comment("The interval in seconds to auto save the regions")
    @Comment("Set to 0 to immediately save the regions after a change")
    public int autoSaveInterval = 300;

    @Variable("use-mysql")
    @Comment("Whether to use MySQL for storing the regions")
    public boolean mysql = false;

    @Variable("mysql-host")
    public String mysqlHost = "localhost";

    @Variable("mysql-port")
    public int mysqlPort = 3306;

    @Variable("mysql-database")
    public String mysqlDatabase = "betterregions";

    @Variable("mysql-username")
    public String mysqlUsername = "betterregions";

    @Variable("mysql-password")
    public String mysqlPassword = "password";
}
