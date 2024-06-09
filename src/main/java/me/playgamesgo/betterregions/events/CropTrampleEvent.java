package me.playgamesgo.betterregions.events;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CropTrampleEvent extends Event implements Cancellable {
    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private Entity trampler;
    @Getter
    private TrampleCause cause;
    @Getter
    private Block block;

    public CropTrampleEvent(Entity trampler, TrampleCause cause, Block block) {
        this.trampler = trampler;
        this.cause = cause;
        this.block = block;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum TrampleCause {
        MOB(),
        PLAYER()
    }
}