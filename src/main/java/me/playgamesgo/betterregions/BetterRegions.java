package me.playgamesgo.betterregions;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.ListArgumentBuilder;
import dev.jorel.commandapi.arguments.StringArgument;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import me.playgamesgo.betterregions.commands.posCommand.pos1;
import me.playgamesgo.betterregions.commands.posCommand.pos2;
import me.playgamesgo.betterregions.commands.posCommand.posLogic;
import me.playgamesgo.betterregions.commands.rgCommand;
import me.playgamesgo.betterregions.listeners.BlockListeners;
import me.playgamesgo.betterregions.listeners.EntityListeners;
import me.playgamesgo.betterregions.utils.Config;
import me.playgamesgo.betterregions.utils.DatabaseManager;
import me.playgamesgo.betterregions.utils.Flags;
import me.playgamesgo.betterregions.utils.Region;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Plugin(name = "BetterRegions", version = "0.0.1")
@ApiVersion(ApiVersion.Target.v1_20)
@Author("playgamesgo")
public final class BetterRegions extends JavaPlugin {
    public static BetterRegions instance;
    public static List<Region> regions = new ArrayList<>();
    public static Config config;
    public static DatabaseManager databaseManager;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
    }

    @Override
    public void onEnable() {
        instance = this;

        config = ConfigManager.create(Config.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesCommons(), new SerdesBukkit());
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        databaseManager = new DatabaseManager().init();

        CommandAPI.registerCommand(pos1.class);
        CommandAPI.registerCommand(pos2.class);

        registerCommands();

        CommandAPI.onEnable();

        getServer().getPluginManager().registerEvents(new BlockListeners(), this);
        getServer().getPluginManager().registerEvents(new EntityListeners(), this);

        try (Connection connection = DatabaseManager.getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM regions");
            while (resultSet.next()) {
                Region region = new Region(
                        resultSet.getString("name"),
                        getServer().getPlayer(resultSet.getString("owner")),
                        resultSet.getInt("min_x"),
                        resultSet.getInt("min_y"),
                        resultSet.getInt("min_z"),
                        resultSet.getInt("max_x"),
                        resultSet.getInt("max_y"),
                        resultSet.getInt("max_z")
                );
                region.setFlags(List.of(resultSet.getString("flags").split(",")));

                regions.add(region);
            }
        } catch (SQLException e) {
            getLogger().severe("An error occurred while connecting to the database");
        }

        if (config.getAutoSaveInterval() > 0) {
            getServer().getScheduler().runTaskTimerAsynchronously(this, BetterRegions::saveRegions, config.getAutoSaveInterval() * 20L, config.getAutoSaveInterval() * 20L);
        }
    }

    private static void registerCommands() {
        new CommandAPICommand("region")
                .withAliases("rg")
                .withPermission("betterregions.region")
                .withSubcommand(new CommandAPICommand("claim")
                        .withOptionalArguments(new StringArgument("regionName"))
                        .executesPlayer((player, args) -> {
                            rgCommand.claim(player, (String) args.getOrDefault("regionName", posLogic.getRegion(player.getLocation()).getName()));
                        }))
                .withSubcommand(new CommandAPICommand("delete")
                        .withOptionalArguments(new StringArgument("regionName").replaceSuggestions(ArgumentSuggestions.strings(
                                regions.stream().map(Region::getName).toArray(String[]::new))
                        )).executesPlayer((player, args) -> {
                            rgCommand.delete(player, (String) args.getOrDefault("regionName", posLogic.getRegion(player.getLocation()).getName()));
                        }))
                .withSubcommand(new CommandAPICommand("list")
                        .executesPlayer((player, args) -> {
                            rgCommand.list(player);
                        }))
                .withSubcommand(new CommandAPICommand("info")
                        .withOptionalArguments(new StringArgument("regionName").replaceSuggestions(ArgumentSuggestions.strings(
                                regions.stream().map(Region::getName).toArray(String[]::new))
                        )).executesPlayer((player, args) -> {
                            rgCommand.info(player, (String) args.getOrDefault("regionName", posLogic.getRegion(player.getLocation()).getName()));
                        }))
                .withSubcommand(new CommandAPICommand("tp")
                        .withArguments(new StringArgument("regionName").replaceSuggestions(ArgumentSuggestions.strings(
                                regions.stream().map(Region::getName).toArray(String[]::new))
                        )).executesPlayer((player, args) -> {
                            rgCommand.tp(player, (String) args.get("regionName"));
                        }))
                .withSubcommand(new CommandAPICommand("flags")
                        .withOptionalArguments(new StringArgument("regionName").replaceSuggestions(ArgumentSuggestions.strings(
                                regions.stream().map(Region::getName).toArray(String[]::new))
                        )).executesPlayer((player, args) -> {
                            rgCommand.flags(player, (String) args.getOrDefault("regionName", posLogic.getRegion(player.getLocation()).getName()));
                        }))
                .register();

        new CommandAPICommand("flag")
                .withPermission("betterregions.flag")
                .withSubcommand(new CommandAPICommand("add")
                        .withArguments(new StringArgument("regionName").replaceSuggestions(ArgumentSuggestions.strings(
                                regions.stream().map(Region::getName).toArray(String[]::new))
                        ))
                        .withArguments(new ListArgumentBuilder<Flags>("flags")
                                .allowDuplicates(false)
                                .withList(List.of(Flags.values()))
                                .withMapper(flags -> flags.name().toUpperCase())
                                .buildGreedy()
                        ).executesPlayer((player, args) -> {
                            if (posLogic.isInside(player.getLocation())) {
                                rgCommand.flag(player, posLogic.getRegion(player.getLocation()).getName(), "add", (List<Flags>) args.get("flags"));
                            } else {
                                player.sendMessage("You are not inside a region!");
                            }
                        }))
                .withSubcommand(new CommandAPICommand("remove")
                        .withArguments(new StringArgument("regionName").replaceSuggestions(ArgumentSuggestions.strings(
                                regions.stream().map(Region::getName).toArray(String[]::new))
                        ))
                        .withArguments(new ListArgumentBuilder<Flags>("flags")
                                .allowDuplicates(false)
                                .withList(List.of(Flags.values()))
                                .withMapper(flags -> flags.name().toUpperCase())
                                .buildGreedy()
                        ).executesPlayer((player, args) -> {
                            if (posLogic.isInside(player.getLocation())) {
                                rgCommand.flag(player, posLogic.getRegion(player.getLocation()).getName(), "remove", (List<Flags>) args.get("flags"));
                            } else {
                                player.sendMessage("You are not inside a region!");
                            }
                        }))
                .register();
    }

    public static void saveRegions() {
        try (Connection connection = DatabaseManager.getConnection()) {
            connection.createStatement().execute("DELETE FROM regions");

            String insertSql = "INSERT INTO regions (name, owner, world, min_x, min_y, min_z, max_x, max_y, max_z, flags) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            for (Region region : regions) {
                preparedStatement.setString(1, region.getName());
                preparedStatement.setString(2, region.getOwner().getName());
                preparedStatement.setString(3, region.getWorld().getName());
                preparedStatement.setInt(4, region.getX1());
                preparedStatement.setInt(5, region.getY1());
                preparedStatement.setInt(6, region.getZ1());
                preparedStatement.setInt(7, region.getX2());
                preparedStatement.setInt(8, region.getY2());
                preparedStatement.setInt(9, region.getZ2());
                preparedStatement.setString(10, region.getFlags().toString());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            instance.getLogger().severe("An error occurred while connecting to the database");
        }
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();

        saveRegions();
    }
}
