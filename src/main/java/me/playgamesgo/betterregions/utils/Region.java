package me.playgamesgo.betterregions.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.List;

@Getter
@Setter
public class Region {
    private final String name;
    private final Player owner;
    private final World world;
    private final int x1;
    private final int y1;
    private final int z1;
    private final int x2;
    private final int y2;
    private final int z2;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final EnumSet<Flags> flags = EnumSet.noneOf(Flags.class);

    public Region(String name, Player owner, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.name = name;
        this.owner = owner;
        this.world = owner.getWorld();
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public boolean isInside(Location location) {
        return location.getBlockX() >= x1 && location.getBlockX() <= x2
                && location.getBlockY() >= y1 && location.getBlockY() <= y2
                && location.getBlockZ() >= z1 && location.getBlockZ() <= z2;
    }

    public void setFlag(Flags flag, boolean value) {
        if (value) {
            flags.add(flag);
        } else {
            flags.remove(flag);
        }
    }

    public void setFlags(List<String> flags) {
        this.flags.clear();
        flags.forEach(flag -> this.flags.add(Flags.valueOf(flag)));
    }

    public boolean getFlag(Flags flag) {
        return flags.contains(flag);
    }

    public List<String> getFlags() {
        return flags.stream().map(Flags::name).toList();
    }
}
