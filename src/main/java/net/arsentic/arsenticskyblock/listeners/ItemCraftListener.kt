package net.arsentic.arsenticskyblock.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.PrepareItemCraftEvent

class ItemCraftListener : Listener {
    @EventHandler
    fun onItemCraft(event: PrepareItemCraftEvent) {
        try {
            val inventory = event.inventory
            if (inventory.result == null) return
            for (itemStack in inventory.contents) {
                if (Utils.getCrystals(itemStack) == 0) continue
                inventory.result = null
                return
            }
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.inventory.type == InventoryType.ANVIL && Utils.getCrystals(event.currentItem) != 0) {
            event.isCancelled = true
        }
    }
}