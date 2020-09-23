package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.manager.IslandManager
import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class PlayerInteractListener : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        try {
            val player = event.player
            val playerLocation = player.location
            val islandManager: IslandManager = getIslandManager()
            if (!islandManager.isIslandWorld(playerLocation)) return
            val user: User = User.Companion.getUser(player)
            val block = event.clickedBlock
            if (event.action.toString().startsWith("RIGHT_CLICK")) {
                if (player.itemInHand != null) {
                    val crystals = Utils.getCrystals(player.itemInHand) * player.itemInHand.amount
                    if (crystals != 0) {
                        player.setItemInHand(null)
                        user.island.setCrystals(user.island.getCrystals() + crystals)
                        player.sendMessage(color(getMessages().depositedCrystals.replace("%amount%", crystals.toString() + "").replace("%prefix%", getConfiguration().prefix)))
                    }
                }
            }
            if (block != null) {
                val location = block.location
                val island: Island? = islandManager.getIslandViaLocation(location)
                if (island != null) {
                    if (!island.getPermissions(user).interact) {
                        event.isCancelled = true
                        return
                    }
                    val itemInHand = player.itemInHand
                    if (itemInHand.type == Material.BUCKET && island.failedGenerators.remove(location)) {
                        if (itemInHand.amount == 1) itemInHand.type = Material.LAVA_BUCKET else {
                            player.inventory.addItem(ItemStack(Material.LAVA_BUCKET))
                            player.itemInHand.amount = itemInHand.amount - 1
                        }
                        block.type = Material.AIR
                    }
                } else if (!user.bypassing) {
                    event.isCancelled = true
                    return
                }
            }
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        try {
            val player = event.player
            val user: User = User.Companion.getUser(player)
            val rightClicked = event.rightClicked
            val location = rightClicked.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            if (island.getPermissions(user).interact) return
            event.isCancelled = true
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}