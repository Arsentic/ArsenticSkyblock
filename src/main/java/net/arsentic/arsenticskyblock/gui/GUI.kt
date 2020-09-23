package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class GUI {
    var inventory: Inventory? = null
        private set
    var islandID = 0
    var scheduler = 0

    constructor() {}
    constructor(island: Island?, size: Int, name: String?) {
        islandID = island.getId()
        inventory = Bukkit.createInventory(null, size, Utils.color(name))
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), { addContent() }, 0, 2)
    }

    constructor(size: Int, name: String?) {
        inventory = Bukkit.createInventory(null, size, Utils.color(name))
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), { addContent() }, 0, 2)
    }

    open fun addContent() {
        if (inventory!!.viewers.isEmpty()) return
        for (i in 0 until inventory!!.size) {
            if (inventory!!.getItem(i) == null || inventory!!.getItem(i)!!.type == Material.AIR) {
                setItem(i, Utils.makeItemHidden(getInventories().background))
            }
        }
    }

    fun setItem(i: Int, itemStack: ItemStack?) {
        if (inventory!!.getItem(i) == null || !inventory!!.getItem(i)!!.isSimilar(itemStack)) {
            inventory!!.setItem(i, itemStack)
        }
    }

    abstract fun onInventoryClick(e: InventoryClickEvent)
    val island: Island
        get() = getIslandManager().getIslandViaId(islandID)
}