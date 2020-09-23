package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.CropState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockGrowEvent
import org.bukkit.material.Crops

class BlockGrowListener : Listener {
    @EventHandler
    fun onBlockGrow(event: BlockGrowEvent) {
        try {
            val block = event.block
            val location = block.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            if (island.getFarmingBooster() === 0) return
            val material = block.type
            if (!XBlock.isCrops(material)) return
            event.isCancelled = true
            val crops = Crops(CropState.RIPE)
            val blockState = block.state
            blockState.data = crops
            blockState.update()
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}