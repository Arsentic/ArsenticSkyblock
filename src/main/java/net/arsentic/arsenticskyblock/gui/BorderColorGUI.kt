package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.Color
import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class BorderColorGUI(island: Island?) : GUI(island, getInventories().borderColorGUISize, getInventories().borderColorGUITitle), Listener {
    var red: ItemStack? = null
    var green: ItemStack? = null
    var blue: ItemStack? = null
    var off: ItemStack? = null
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        red = Utils.makeItem(getInventories().red)
        green = Utils.makeItem(getInventories().green)
        blue = Utils.makeItem(getInventories().blue)
        off = Utils.makeItem(getInventories().off)
        if (IridiumSkyblock.Companion.border!!.RedEnabled) setItem(10, red)
        if (IridiumSkyblock.Companion.border!!.BlueEnabled) setItem(12, blue)
        if (IridiumSkyblock.Companion.border!!.GreenEnabled) setItem(14, green)
        if (IridiumSkyblock.Companion.border!!.OffEnabled) setItem(16, off)
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            if (e.currentItem != null) {
                if (e.currentItem == blue) getIslandManager().getIslandViaId(islandID).setBorderColor(Color.Blue)
                if (e.currentItem == red) getIslandManager().getIslandViaId(islandID).setBorderColor(Color.Red)
                if (e.currentItem == green) getIslandManager().getIslandViaId(islandID).setBorderColor(Color.Green)
                if (e.currentItem == off) getIslandManager().getIslandViaId(islandID).setBorderColor(Color.Off)
                getIslandManager().getIslandViaId(islandID).sendBorder()
            }
        }
    }

    init {
        getInstance().registerListeners(this)
    }
}