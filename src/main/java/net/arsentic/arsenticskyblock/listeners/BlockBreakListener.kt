package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.manager.IslandManager
import net.arsentic.arsenticskyblock.mission.MissionType
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.material.Crops

class BlockBreakListener : Listener {
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        try {
            if (event.isCancelled) return
            val block = event.block
            val location = block.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            val player = event.player
            val user: User = User.Companion.getUser(player)
            if (user.islandID == island.getId()) {
                for (mission in getMissions().missions) {
                    val key: Int = island.getMissionLevels().computeIfAbsent(mission.name) { name -> 1 }
                    val levels = mission.levels
                    val level = levels!!.get(key) ?: continue
                    if (level.type != MissionType.BLOCK_BREAK) continue
                    val conditions = level.conditions
                    if (conditions!!.isEmpty()
                        ||
                        conditions!!.contains(Material.matchMaterial(block.type).name())
                        ||
                        (block.state.data is Crops
                                &&
                                conditions!!.contains((block.state.data as Crops).state.toString()))
                    ) island.addMission(mission.name, 1)
                }
            }
            if (!island.getPermissions(user).breakBlocks || !island.getPermissions(user).breakSpawners && Material.matchMaterial(block.type).equals(Material.SPAWNER)) {
                if (Material.matchMaterial(block.type).equals(Material.SPAWNER)) {
                    player.sendMessage(color(getMessages().noPermissionBreakSpawners.replace("%prefix%", getConfiguration().prefix)))
                } else {
                    player.sendMessage(color(getMessages().noPermissionBuild.replace("%prefix%", getConfiguration().prefix)))
                }
                event.isCancelled = true
            }
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onMonitorBreakBlock(event: BlockBreakEvent) {
        try {
            val block = event.block
            val location = block.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            if (Utils.isBlockValuable(block)) {
                val material = block.type
                val materialName: String = Material.matchMaterial(material).name()
                island.valuableBlocks.computeIfPresent(materialName) { name, original -> original - 1 }
                Bukkit.getScheduler().runTask(getInstance(), island::calculateIslandValue)
            }
            island.failedGenerators.remove(location)
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}