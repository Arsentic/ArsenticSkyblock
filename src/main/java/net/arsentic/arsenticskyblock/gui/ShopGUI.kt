package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.configs.Shop.ShopItem
import net.arsentic.arsenticskyblock.configs.Shop.ShopObject
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class ShopGUI : GUI, Listener {
    var root: ShopGUI? = null
    var shop: ShopObject? = null
    var page = 0
    var shops: MutableMap<Int, ShopGUI> = HashMap()
    var items: MutableMap<Int, ShopItem?> = HashMap()

    constructor() : super(getInventories().shopGUISize, getInventories().shopGUITitle) {
        getInstance().registerListeners(this)
    }

    constructor(shop: ShopObject?, page: Int, root: ShopGUI?) : super(getInventories().shopGUISize, getInventories().shopGUITitle) {
        this.shop = shop
        this.page = page
        this.root = root
    }

    constructor(shop: ShopObject?, root: ShopGUI?) {
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), { addPages() }, 0, 5)
        this.shop = shop
        this.root = root
    }

    fun addPages() {
        for (item in shop!!.items) {
            if (item == null) continue
            if (!shops.containsKey(item.page)) {
                shops[item.page] = ShopGUI(shop, item.page, this)
            }
        }
    }

    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        if (!getConfiguration().islandShop) return
        if (shop == null) {
            for (shop in getShop().shop) {
                setItem(shop.slot, Utils.makeItem(shop.display, 1, shop.displayName))
                if (!shops.containsKey(shop.slot)) {
                    shops[shop.slot] = ShopGUI(shop, this)
                }
            }
        } else {
            for (item in shop!!.items) {
                if (item!!.page == page) {
                    items[item.slot] = item
                    setItem(item.slot, Utils.makeItem(item.material, item.amount, item.displayName, color(processMultiplePlaceholders(getShop().lore, listOf(Utils.Placeholder("buyvaultprice", item.buyVault.toString() + ""), Utils.Placeholder("sellvaultprice", item.sellVault.toString() + ""), Utils.Placeholder("buycrystalprice", item.buyCrystals.toString() + ""), Utils.Placeholder("sellcrystalprice", item.sellCrystals.toString() + ""))))))
                }
            }
            setItem(inventory.size - 3, Utils.makeItem(getInventories().nextPage))
            setItem(inventory.size - 5, Utils.makeItem(getInventories().back))
            setItem(inventory.size - 7, Utils.makeItem(getInventories().previousPage))
        }
    }

    fun contains(p: Player, materials: Material?, amount: Int): Boolean {
        var total = 0
        for (item in p.inventory.contents) {
            if (item == null) continue
            if (materials.isSimilar(item)) {
                total += item.amount
            }
        }
        return total >= amount
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            if (shop == null) {
                if (shops.containsKey(e.slot)) {
                    if (shops[e.slot]!!.shops.containsKey(1)) { // This should always be called, but just incase the user configured the plugin incorrectly
                        e.whoClicked.openInventory(shops[e.slot]!!.shops[1].getInventory())
                    }
                }
            } else {
                if (items.containsKey(e.slot)) {
                    val item = items[e.slot]
                    if (e.click == ClickType.RIGHT) {
                        //Can we sell this?
                        if (item!!.sellVault > 0 || item.sellCrystals > 0) {
                            if (contains(e.whoClicked as Player, item.material, item.amount)) {
                                var removed = 0
                                var index = 0
                                for (itemStack in e.whoClicked.inventory.contents) {
                                    if (removed >= item.amount) break
                                    if (itemStack != null) {
                                        if (item.material.isSimilar(itemStack)) {
                                            if (removed + itemStack.amount <= item.amount) {
                                                removed += itemStack.amount
                                                e.whoClicked.inventory.setItem(index, null)
                                            } else {
                                                itemStack.amount = itemStack.amount - (item.amount - removed)
                                                removed += item.amount
                                            }
                                        }
                                    }
                                    index++
                                }
                                Utils.pay(e.whoClicked as Player, item.sellVault, item.sellCrystals)
                            } else {
                                e.whoClicked.sendMessage(color(getMessages().cantSell.replace("%prefix%", getConfiguration().prefix)))
                            }
                        } else {
                            e.whoClicked.sendMessage(color(getMessages().cannotSellItem.replace("%prefix%", getConfiguration().prefix)))
                        }
                    } else {
                        if (Utils.canBuy(e.whoClicked as Player, item!!.buyVault, item.buyCrystals)) {
                            if (item.commands != null) {
                                for (Command in item.commands!!) {
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Command!!.replace("%player%", e.whoClicked.name))
                                }
                            } else {
                                val itemStack: ItemStack = item.material.parseItem(true)
                                itemStack.amount = item.amount
                                e.whoClicked.inventory.addItem(itemStack)
                            }
                        } else {
                            e.whoClicked.sendMessage(color(getMessages().cantBuy.replace("%prefix%", getConfiguration().prefix)))
                        }
                    }
                }
                if (e.slot == inventory.size - 3) {
                    if (root!!.shops.containsKey(page + 1)) {
                        e.whoClicked.openInventory(root!!.shops[page + 1].getInventory())
                    }
                }
                if (e.slot == inventory.size - 5) {
                    e.whoClicked.openInventory(root!!.root.getInventory())
                }
                if (e.slot == inventory.size - 7) {
                    if (root!!.shops.containsKey(page - 1)) {
                        e.whoClicked.openInventory(root!!.shops[page - 1].getInventory())
                    }
                }
            }
        }
        for (gui in shops.values) {
            gui.onInventoryClick(e)
        }
    }
}