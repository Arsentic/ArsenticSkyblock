package net.arsentic.arsenticskyblock.listeners

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.inventory.ItemStack
import java.lang.reflect.InvocationTargetException

class PlayerBucketEmptyListener : Listener {
    val supports: Boolean = Material.supports(13)

    @EventHandler
    fun onBucketEmpty(event: PlayerBucketEmptyEvent) {
        val type = event.bucket
        val item = event.player.itemInHand
        var block: Block
        val isInPaper113 = Bukkit.getVersion().contains("Paper") && Bukkit.getBukkitVersion().contains("1.13")
        block = if (supports && !isInPaper113) {
            event.block
        } else {
            try {
                PlayerBucketEmptyEvent::class.java.getMethod("getBlockClicked").invoke(event) as Block
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
                return
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                return
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
                return
            }
        }
        val player = event.player
        if (getConfiguration().allowWaterInNether) {
            val world = block.world
            if (world.environment != World.Environment.NETHER) return
            if (type != Material.WATER_BUCKET) return

            // To prevent waterloggable blocks from being replaced by the water
            if (block.type != Material.AIR) {
                block = block.getRelative(event.blockFace)
            }
            event.isCancelled = true
            val face = event.blockFace
            block.type = Material.WATER
            val blockPlaceEvent = BlockPlaceEvent(block, block.state, block.getRelative(face.oppositeFace), item, player, false)
            if (blockPlaceEvent.isCancelled) {
                block.type = Material.AIR
            } else if (player.gameMode == GameMode.SURVIVAL) {
                if (item.amount == 1) {
                    item.type = Material.BUCKET
                } else {
                    item.amount = item.amount - 1
                    player.inventory.addItem(ItemStack(Material.BUCKET))
                }
            }
        }
    }
}