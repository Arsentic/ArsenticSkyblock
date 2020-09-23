package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.*

class WarpGUI(island: Island?) : GUI(island, getInventories().warpGUISize, getInventories().warpGUITitle), Listener {
    var warps: MutableMap<Int, Island.Warp> = HashMap<Int, Island.Warp>()
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        if (getIslandManager().islands.containsKey(islandID)) {
            val island: Island = getIslandManager().islands.get(islandID)
            var i = 9
            warps.clear()
            for (warp in island.getWarps()) {
                warps[i] = warp
                setItem(i, makeItem(getInventories().islandWarp, listOf(Utils.Placeholder("warp", warp.getName()))))
                i++
            }
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            val p = e.whoClicked as Player
            val u: User = User.Companion.getUser(p)
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            if (warps.containsKey(e.slot)) {
                val warp: Island.Warp? = warps[e.slot]
                if (e.click == ClickType.RIGHT) {
                    u.island.removeWarp(warps[e.slot])
                    inventory.clear()
                    addContent()
                } else {
                    if (warp.getPassword().isEmpty()) {
                        p.teleport(warp.getLocation())
                        p.sendMessage(color(getMessages().teleporting.replace("%prefix%", getConfiguration().prefix)))
                    } else {
                        p.sendMessage(color(getMessages().enterPassword.replace("%prefix%", getConfiguration().prefix)))
                        u.warp = warp
                    }
                }
                p.closeInventory()
            }
        }
    }

    init {
        getInstance().registerListeners(this)
    }
}