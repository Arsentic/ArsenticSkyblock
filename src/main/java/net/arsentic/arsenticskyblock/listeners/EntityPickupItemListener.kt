package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.data.User
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPickupItemEvent

class EntityPickupItemListener : Listener {
    @EventHandler
    fun onEntityPickupItem(event: PlayerPickupItemEvent) {
        try {
            val item = event.item
            val location = item.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            val player = event.player
            val user: User = User.Companion.getUser(player)
            if (!island.getPermissions(user).pickupItems) event.isCancelled = true
        } catch (ex: Exception) {
            getInstance().sendErrorMessage(ex)
        }
    }
}