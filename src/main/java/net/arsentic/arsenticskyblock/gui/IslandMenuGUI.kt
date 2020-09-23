package net.arsentic.arsenticskyblock.gui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class IslandMenuGUI(island: Island?) : GUI(island, getInventories().islandMenuGUISize, getInventories().islandMenuGUITitle), Listener {
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        if (getIslandManager().islands.containsKey(islandID)) {
            for (item in getInventories().menu.keySet()) {
                setItem(item.slot!!, Utils.makeItemHidden(item, island))
            }
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            val p = e.whoClicked as Player
            for (item in getInventories().menu.keySet()) {
                if (item.slot == e.slot) {
                    p.closeInventory()
                    Bukkit.getServer().dispatchCommand(e.whoClicked, getInventories().menu.get(item))
                    return
                }
            }
        }
    }

    init {
        getInstance().registerListeners(this)
    }
}