package me.playgamesgo.betterregions.commands.posCommand;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.arguments.ALocationArgument;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command("pos1")
@Permission("betterregions.pos")
public class pos1 {
    @Default
    public static void pos(Player player, @ALocationArgument Location location) {
        posLogic.pos1(player, location);
        player.sendMessage("Position 1 set!");
    }

    @Default
    public static void pos(Player player) {
        posLogic.pos1(player, player.getLocation());
        player.sendMessage("Position 1 set!");
    }
}
