package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.configs.Missions
import net.arsentic.arsenticskyblock.manager.ConfigManager
import net.arsentic.arsenticskyblock.manager.IslandManager
import net.arsentic.arsenticskyblock.manager.MissionManager
import net.arsentic.arsenticskyblock.mission.MissionType
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.data.Ageable
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.material.Crops

class BlockBreakListeners(val plugin: ArsenticSkyblock) : Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        val block = event.block
        val location = block.location
        val islandManager = plugin.getManager(IslandManager::class)
        val island = islandManager.getIslandFromLocation(location) ?: return
        val player = event.player

        val user = islandManager.getUser(player) ?: return

        if (user.islandID == island.id) {
            for (mission in plugin.getManager(ConfigManager::class).getConfig(Missions::class).missions) {
                val key: Int = island.getMissionLevels().computeIfAbsent(mission.name) { name -> 1 }
                val levels = mission.levels
                val level = levels[key] ?: continue

                if (level.type != MissionType.BLOCK_BREAK)
                    continue

                val conditions = level.conditions

                if (conditions.isEmpty()
                    || conditions.contains(Material.matchMaterial(block.type).name())
                    || (block.state.data is Ageable && conditions.contains((block.state.data as Crops).state.toString()))
                ) plugin.getManager(MissionManager::class).addMission(mission.name, 1)
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