package net.arsentic.arsenticskyblock.listeners

import com.google.common.collect.ImmutableMap
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPistonExtendEvent
import org.bukkit.event.block.BlockPistonRetractEvent

class BlockPistonListener : Listener {
    @EventHandler
    fun onBlockPistonExtend(event: BlockPistonExtendEvent) {
        try {
            val block = event.block
            val location = block.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            val face = event.direction
            for (extendedBlock in event.blocks) {
                val extendedBlockLocation = extendedBlock.location
                val offset = offsets[face]
                extendedBlockLocation.add(offset!![0].toDouble(), offset[1].toDouble(), offset[2].toDouble())
                if (!island.isInIsland(extendedBlockLocation)) {
                    event.isCancelled = true
                    return
                }
            }
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }

    @EventHandler
    fun onBlockPistonReact(event: BlockPistonRetractEvent) {
        try {
            val block = event.block
            val location = block.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            for (retractedBlock in event.blocks) {
                val retractedBlockLocation = retractedBlock.location
                if (!island.isInIsland(retractedBlockLocation)) {
                    event.isCancelled = true
                    return
                }
            }
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }

    companion object {
        private val offsets: Map<BlockFace, IntArray> = ImmutableMap.builder<BlockFace, IntArray>()
            .put(BlockFace.EAST, intArrayOf(1, 0, 0))
            .put(BlockFace.WEST, intArrayOf(-1, 0, 0))
            .put(BlockFace.UP, intArrayOf(0, 1, 0))
            .put(BlockFace.DOWN, intArrayOf(0, -1, 0))
            .put(BlockFace.SOUTH, intArrayOf(0, 0, 1))
            .put(BlockFace.NORTH, intArrayOf(0, 0, -1))
            .build()
    }
}