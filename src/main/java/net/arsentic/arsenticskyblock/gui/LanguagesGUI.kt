package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.*

class LanguagesGUI : GUI, Listener {
    var page = 0
    var root: LanguagesGUI? = null
    var pages: MutableMap<Int, LanguagesGUI>? = null
    var languages: MutableMap<Int, String>? = null

    constructor() {
        pages = HashMap()
        var i = 1
        while (i <= Math.ceil(getInstance().languages.size() / 45.00)) {
            pages[i] = LanguagesGUI(i, this)
            i++
        }
    }

    constructor(page: Int, root: LanguagesGUI?) : super(54, "&7Languages") {
        this.page = page
        this.root = root
        languages = HashMap()
        Bukkit.getPluginManager().registerEvents(this, getInstance())
    }

    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        languages!!.clear()
        var slot = 0
        var i = 0
        Collections.sort(getInstance().languages)
        for (language in getInstance().languages) {
            if (i >= (page - 1) * 45 && i < page * 54) {
                if (slot < 45) {
                    languages!![slot] = language
                    setItem(slot, Utils.makeItem(Material.PAPER, 1, "&b&l$language"))
                    slot++
                }
            }
            i++
        }
        setItem(inventory.size - 3, Utils.makeItem(getInventories().nextPage))
        setItem(inventory.size - 7, Utils.makeItem(getInventories().previousPage))
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            if (languages!!.containsKey(e.slot)) {
                getInstance().setLanguage(languages!![e.slot], e.whoClicked as Player)
            } else if (e.slot == inventory.size - 7) {
                if (root!!.pages!!.containsKey(page - 1)) {
                    e.whoClicked.openInventory(root!!.pages!![page - 1].getInventory())
                }
            } else if (e.slot == inventory.size - 3) {
                if (root!!.pages!!.containsKey(page + 1)) {
                    e.whoClicked.openInventory(root!!.pages!![page + 1].getInventory())
                }
            }
        }
    }
}