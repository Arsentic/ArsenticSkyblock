package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.data.User
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

class PlayerTeleportListener : Listener {
    @EventHandler
    fun onPlayerTeleport(event: PlayerTeleportEvent) {
        try {
            val toLocation = event.to
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(toLocation) ?: return
            val player = event.player
            val user: User = User.Companion.getUser(player)
            if (user.islandID == island.getId()) return
            if (island.isVisit() && !island.isBanned(user) || user.bypassing) {
                if (!island.isInIsland(event.from)) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), { island.sendBorder(player) }, 1)
                    if (user.islandID != island.getId()) {
                        player.sendMessage(color(getMessages().visitingIsland.replace("%player%", getUser(island.getOwner()).name).replace("%prefix%", getConfiguration().prefix)))
                        for (pl in island.getMembers()) {
                            val p: Player = Bukkit.getPlayer(User.Companion.getUser(pl).name!!)
                            if (p != null && p.canSee(player)) {
                                p.sendMessage(color(getMessages().visitedYourIsland.replace("%player%", player.name).replace("%prefix%", getConfiguration().prefix)))
                            }
                        }
                    }
                }
            } else {
                event.isCancelled = true
                player.sendMessage(color(getMessages().playersIslandIsPrivate.replace("%prefix%", getConfiguration().prefix)))
            }
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}