package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.text.NumberFormat
import java.util.*

class CoopGUI(island: Island?) : GUI(island, getInventories().coopGUISize, getInventories().coopGUITitle), Listener {
    var islands: MutableMap<Int, Int> = HashMap()
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        islands.clear()
        val island: Island? = island
        if (island != null) {
            var i = 0
            for (id in island.getCoop()) {
                val `is`: Island = getIslandManager().getIslandViaId(id)
                if (`is` != null) {
                    islands[i] = id
                    val user: User = getUser(`is`.getOwner())
                    val head: ItemStack = makeItem(getInventories().islandcoop, listOf(Utils.Placeholder("player", user.name), Utils.Placeholder("name", `is`.getName()), Utils.Placeholder("rank", Utils.getIslandRank(`is`).toString() + ""), Utils.Placeholder("value", NumberFormat.getInstance().format(`is`.getValue()).toString() + "")))
                    setItem(i, head)
                    i++
                } else {
                    island.removeCoop(id)
                }
            }
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            if (islands.containsKey(e.slot)) {
                val island: Island = getIslandManager().getIslandViaId(islands[e.slot])
                val u: User = User.Companion.getUser(e.whoClicked as OfflinePlayer)
                if (e.click == ClickType.RIGHT) {
                    if (u.bypassing || u.island.getPermissions(u.getRole()).coop) {
                        getIsland().removeCoop(island)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().noPermission.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    if (island.isVisit() || u.bypassing) {
                        island.teleportHome(e.whoClicked as Player)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().playersIslandIsPrivate.replace("%prefix%", getConfiguration().prefix)))
                    }
                }
            }
        }
    }

    init {
        getInstance().registerListeners(this)
    }
}