package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.configs.Options
import net.arsentic.arsenticskyblock.manager.IslandManager
import net.arsentic.arsenticskyblock.mission.MissionType
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.material.Crops

class BlockPlaceListener : Listener {
    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        try {
            val block = event.block
            val location = block.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island? = islandManager.getIslandViaLocation(location)
            if (island == null) {
                val user: User = User.Companion.getUser(event.player)
                if (islandManager.isIslandWorld(event.block.world)) {
                    if (!user.bypassing) {
                        event.isCancelled = true
                    }
                }
                return
            }
            val player = event.player
            val user: User = User.Companion.getUser(player)
            val material = block.type
            val Material: Material = Material.matchMaterial(material)
            val config: Options = getConfiguration()
            val max = config.limitedBlocks[Material]
            if (max != null) {
                if (island.valuableBlocks.getOrDefault(Material.name(), 0) >= max) {
                    player.sendMessage(
                        color(
                            getMessages().blockLimitReached
                                .replace("%prefix%", config.prefix)
                        )
                    )
                    event.isCancelled = true
                    return
                }
            }
            if (user.islandID == island.getId()) {
                for (mission in getMissions().missions) {
                    val levels: MutableMap<String?, Int> = island.getMissionLevels()
                    levels.putIfAbsent(mission.name, 1)
                    val level = mission.levels.get(levels[mission.name]) ?: continue
                    if (level.type != MissionType.BLOCK_PLACE) continue
                    val conditions = level.conditions
                    if (conditions!!.isEmpty()
                        ||
                        conditions!!.contains(Material.name())
                        ||
                        conditions!!.contains((block.state.data as Crops).state.toString())
                    ) island.addMission(mission.name, 1)
                }
            }
            if (!island.getPermissions(user).placeBlocks) event.isCancelled = true
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onMonitorBlockPlace(event: BlockPlaceEvent) {
        try {
            val block = event.block
            val location = block.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            if (!Utils.isBlockValuable(block)) return
            val material = block.type
            val Material: Material = Material.matchMaterial(material)
            island.valuableBlocks.compute(Material.name()) { name, original ->
                if (original == null) return@compute 1
                original + 1
            }
            Bukkit.getScheduler().runTask(getInstance(), island::calculateIslandValue)
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}