package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.manager.IslandManager
import net.arsentic.arsenticskyblock.mission.MissionType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerExpChangeEvent

class PlayerExpChangeListener : Listener {
    @EventHandler
    fun onPlayerExpChange(event: PlayerExpChangeEvent) {
        try {
            val player = event.player
            val location = player.location
            val islandManager: IslandManager = getIslandManager()
            if (!islandManager.isIslandWorld(location)) return
            val user: User = User.Companion.getUser(player)
            val island: Island = user.island ?: return
            for (mission in getMissions().missions) {
                val levels: MutableMap<String?, Int> = island.getMissionLevels()
                levels.putIfAbsent(mission.name, 1)
                val level = mission.levels[levels[mission.name]]
                if (level!!.type == MissionType.EXPERIENCE) island.addMission(mission.name, event.amount)
            }
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}