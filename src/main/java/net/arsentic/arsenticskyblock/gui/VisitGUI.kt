package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.ArsenticSkyblock
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

class VisitGUI(page: Int) : GUI(getInventories().visitGUISize, getInventories().visitGUITitle), Listener {
    var islands: MutableMap<Int, Int> = HashMap()
    private val page: Int
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        val top: List<Island?>? = Utils.getIslands()
        var slot = 0
        var i = 45 * (page - 1)
        while (slot < 45) {
            if (top!!.size > i && i >= 0) {
                val island: Island? = top[i]
                if (island.isVisit()) {
                    val owner: User = getUser(island.getOwner())
                    val head: ItemStack = makeItem(getInventories().visitisland, listOf(Utils.Placeholder("player", owner.name), Utils.Placeholder("name", island.getName()), Utils.Placeholder("rank", Utils.getIslandRank(island).toString() + ""), Utils.Placeholder("votes", NumberFormat.getInstance().format(island.getVotes())), Utils.Placeholder("value", NumberFormat.getInstance().format(island.getValue()).toString() + "")))
                    islands[slot] = island.getId()
                    setItem(slot, head)
                    slot++
                } else {
                    setItem(slot, Utils.makeItemHidden(getInventories().background))
                }
                i++
            } else {
                setItem(slot, Utils.makeItemHidden(getInventories().background))
                slot++
            }
        }
        setItem(inventory.size - 3, Utils.makeItem(getInventories().nextPage))
        setItem(inventory.size - 7, Utils.makeItem(getInventories().previousPage))
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            if (islands.containsKey(e.slot)) {
                val island: Island = getIslandManager().getIslandViaId(islands[e.slot])
                val u: User = User.Companion.getUser(e.whoClicked as OfflinePlayer)
                if (island.isVisit() || u.bypassing) {
                    if (e.click == ClickType.RIGHT) {
                        if (island.hasVoted(u)) {
                            island.removeVote(u)
                        } else {
                            island.addVote(u)
                        }
                    } else {
                        e.whoClicked.closeInventory()
                        island.teleportHome(e.whoClicked as Player)
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().playersIslandIsPrivate.replace("%prefix%", getConfiguration().prefix)))
                }
            } else if (e.slot == inventory.size - 7) {
                if (ArsenticSkyblock.Companion.visitGUI!!.containsKey(page - 1)) e.whoClicked.openInventory(ArsenticSkyblock.Companion.visitGUI!!.get(page - 1).getInventory())
            } else if (e.slot == inventory.size - 3) {
                if (ArsenticSkyblock.Companion.visitGUI!!.containsKey(page + 1)) e.whoClicked.openInventory(ArsenticSkyblock.Companion.visitGUI!!.get(page + 1).getInventory())
            }
        }
    }

    init {
        getInstance().registerListeners(this)
        this.page = page
    }
}