package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.Bukkit
import org.bukkit.block.CreatureSpawner
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent
import java.util.*

class EntityExplodeListener : Listener {
    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        try {
            val entity = event.entity
            val location = entity.location
            val islandManager: IslandManager = getIslandManager()
            if (!islandManager.isIslandWorld(location)) return
            if (!getConfiguration().allowExplosions) event.isCancelled = true
        } catch (ex: Exception) {
            getInstance().sendErrorMessage(ex)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onMonitorEntityExplode(event: EntityExplodeEvent) {
        try {
            val entity = event.entity
            val location = entity.location
            val islandManager: IslandManager = getIslandManager()
            if (!islandManager.isIslandWorld(location)) return
            val uuid = entity.uniqueId
            val plugin: ArsenticSkyblock = getInstance()
            val entities: MutableMap<UUID?, Island?>? = plugin.entities
            var island: Island? = entities!![uuid]
            if (island != null && island.isInIsland(location)) {
                event.isCancelled = true
                entity.remove()
                entities.remove(uuid)
                return
            }
            island = islandManager.getIslandViaLocation(location)
            if (island == null) return
            for (block in event.blockList()) {
                if (!island.isInIsland(block.location)) {
                    val state = block.state
                    ArsenticSkyblock.Companion.nms.setBlockFast(block, 0, 0.toByte())
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin) { state.update(true, true) }
                }
                if (!Utils.isBlockValuable(block)) continue
                if (block.state !is CreatureSpawner) {
                    val material = block.type
                    val Material: Material = Material.matchMaterial(material)
                    island.valuableBlocks.computeIfPresent(Material.name()) { name, original -> original - 1 }
                }
            }
            island.calculateIslandValue()
        } catch (ex: Exception) {
            getInstance().sendErrorMessage(ex)
        }
    }
}