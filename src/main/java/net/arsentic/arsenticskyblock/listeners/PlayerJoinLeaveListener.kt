package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.manager.IslandManager
import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinLeaveListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        try {
            val player = event.player
            val plugin: ArsenticSkyblock = getInstance()
            if (player.isOp) {
                val latest: String = plugin.getLatest()
                if (plugin.getLatest() != null && getConfiguration().notifyAvailableUpdate
                    && latest != plugin.description.version
                ) {
                    val prefix: String = getConfiguration().prefix
                    player.sendMessage(Utils.color("$prefix &7This message is only seen by opped players."))
                    player.sendMessage(Utils.color("$prefix &7Newer version available: $latest"))
                }
            }
            val location = player.location
            val islandManager: IslandManager = getIslandManager()
            if (!islandManager.isIslandWorld(location)) return
            val user: User = User.Companion.getUser(player)
            user.name = player.name
            if (user.flying && (user.island == null || user.island.getFlightBooster() === 0)) {
                player.allowFlight = false
                player.isFlying = false
                user.flying = false
            }
            user.bypassing = false
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            Bukkit.getScheduler().runTaskLater(plugin, Runnable { island.sendBorder(player) }, 1)
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}