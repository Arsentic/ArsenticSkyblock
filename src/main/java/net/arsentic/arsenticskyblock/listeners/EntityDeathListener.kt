package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.manager.IslandManager
import net.arsentic.arsenticskyblock.mission.MissionType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class EntityDeathListener : Listener {
    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        try {
            val entity = event.entity
            val killer = entity.killer ?: return
            val location = killer.location
            val islandManager: IslandManager = getIslandManager()
            if (!islandManager.isIslandWorld(location)) return
            val user: User = User.Companion.getUser(killer)
            val userIsland: Island = user.island ?: return
            for (mission in getMissions().missions) {
                val levels: MutableMap<String?, Int> = userIsland.getMissionLevels()
                levels.putIfAbsent(mission.name, 1)
                val level = mission.levels[levels[mission.name]]
                if (level!!.type != MissionType.ENTITY_KILL) continue
                val conditions = level!!.conditions
                if (conditions!!.isEmpty() || conditions!!.contains(entity.name) || conditions!!.contains(entity.toString())) userIsland.addMission(mission.name, 1)
            }
            if (userIsland.getExpBooster() !== 0) event.droppedExp = event.droppedExp * 2
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}