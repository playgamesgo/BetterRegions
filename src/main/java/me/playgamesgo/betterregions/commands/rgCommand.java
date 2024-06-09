package me.playgamesgo.betterregions.commands;

import me.playgamesgo.betterregions.BetterRegions;
import me.playgamesgo.betterregions.commands.posCommand.posLogic;
import me.playgamesgo.betterregions.utils.Flags;
import me.playgamesgo.betterregions.utils.Region;
import org.bukkit.entity.Player;

import java.util.List;

public class rgCommand {
    public static void claim(Player player, String regionName) {
        if (posLogic.check(player, regionName)) {
            if (BetterRegions.config.autoSaveInterval == 0) BetterRegions.saveRegions();
            player.sendMessage("Region claimed!");
        } else {
            player.sendMessage("Failed to claim region!");
        }
    }

    public static void delete(Player player, String regionName) {
        if (posLogic.removeRegion(regionName)) {
            if (BetterRegions.config.autoSaveInterval == 0) BetterRegions.saveRegions();
            player.sendMessage("Region deleted!");
        } else {
            player.sendMessage("Region not found!");
        }
    }

    public static void list(Player player) {
        player.sendMessage("Regions:");
        BetterRegions.regions.forEach(region -> player.sendMessage(region.getName() + " - " +
                region.getOwner().getName() + " - " + region.getWorld().getName() + " - " +
                region.getX1() + " " + region.getY1() + " " + region.getZ1() + " " + region.getX2() + " " + region.getY2() + " " + region.getZ2()));
    }

    public static void info(Player player, String regionName) {
        if (posLogic.getRegion(regionName) != null) {
            Region region = posLogic.getRegion(regionName);
            player.sendMessage("Region info:");
            player.sendMessage("Name: " + region.getName());
            player.sendMessage("Owner: " + region.getOwner().getName());
            player.sendMessage("World: " + region.getWorld().getName());
            player.sendMessage("Position 1: " + region.getX1() + " " + region.getY1() + " " + region.getZ1());
            player.sendMessage("Position 2: " + region.getX2() + " " + region.getY2() + " " + region.getZ2());
        } else {
            player.sendMessage("Region not found!");
        }
    }

    public static void tp(Player player, String regionName) {
        if (posLogic.getRegion(regionName) != null) {
            Region region = posLogic.getRegion(regionName);
            player.teleport(region.getOwner());
            player.sendMessage("Teleported to region owner!");
        } else {
            player.sendMessage("Region not found!");
        }
    }

    public static void flags(Player player,String regionName) {
        if (posLogic.getRegion(regionName) != null) {
            Region region = posLogic.getRegion(regionName);
            player.sendMessage("Flags:");
            player.sendMessage(String.join(", ", region.getFlags()));
        } else {
            player.sendMessage("Region not found!");
        }
    }

    public static void flag(Player player, String regionName, String action, List<Flags> flags) {
        if (posLogic.getRegion(regionName) != null) {
            Region region = posLogic.getRegion(regionName);
            for (Flags flag : flags)
                if (action.equals("add")) {
                    region.setFlag(flag, true);
                    player.sendMessage("Flag added!");
                } else {
                    region.setFlag(flag, false);
                    player.sendMessage("Flag removed!");
                }
            if (BetterRegions.config.autoSaveInterval == 0) BetterRegions.saveRegions();
        } else {
            player.sendMessage("Region not found!");
        }
    }
}
