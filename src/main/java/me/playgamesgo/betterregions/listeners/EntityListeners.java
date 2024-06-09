package me.playgamesgo.betterregions.listeners;

import io.papermc.paper.event.entity.EntityPortalReadyEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.event.player.PlayerItemFrameChangeEvent;
import me.playgamesgo.betterregions.BetterRegions;
import me.playgamesgo.betterregions.commands.posCommand.posLogic;
import me.playgamesgo.betterregions.utils.Flags;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;

public class EntityListeners implements Listener {
    @EventHandler
    public void onPortalJoin(EntityPortalReadyEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.ENTER_PORTALS)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getLocation()))
                && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.CREATURE_SPAWNING)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChorusFruit(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getTo()))
                    && posLogic.getRegion(event.getTo()).getFlag(Flags.CHORUS_FRUIT)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFireChargeCreate(EntitySpawnEvent event) {
        if (event.getEntity().getType() == EntityType.FIREBALL) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.FIRE_CHARGE_CREATE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPvE(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) return;
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.PvE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getItem().getLocation()))
                && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.ITEM_PICKUP)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPotionConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.POTION) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getPlayer().getLocation()))
                    && posLogic.getRegion(event.getPlayer().getLocation()).getFlag(Flags.POTION_CONSUME)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChatting(AsyncChatEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getPlayer().getLocation()))
                && posLogic.getRegion(event.getPlayer().getLocation()).getFlag(Flags.CHATTING)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPvP(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.PvP)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.FOOD_CHANGE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemMerging(ItemMergeEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.ITEM_MERGING)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEnter(PlayerMoveEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getTo()))
                && posLogic.getRegion(event.getTo()).getFlag(Flags.ENTRY_DENY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEnterPermission(PlayerMoveEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getTo()))
                && posLogic.getRegion(event.getTo()).getFlag(Flags.ENTRY_PERMISSION)) {
            if (!event.getPlayer().hasPermission("betterregions.enter." + posLogic.getRegion(event.getTo()).getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHostileMobSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Creature) {
            if (event.getEntity() instanceof Monster) {
                if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getLocation()))
                        && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.HOSTILE_MOB_SPAWN)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onItemFrameInteract(PlayerItemFrameChangeEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getItemFrame().getLocation()))
                && posLogic.getRegion(event.getItemFrame().getLocation()).getFlag(Flags.ITEM_FRAME_INTERACT)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemFrameBreak(HangingBreakByEntityEvent event) {
        if (event.getEntity().getType() == EntityType.ITEM_FRAME) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.ITEM_FRAME_INTERACT)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntryExit(PlayerMoveEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> !region.isInside(event.getTo()))
                && posLogic.getRegion(event.getTo()).getFlag(Flags.ENTRY_EXIT)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.POTION_SPLASH)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPeacefulMobSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Creature) {
            if (!(event.getEntity() instanceof Monster)) {
                if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getLocation()))
                        && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.PEACEFUL_MOB_SPAWN)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getPlayer().getLocation()))
                && posLogic.getRegion(event.getPlayer().getLocation()).getFlag(Flags.ITEM_DROP)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCrafting(CraftItemEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getWhoClicked().getLocation()))
                && posLogic.getRegion(event.getWhoClicked().getLocation()).getFlag(Flags.CRAFTING)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onContactDamage(EntityDamageByBlockEvent event) {
        if (event.getDamager() != null && event.getDamager().getType() == Material.CACTUS) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.CONTACT_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityAttachDamage(EntityDamageByEntityEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.ENTITY_ATTACH_DAMAGE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntitySweepAttackDamage(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.ENTITY_SWEEP_ATTACK_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onProjectileDamage(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.PROJECTILE_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSuffocationDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.SUFFOCATION_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.FALL_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFireDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.FIRE_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFireTickDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.FIRE_TICK_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMeltingDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.MELTING) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.MELTING_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLavaDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.LAVA_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrowningDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.DROWNING_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockExplosionDamage(EntityDamageByBlockEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.BLOCK_EXPLOSION_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityExplosionDamage(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.ENTITY_EXPLOSION_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onVoidDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.VOID_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLightningDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.LIGHTNING_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSuicideDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.SUICIDE_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onStarvationDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.STARVATION_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPotionDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.POTION_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMagicDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.MAGIC_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onWitherDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.WITHER) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.WITHER_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFallingBlockDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.FALLING_BLOCK_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onThornsDamage(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.THORNS) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.THORNS_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDragonBreathDamage(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.DRAGON_BREATH_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCustomDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.CUSTOM_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFlyIntoWallDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.FLY_INTO_WALL_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHotFloorDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.HOT_FLOOR_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCrammingDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.CRAMMING) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.CRAMMING_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDryOutDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.DRYOUT) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.DRYOUT_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFreezeDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FREEZE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                    && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.FREEZE_DAMAGE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(PlayerCommandPreprocessEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getPlayer().getLocation()))
                && posLogic.getRegion(event.getPlayer().getLocation()).getFlag(Flags.COMMAND_DENY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.DEATH)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getBed().getLocation()))
                && posLogic.getRegion(event.getBed().getLocation()).getFlag(Flags.DENY_SLEEP)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFly(PlayerToggleFlightEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getPlayer().getLocation()))
                && posLogic.getRegion(event.getPlayer().getLocation()).getFlag(Flags.FLY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onKeepInventory(PlayerDeathEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getEntity().getLocation()))
                && posLogic.getRegion(event.getEntity().getLocation()).getFlag(Flags.KEEP_INVENTORY)) {
            event.setKeepInventory(true);
        }
    }
}
