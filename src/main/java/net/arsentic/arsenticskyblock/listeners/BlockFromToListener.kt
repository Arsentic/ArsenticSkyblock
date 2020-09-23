package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.configs.Options
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFormEvent
import org.bukkit.event.block.BlockFromToEvent
import java.util.*

class BlockFromToListener : Listener {
    @EventHandler
    fun onBlockFromTo(event: BlockFromToEvent) {
        try {
            val block = event.block
            val location = block.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            val material = block.type
            val toBlock = event.toBlock
            val toLocation = toBlock.location
            if (material == Material.WATER || material == Material.LAVA) {
                val toIsland: Island? = islandManager.getIslandViaLocation(toLocation)
                if (island !== toIsland) event.isCancelled = true
            }
            if (!ArsenticSkyblock.Companion.getUpgrades()!!.oresUpgrade.enabled) return
            if (event.face == BlockFace.DOWN) return
            if (!isSurroundedByWater(toLocation)) return
            val oreLevel: Int = island.getOreLevel()
            val world = location.world ?: return
            val worldName = world.name
            val config: Options = getConfiguration()
            val islandOreUpgrades: List<String>
            islandOreUpgrades = if (worldName == config.worldName) ArsenticSkyblock.Companion.oreUpgradeCache.get(oreLevel)!! else if (worldName == config.netherWorldName) ArsenticSkyblock.Companion.netherOreUpgradeCache.get(oreLevel)!! else return
            Bukkit.getScheduler().runTask(getInstance(), Runnable {
                val toMaterial = toBlock.type
                if (!(toMaterial == Material.COBBLESTONE || toMaterial == Material.STONE)) return@runTask
                val random = Random()
                val oreUpgrade = islandOreUpgrades[random.nextInt(islandOreUpgrades.size)]
                val oreUpgradeMaterial: Material = Material.valueOf(oreUpgrade)
                val oreUpgradeMaterial: Material = oreUpgradeMaterial.parseMaterial(true) ?: return@runTask
                toBlock.type = oreUpgradeMaterial
                val blockState = toBlock.state
                blockState.update(true)
                if (Utils.isBlockValuable(toBlock)) {
                    val Material: Material = Material.matchMaterial(material)
                    island.valuableBlocks.compute(Material.name()) { name, original ->
                        if (original == null) return@compute 1
                        original + 1
                    }
                    island.calculateIslandValue()
                }
            })
        } catch (ex: Exception) {
            getInstance().sendErrorMessage(ex)
        }
    }

    @EventHandler
    fun onBlockFrom(event: BlockFormEvent) {
        try {
            val block = event.block
            val location = block.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            if (event.newState.type != Material.OBSIDIAN) return
            island.failedGenerators.add(location)
        } catch (ex: Exception) {
            getInstance().sendErrorMessage(ex)
        }
    }

    fun isSurroundedByWater(location: Location): Boolean {
        val world = location.world ?: return false
        val x = location.blockX
        val y = location.blockY
        val z = location.blockZ
        val coords = arrayOf(intArrayOf(x + 1, y, z), intArrayOf(x - 1, y, z), intArrayOf(x, y, z + 1), intArrayOf(x, y, z - 1), intArrayOf(x + 1, y + 1, z), intArrayOf(x - 1, y + 1, z), intArrayOf(x, y + 1, z + 1), intArrayOf(x, y + 1, z - 1), intArrayOf(x + 1, y - 1, z), intArrayOf(x - 1, y - 1, z), intArrayOf(x, y - 1, z + 1), intArrayOf(x, y - 1, z - 1))
        for (coord in coords) {
            val block = world.getBlockAt(coord[0], coord[1], coord[2])
            val material = block.type
            val name = material.name
            if (name.contains("WATER")) return true
        }
        return false
    }
}