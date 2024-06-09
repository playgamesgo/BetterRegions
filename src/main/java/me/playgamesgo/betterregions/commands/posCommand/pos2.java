package me.playgamesgo.betterregions.commands.posCommand;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.arguments.ALocationArgument;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command("pos2")
@Permission("betterregions.pos")
public class pos2 {
    @Default
    public static void pos(Player player, @ALocationArgument Location location) {
        posLogic.pos2(player, location);
        player.sendMessage("Position 2 set!");
    }

    @Default
    public static void pos(Player player) {
        posLogic.pos2(player, player.getLocation());
        player.sendMessage("Position 2 set!");
    }
}
