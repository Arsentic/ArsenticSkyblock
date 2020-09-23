package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.LeavesDecayEvent

class LeafDecayListener : Listener {
    @EventHandler
    fun onLeafDecay(event: LeavesDecayEvent) {
        try {
            val block = event.block
            val location = block.location
            val islandManager: IslandManager = getIslandManager()
            if (!islandManager.isIslandWorld(location)) return
            if (!getConfiguration().disableLeafDecay) return
            event.isCancelled = true
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}