package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class ConfirmationGUI : GUI, Listener {
    var runnable: Runnable

    constructor(island: Island?, runnable: Runnable, action: String?) : super(island, 27, getInventories().confirmationGUITitle.replace("%action%", action)) {
        getInstance().registerListeners(this)
        this.runnable = runnable
        for (i in 0 until inventory.size) {
            setItem(i, Utils.makeItemHidden(getInventories().background))
        }
        setItem(12, Utils.makeItem(Material.LIME_STAINED_GLASS_PANE, 1, getMessages().yes))
        setItem(14, Utils.makeItem(Material.RED_STAINED_GLASS_PANE, 1, getMessages().no))
    }

    constructor(runnable: Runnable, action: String?) : super(27, getInventories().confirmationGUITitle.replace("%action%", action)) {
        getInstance().registerListeners(this)
        this.runnable = runnable
        for (i in 0 until inventory.size) {
            setItem(i, Utils.makeItemHidden(getInventories().background))
        }
        setItem(12, Utils.makeItem(Material.LIME_STAINED_GLASS_PANE, 1, getMessages().yes))
        setItem(14, Utils.makeItem(Material.RED_STAINED_GLASS_PANE, 1, getMessages().no))
    }

    override fun addContent() {}
    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            if (e.slot == 12) {
                e.whoClicked.closeInventory()
                runnable.run()
            } else if (e.slot == 14) {
                e.whoClicked.closeInventory()
            }
        }
    }
}