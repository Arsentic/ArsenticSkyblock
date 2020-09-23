package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.data.User
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.text.NumberFormat
import java.util.*

class TopGUI : GUI(getInventories().topGUISize, getInventories().topGUITitle), Listener {
    var islands: MutableMap<Int, Int> = HashMap()
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        val top: List<Island> = Utils.getTopIslands()
        for (i in getConfiguration().islandTopSlots.keySet()) {
            if (top.size >= i) {
                val island: Island = top[i - 1]
                val owner: User = getUser(island.getOwner())
                val placeholders: ArrayList<Utils.Placeholder> = ArrayList<Utils.Placeholder>(listOf<Any>(Placeholder("player", owner.name), Placeholder("name", island.getName()), Placeholder("rank", i.toString() + ""), Placeholder("level", NumberFormat.getInstance().format(island.getValue() / getConfiguration().valuePerLevel).toString() + ""), Placeholder("value", NumberFormat.getInstance().format(island.getValue()).toString() + "")))
                for (item in getBlockValues().blockvalue.keySet()) {
                    placeholders.add(Placeholder(item.name().toString() + "_amount", "" + island.valuableBlocks.getOrDefault(item.name(), 0)))
                }
                for (item in getBlockValues().spawnervalue.keySet()) {
                    placeholders.add(Placeholder(item + "_amount", "" + island.spawners.getOrDefault(item, 0)))
                }
                val head: ItemStack = Utils.makeItem(getInventories().topisland, placeholders)
                islands[getConfiguration().islandTopSlots.get(i)] = island.getId()
                setItem(getConfiguration().islandTopSlots.get(i), head)
            } else {
                setItem(getConfiguration().islandTopSlots.get(i), Utils.makeItemHidden(getInventories().background))
            }
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            if (islands.containsKey(e.slot)) {
                e.whoClicked.closeInventory()
                val island: Island = getIslandManager().getIslandViaId(islands[e.slot])
                if (island.isVisit() || User.Companion.getUser(e.whoClicked as OfflinePlayer)!!.bypassing) {
                    island.teleportHome(e.whoClicked as Player)
                } else {
                    e.whoClicked.sendMessage(Utils.color(getMessages().playersIslandIsPrivate.replace("%prefix%", getConfiguration().prefix)))
                }
            }
        }
    }

    init {
        getInstance().registerListeners(this)
    }
}