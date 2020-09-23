package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.ArsenticSkyblock
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
            if (ArsenticSkyblock.Companion.getUpgrades()!!.sizeUpgrade.enabled) setItem(ArsenticSkyblock.Companion.getUpgrades()!!.sizeUpgrade.slot, makeItemHidden(getInventories().size, getIsland()))
            if (ArsenticSkyblock.Companion.getUpgrades()!!.memberUpgrade.enabled) setItem(ArsenticSkyblock.Companion.getUpgrades()!!.memberUpgrade.slot, makeItemHidden(getInventories().member, getIsland()))
            if (ArsenticSkyblock.Companion.getUpgrades()!!.warpUpgrade.enabled) setItem(ArsenticSkyblock.Companion.getUpgrades()!!.warpUpgrade.slot, makeItemHidden(getInventories().warp, getIsland()))
            if (ArsenticSkyblock.Companion.getUpgrades()!!.oresUpgrade.enabled) setItem(ArsenticSkyblock.Companion.getUpgrades()!!.oresUpgrade.slot, makeItemHidden(getInventories().ores, getIsland()))
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            val p = e.whoClicked as Player
            if (e.slot == ArsenticSkyblock.Companion.getUpgrades()!!.sizeUpgrade.slot && ArsenticSkyblock.Companion.getUpgrades()!!.sizeUpgrade.enabled) {
                if (ArsenticSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.containsKey(island.getSizeLevel() + 1)) {
                    val upgrade: IslandUpgrade = ArsenticSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(island.getSizeLevel() + 1)
                    if (Utils.canBuy(p, upgrade.vaultCost.toDouble(), upgrade.crystalsCost)) {
                        island.setSizeLevel(island.getSizeLevel() + 1)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().notEnoughCrystals.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().maxLevelReached.replace("%prefix%", getConfiguration().prefix)))
                }
            }
            if (e.slot == ArsenticSkyblock.Companion.getUpgrades()!!.memberUpgrade.slot && ArsenticSkyblock.Companion.getUpgrades()!!.memberUpgrade.enabled) {
                if (ArsenticSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.containsKey(island.getMemberLevel() + 1)) {
                    val upgrade: IslandUpgrade = ArsenticSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.get(island.getMemberLevel() + 1)
                    if (Utils.canBuy(p, upgrade.vaultCost.toDouble(), upgrade.crystalsCost)) {
                        island.setMemberLevel(island.getMemberLevel() + 1)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().notEnoughCrystals.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().maxLevelReached.replace("%prefix%", getConfiguration().prefix)))
                }
            }
            if (e.slot == ArsenticSkyblock.Companion.getUpgrades()!!.warpUpgrade.slot && ArsenticSkyblock.Companion.getUpgrades()!!.warpUpgrade.enabled) {
                if (ArsenticSkyblock.Companion.getUpgrades()!!.warpUpgrade.upgrades.containsKey(island.getWarpLevel() + 1)) {
                    val upgrade: IslandUpgrade = ArsenticSkyblock.Companion.getUpgrades()!!.warpUpgrade.upgrades.get(island.getWarpLevel() + 1)
                    if (Utils.canBuy(p, upgrade.vaultCost.toDouble(), upgrade.crystalsCost)) {
                        island.setWarpLevel(island.getWarpLevel() + 1)
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().notEnoughCrystals.replace("%prefix%", getConfiguration().prefix)))
                    }
                } else {
                    e.whoClicked.sendMessage(color(getMessages().maxLevelReached.replace("%prefix%", getConfiguration().prefix)))
                }
            }
            if (e.slot == ArsenticSkyblock.Companion.getUpgrades()!!.oresUpgrade.slot && ArsenticSkyblock.Companion.getUpgrades()!!.oresUpgrade.enabled) {
                if (ArsenticSkyblock.Companion.getUpgrades()!!.oresUpgrade.upgrades.containsKey(island.getOreLevel() + 1)) {
                    val upgrade: IslandUpgrade = ArsenticSkyblock.Companion.getUpgrades()!!.oresUpgrade.upgrades.get(island.getOreLevel() + 1)
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