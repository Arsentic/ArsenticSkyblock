package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.vehicle.VehicleCreateEvent

class EntitySpawnListener : Listener {
    @EventHandler
    fun onEntitySpawn(event: EntitySpawnEvent) {
        val entity = event.entity
        val location = entity.location
        val islandManager: IslandManager = getIslandManager()
        val island: Island = islandManager.getIslandViaLocation(location) ?: return
        if (!getConfiguration().blockedEntities.contains(event.entityType)) return
        getInstance().entities.put(entity.uniqueId, island)
        monitorEntity(entity)
    }

    @EventHandler
    fun onVehicleSpawn(event: VehicleCreateEvent) {
        val vehicle = event.vehicle
        val islandManager: IslandManager = getIslandManager()
        val location = vehicle.location
        val island: Island = islandManager.getIslandViaLocation(location) ?: return
        if (!getConfiguration().blockedEntities.contains(vehicle.type)) return
        getInstance().entities.put(vehicle.uniqueId, island)
        monitorEntity(vehicle)
    }

    fun monitorEntity(entity: Entity?) {
        if (entity == null) return
        if (entity.isDead) return
        val uuid = entity.uniqueId
        val startingIsland: Island = getInstance().entities.get(uuid)
        if (startingIsland.isInIsland(entity.location)) {
            //The entity is still in the island, so make a scheduler to check again
            Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), { monitorEntity(entity) }, 20)
        } else {
            //The entity is not in the island, so remove it
            entity.remove()
            getInstance().entities.remove(uuid)
        }
    }
}