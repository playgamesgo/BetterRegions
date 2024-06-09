package me.playgamesgo.betterregions.listeners;

import me.playgamesgo.betterregions.BetterRegions;
import me.playgamesgo.betterregions.events.CropTrampleEvent;
import me.playgamesgo.betterregions.utils.Flags;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;

public class BlockListeners implements Listener {
    @EventHandler
    public void onFireSpread(BlockSpreadEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getBlock().getLocation()))
                && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.FIRE_SPREAD))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getBlock().getLocation()))
                && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.BLOCK_IGNITING))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMobTrample(EntityInteractEvent event) {
        if (event.getEntity() instanceof Player) return;
        if (event.getBlock().getType() == Material.FARMLAND) {
            CropTrampleEvent cropTrampleEvent = new CropTrampleEvent(event.getEntity(), CropTrampleEvent.TrampleCause.MOB, event.getBlock());
            Bukkit.getPluginManager().callEvent(cropTrampleEvent);
            if (cropTrampleEvent.isCancelled()) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerTrample(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.FARMLAND) {
            CropTrampleEvent cropTrampleEvent = new CropTrampleEvent(event.getPlayer(), CropTrampleEvent.TrampleCause.PLAYER, event.getClickedBlock());
            Bukkit.getPluginManager().callEvent(cropTrampleEvent);
            if (cropTrampleEvent.isCancelled()) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTrample(CropTrampleEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getBlock().getLocation()))
                && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.CROP_TRAMPLING))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWaterFlow(BlockFromToEvent event) {
        if (event.getBlock().getType() == Material.WATER) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getToBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.WATER_FLOW))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onExplosionBlockDamage(EntityExplodeEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getLocation()))
                && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.EXPLOSION_BLOCK_DAMAGE))) {
            event.blockList().clear();
        }
    }

    @EventHandler
    public void onLavaFlow(BlockFromToEvent event) {
        if (event.getBlock().getType() == Material.LAVA) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getToBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.LAVA_FLOW))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getBlock().getLocation()))
                && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.BLOCK_BREAKING))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFireChargeFire(BlockPlaceEvent event) {
        if (event.getItemInHand().getType() == Material.FIRE_CHARGE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.FIRE_CHARGE_FIRE))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getBlock().getLocation()))
                && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.BLOCK_PLACING))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getBlock().getLocation()))
                && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.BLOCK_BURNING))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockInteraction(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.BLOCK_INTERACTION))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBarrelOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BARREL) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.BARREL_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.CHEST_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEnderChestOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.ENDER_CHEST) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.ENDER_CHEST_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onShulkerOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.SHULKER_BOX) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.SHULKER_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onTrappedChestOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.TRAPPED_CHEST_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlastFurnaceOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BLAST_FURNACE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.BLAST_FURNACE_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFurnaceOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.FURNACE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.FURNACE_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSmokerOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.SMOKER) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.SMOKER_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCartographyTableOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CARTOGRAPHY_TABLE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.CARTOGRAPHY_TABLE_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onGrindstoneOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.GRINDSTONE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.GRINDSTONE_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLoomOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.LOOM) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.LOOM_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onStonecutterOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.STONECUTTER) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.STONECUTTER_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSmithingTableOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.SMITHING_TABLE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.SMITHING_TABLE_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMinecartOpen(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Minecart) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getRightClicked().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.MINECART_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHopperOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.HOPPER) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.HOPPER_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDropperOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.DROPPER) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.DROPPER_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDispenserOpen(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.DISPENSER) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.DISPENSER_OPEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRespawnAnchorInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.RESPAWN_ANCHOR) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.RESPAWN_ANCHOR_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onAnvilInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.ANVIL) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.ANVIL_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBeaconInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BEACON) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.BEACON_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onButtonsInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.STONE_BUTTON ||
                event.getClickedBlock().getType() == Material.OAK_BUTTON || event.getClickedBlock().getType() == Material.SPRUCE_BUTTON ||
                event.getClickedBlock().getType() == Material.BIRCH_BUTTON || event.getClickedBlock().getType() == Material.JUNGLE_BUTTON ||
                event.getClickedBlock().getType() == Material.ACACIA_BUTTON || event.getClickedBlock().getType() == Material.DARK_OAK_BUTTON ||
                event.getClickedBlock().getType() == Material.CRIMSON_BUTTON || event.getClickedBlock().getType() == Material.WARPED_BUTTON)) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.BUTTONS_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBedInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BLACK_BED ||
                event.getClickedBlock().getType() == Material.BLUE_BED || event.getClickedBlock().getType() == Material.BROWN_BED ||
                event.getClickedBlock().getType() == Material.CYAN_BED || event.getClickedBlock().getType() == Material.GRAY_BED ||
                event.getClickedBlock().getType() == Material.GREEN_BED || event.getClickedBlock().getType() == Material.LIGHT_BLUE_BED ||
                event.getClickedBlock().getType() == Material.LIGHT_GRAY_BED || event.getClickedBlock().getType() == Material.LIME_BED ||
                event.getClickedBlock().getType() == Material.MAGENTA_BED || event.getClickedBlock().getType() == Material.ORANGE_BED ||
                event.getClickedBlock().getType() == Material.PINK_BED || event.getClickedBlock().getType() == Material.PURPLE_BED ||
                event.getClickedBlock().getType() == Material.RED_BED || event.getClickedBlock().getType() == Material.WHITE_BED ||
                event.getClickedBlock().getType() == Material.YELLOW_BED) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.BED_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDoorsInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.OAK_DOOR ||
                event.getClickedBlock().getType() == Material.ACACIA_DOOR || event.getClickedBlock().getType() == Material.BIRCH_DOOR ||
                event.getClickedBlock().getType() == Material.CRIMSON_DOOR || event.getClickedBlock().getType() == Material.DARK_OAK_DOOR ||
                event.getClickedBlock().getType() == Material.IRON_DOOR || event.getClickedBlock().getType() == Material.JUNGLE_DOOR ||
                event.getClickedBlock().getType() == Material.SPRUCE_DOOR || event.getClickedBlock().getType() == Material.WARPED_DOOR)) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.DOORS_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEnchantingTableInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.ENCHANTING_TABLE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.ENCHANTING_TABLE_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCakeInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CAKE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.CAKE_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLeverInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.LEVER) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.LEVER_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCandleInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.CANDLE ||
                event.getClickedBlock().getType() == Material.WHITE_CANDLE || event.getClickedBlock().getType() == Material.ORANGE_CANDLE ||
                event.getClickedBlock().getType() == Material.MAGENTA_CANDLE || event.getClickedBlock().getType() == Material.LIGHT_BLUE_CANDLE ||
                event.getClickedBlock().getType() == Material.YELLOW_CANDLE || event.getClickedBlock().getType() == Material.LIME_CANDLE ||
                event.getClickedBlock().getType() == Material.PINK_CANDLE || event.getClickedBlock().getType() == Material.GRAY_CANDLE ||
                event.getClickedBlock().getType() == Material.LIGHT_GRAY_CANDLE || event.getClickedBlock().getType() == Material.CYAN_CANDLE ||
                event.getClickedBlock().getType() == Material.PURPLE_CANDLE || event.getClickedBlock().getType() == Material.BLUE_CANDLE ||
                event.getClickedBlock().getType() == Material.BROWN_CANDLE || event.getClickedBlock().getType() == Material.GREEN_CANDLE ||
                event.getClickedBlock().getType() == Material.RED_CANDLE || event.getClickedBlock().getType() == Material.BLACK_CANDLE)) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.CANDLE_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onComposterInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.COMPOSTER) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.COMPOSTER_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCraftingTableInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CRAFTING_TABLE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.CRAFTING_TABLE_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFenceInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.OAK_FENCE ||
                event.getClickedBlock().getType() == Material.ACACIA_FENCE || event.getClickedBlock().getType() == Material.BIRCH_FENCE ||
                event.getClickedBlock().getType() == Material.CRIMSON_FENCE || event.getClickedBlock().getType() == Material.DARK_OAK_FENCE ||
                event.getClickedBlock().getType() == Material.JUNGLE_FENCE || event.getClickedBlock().getType() == Material.NETHER_BRICK_FENCE ||
                event.getClickedBlock().getType() == Material.SPRUCE_FENCE || event.getClickedBlock().getType() == Material.WARPED_FENCE)) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.FENCE_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFlowerPotInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.FLOWER_POT) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.FLOWER_POT_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onGrindstoneInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.GRINDSTONE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.GRINDSTONE_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onJukeboxInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.JUKEBOX) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.JUKEBOX_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLecternInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.LECTERN) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.LECTERN_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLecternTake(PlayerTakeLecternBookEvent event) {
        if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getLectern().getLocation()))
                && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.LECTERN_TAKE))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPressurePlatesInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.STONE_PRESSURE_PLATE ||
                event.getClickedBlock().getType() == Material.ACACIA_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.BIRCH_PRESSURE_PLATE ||
                event.getClickedBlock().getType() == Material.CRIMSON_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.DARK_OAK_PRESSURE_PLATE ||
                event.getClickedBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.JUNGLE_PRESSURE_PLATE ||
                event.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.OAK_PRESSURE_PLATE ||
                event.getClickedBlock().getType() == Material.POLISHED_BLACKSTONE_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.SPRUCE_PRESSURE_PLATE ||
                event.getClickedBlock().getType() == Material.WARPED_PRESSURE_PLATE)) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.PRESSURE_PLATES_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSmitingTableInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.SMITHING_TABLE) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.SMITING_TABLE_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onTrapdoorsInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.OAK_TRAPDOOR ||
                event.getClickedBlock().getType() == Material.ACACIA_TRAPDOOR || event.getClickedBlock().getType() == Material.BIRCH_TRAPDOOR ||
                event.getClickedBlock().getType() == Material.CRIMSON_TRAPDOOR || event.getClickedBlock().getType() == Material.DARK_OAK_TRAPDOOR ||
                event.getClickedBlock().getType() == Material.IRON_TRAPDOOR || event.getClickedBlock().getType() == Material.JUNGLE_TRAPDOOR ||
                event.getClickedBlock().getType() == Material.SPRUCE_TRAPDOOR || event.getClickedBlock().getType() == Material.WARPED_TRAPDOOR)) {
            if (BetterRegions.regions.stream().anyMatch(region -> region.isInside(event.getClickedBlock().getLocation()))
                    && BetterRegions.regions.stream().noneMatch(region -> region.getFlag(Flags.TRAPDOORS_INTERACT))) {
                event.setCancelled(true);
            }
        }
    }
}
