package net.arsentic.arsenticskyblock.gui

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class BoosterGUI(island: Island?) : GUI(island, getInventories().boosterGUISize, getInventories().boosterGUITitle), Listener {
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        if (getIslandManager().islands.containsKey(islandID)) {
            if (getBoosters().spawnerBooster.enabled) setItem(getBoosters().spawnerBooster.slot, makeItem(getInventories().spawner, island))
            if (getBoosters().farmingBooster.enabled) setItem(getBoosters().farmingBooster.slot, makeItem(getInventories().farming, island))
            if (getBoosters().experianceBooster.enabled) setItem(getBoosters().experianceBooster.slot, makeItem(getInventories().exp, island))
            if (getBoosters().flightBooster.enabled) setItem(getBoosters().flightBooster.slot, makeItem(getInventories().flight, island))
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            val p = e.whoClicked as Player
            val island: Island = getIslandManager().islands.get(islandID)
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            if (e.slot == getBoosters().spawnerBooster.slot && getBoosters().spawnerBooster.enabled) {
                if (getIsland().getSpawnerBooster() === 0) {
                    if (Utils.canBuy(p, getBoosters().spawnerBooster.vaultCost, getBoosters().spawnerBooster.crystalsCost)) {
                        getIsland().setSpawnerBooster(getBoosters().spawnerBooster.time)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().notEnoughCrystals.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().spawnerBoosterActive.replace("%prefix%", getConfiguration().prefix)))
                }
            }
            if (e.slot == getBoosters().farmingBooster.slot && getBoosters().farmingBooster.enabled) {
                if (getIsland().getFarmingBooster() === 0) {
                    if (Utils.canBuy(p, getBoosters().farmingBooster.vaultCost, getBoosters().farmingBooster.crystalsCost)) {
                        getIsland().setFarmingBooster(getBoosters().farmingBooster.time)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().notEnoughCrystals.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().farmingBoosterActive.replace("%prefix%", getConfiguration().prefix)))
                }
            }
            if (e.slot == getBoosters().experianceBooster.slot && getBoosters().experianceBooster.enabled) {
                if (getIsland().getExpBooster() === 0) {
                    if (Utils.canBuy(p, getBoosters().experianceBooster.vaultCost, getBoosters().experianceBooster.crystalsCost)) {
                        getIsland().setExpBooster(getBoosters().experianceBooster.time)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().notEnoughCrystals.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().expBoosterActive.replace("%prefix%", getConfiguration().prefix)))
                }
            }
            if (e.slot == getBoosters().flightBooster.slot && getBoosters().flightBooster.enabled) {
                if (getIsland().getFlightBooster() === 0) {
                    if (Utils.canBuy(p, getBoosters().flightBooster.vaultCost, getBoosters().flightBooster.crystalsCost)) {
                        getIsland().setFlightBooster(getBoosters().flightBooster.time)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().notEnoughCrystals.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().flightBoosterActive.replace("%prefix%", getConfiguration().prefix)))
                }
            }
        }
    }

    init {
        getInstance().registerListeners(this)
    }
}