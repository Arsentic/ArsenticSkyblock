package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.arsentic.arsenticskyblock.configs.Upgrades.IslandUpgrade
import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class UpgradeGUI(island: Island?) : GUI(island, getInventories().upgradeGUISize, getInventories().upgradeGUITitle), Listener {
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        val island: Island? = island
        if (island != null) {
            if (IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.enabled) setItem(IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.slot, makeItemHidden(getInventories().size, getIsland()))
            if (IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.enabled) setItem(IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.slot, makeItemHidden(getInventories().member, getIsland()))
            if (IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.enabled) setItem(IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.slot, makeItemHidden(getInventories().warp, getIsland()))
            if (IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.enabled) setItem(IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.slot, makeItemHidden(getInventories().ores, getIsland()))
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            val p = e.whoClicked as Player
            if (e.slot == IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.slot && IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.enabled) {
                if (IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.containsKey(island.getSizeLevel() + 1)) {
                    val upgrade: IslandUpgrade = IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(island.getSizeLevel() + 1)
                    if (Utils.canBuy(p, upgrade.vaultCost.toDouble(), upgrade.crystalsCost)) {
                        island.setSizeLevel(island.getSizeLevel() + 1)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().notEnoughCrystals.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().maxLevelReached.replace("%prefix%", getConfiguration().prefix)))
                }
            }
            if (e.slot == IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.slot && IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.enabled) {
                if (IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.containsKey(island.getMemberLevel() + 1)) {
                    val upgrade: IslandUpgrade = IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.get(island.getMemberLevel() + 1)
                    if (Utils.canBuy(p, upgrade.vaultCost.toDouble(), upgrade.crystalsCost)) {
                        island.setMemberLevel(island.getMemberLevel() + 1)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().notEnoughCrystals.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().maxLevelReached.replace("%prefix%", getConfiguration().prefix)))
                }
            }
            if (e.slot == IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.slot && IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.enabled) {
                if (IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.upgrades.containsKey(island.getWarpLevel() + 1)) {
                    val upgrade: IslandUpgrade = IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.upgrades.get(island.getWarpLevel() + 1)
                    if (Utils.canBuy(p, upgrade.vaultCost.toDouble(), upgrade.crystalsCost)) {
                        island.setWarpLevel(island.getWarpLevel() + 1)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().notEnoughCrystals.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().maxLevelReached.replace("%prefix%", getConfiguration().prefix)))
                }
            }
            if (e.slot == IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.slot && IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.enabled) {
                if (IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.upgrades.containsKey(island.getOreLevel() + 1)) {
                    val upgrade: IslandUpgrade = IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.upgrades.get(island.getOreLevel() + 1)
                    if (Utils.canBuy(p, upgrade.vaultCost.toDouble(), upgrade.crystalsCost)) {
                        island.setOreLevel(island.getOreLevel() + 1)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().notEnoughCrystals.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().maxLevelReached.replace("%prefix%", getConfiguration().prefix)))
                }
            }
        }
    }

    init {
        getInstance().registerListeners(this)
    }
}