package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.manager.IslandManager
import net.arsentic.arsenticskyblock.mission.MissionType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent

class PlayerFishListener : Listener {
    @EventHandler
    fun onPlayerFish(event: PlayerFishEvent) {
        try {
            val player = event.player
            val location = player.location
            val islandManager: IslandManager = getIslandManager()
            if (!islandManager.isIslandWorld(location)) return
            if (event.state != PlayerFishEvent.State.CAUGHT_FISH) return
            val user: User = User.Companion.getUser(player)
            val island: Island = user.island ?: return
            for (mission in getMissions().missions) {
                val levels: MutableMap<String?, Int> = island.getMissionLevels()
                levels.putIfAbsent(mission.name, 1)
                val level = mission.levels[levels[mission.name]]
                if (level!!.type == MissionType.FISH_CATCH) island.addMission(mission.name, 1)
            }
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}