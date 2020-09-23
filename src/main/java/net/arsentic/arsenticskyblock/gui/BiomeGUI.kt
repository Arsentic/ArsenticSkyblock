package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.util.Utils
import org.apache.commons.lang.WordUtils
import org.bukkit.block.Biome
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.*

class BiomeGUI : GUI, Listener {
    var page = 0
    var root: BiomeGUI? = null
    var pages: MutableMap<Int, BiomeGUI> = HashMap()
    var biomes: MutableMap<Int, XBiome> = HashMap<Int, XBiome>()

    constructor(island: Island?) {
        getInstance().registerListeners(this)
        val size = (Math.floor(Biome.values().size / (getInventories().biomeGUISize as Double - 9)) + 1).toInt()
        for (i in 1..size) {
            pages[i] = BiomeGUI(island, i, this)
        }
    }

    constructor(island: Island?, page: Int, root: BiomeGUI?) : super(island, getInventories().biomeGUISize, getInventories().biomeGUITitle) {
        this.page = page
        this.root = root
    }

    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        var i = 0
        var slot = 0
        for (biome in getConfiguration().biomes) {
            if (biome.parseBiome() != null) {
                if (i >= 45 * (page - 1) && slot < 45) {
                    setItem(slot, makeItem(getInventories().biome, listOf(Utils.Placeholder("biome", WordUtils.capitalize(biome.name().toLowerCase().replace("_", " "))))))
                    biomes[slot] = biome
                    slot++
                }
                i++
            }
        }
        setItem(inventory.size - 3, Utils.makeItem(getInventories().nextPage))
        setItem(inventory.size - 7, Utils.makeItem(getInventories().previousPage))
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (inventory == null) {
            for (gui in pages.values) {
                gui.onInventoryClick(e)
            }
        } else {
            if (e.inventory == inventory) {
                e.isCancelled = true
                if (e.clickedInventory == null || e.clickedInventory != inventory) return
                if (e.slot == inventory.size - 3) {
                    if (root!!.pages.containsKey(page + 1)) {
                        e.whoClicked.openInventory(root!!.pages[page + 1].getInventory())
                    }
                }
                if (e.slot == inventory.size - 7) {
                    if (root!!.pages.containsKey(page - 1)) {
                        e.whoClicked.openInventory(root!!.pages[page - 1].getInventory())
                    }
                }
                if (biomes.containsKey(e.slot)) {
                    island.setBiome(biomes[e.slot])
                }
            }
        }
    }
}