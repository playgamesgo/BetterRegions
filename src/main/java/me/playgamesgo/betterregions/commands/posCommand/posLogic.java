package me.playgamesgo.betterregions.commands.posCommand;

import me.playgamesgo.betterregions.BetterRegions;
import me.playgamesgo.betterregions.utils.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class posLogic {
    private static final HashMap<Player, Location> pos1 = new HashMap<>();
    private static final HashMap<Player, Location> pos2 = new HashMap<>();

    protected static void pos1(Player player, Location location) {
        pos1.put(player, location);
    }

    protected static void pos2(Player player, Location location) {
        pos2.put(player, location);
    }

    public static boolean check(Player player, String regionName) {
        if (pos1.containsKey(player) && pos2.containsKey(player)) {
            if (pos1.get(player).getWorld() != pos2.get(player).getWorld()) {
                player.sendMessage("Both positions need to be in the same world!");
                return false;
            }

            if (BetterRegions.regions.stream().anyMatch(region -> region.getName().equals(regionName))) {
                player.sendMessage("A region with that name already exists!");
                return false;
            }

            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(pos1.get(player)) || region.isInside(pos2.get(player)))) {
                player.sendMessage("One of the positions is inside another region!");
                return false;
            }

            BetterRegions.regions.add(new Region(regionName, player, pos1.get(player).getBlockX(), pos1.get(player).getBlockY(), pos1.get(player).getBlockZ(), pos2.get(player).getBlockX(), pos2.get(player).getBlockY(), pos2.get(player).getBlockZ()));

            pos1.remove(player);
            pos2.remove(player);

            return true;
        } else {
            player.sendMessage("You need to set both positions first!");
            return false;
        }
    }

    public static boolean isInside(Location location) {
        return BetterRegions.regions.stream().anyMatch(region -> region.isInside(location));
    }

    public static Region getRegion(Location location) {
        return BetterRegions.regions.stream().filter(region -> region.isInside(location)).findFirst().orElse(null);
    }

    public static Region getRegion(String regionName) {
        return BetterRegions.regions.stream().filter(region -> region.getName().equals(regionName)).findFirst().orElse(null);
    }

    public static boolean removeRegion(String regionName) {
        if (BetterRegions.regions.stream().noneMatch(region -> region.getName().equals(regionName))) {
            return false;
        }
        BetterRegions.regions.removeIf(region -> region.getName().equals(regionName));
        return true;
    }
}
