package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.SpawnerSpawnEvent

class SpawnerSpawnListener : Listener {
    @EventHandler
    fun onSpawnerSpawn(event: SpawnerSpawnEvent) {
        try {
            val location = event.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            if (island.getSpawnerBooster() === 0) return
            val plugin: ArsenticSkyblock = getInstance()
            val spawner = event.spawner
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, { spawner.delay = spawner.delay / 2 }, 0)
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}