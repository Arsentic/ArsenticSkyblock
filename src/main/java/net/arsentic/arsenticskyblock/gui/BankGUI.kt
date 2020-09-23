package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.data.User
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

class BankGUI(island: Island?) : GUI(island, getInventories().bankGUISize, getInventories().bankGUITitle), Listener {
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        if (getIslandManager().islands.containsKey(islandID)) {
            setItem(if (getInventories().experience.slot == null) 11 else getInventories().experience.slot, makeItemHidden(getInventories().experience, island))
            setItem(if (getInventories().crystals.slot == null) 13 else getInventories().crystals.slot, makeItemHidden(getInventories().crystals, island))
            setItem(if (getInventories().money.slot == null) 15 else getInventories().money.slot, makeItemHidden(getInventories().money, island))
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            val p = e.whoClicked as Player
            val island: Island? = island
            val u: User = User.Companion.getUser(p)
            if (e.slot == (if (getInventories().experience.slot == null) 11 else getInventories().experience.slot)) {
                if (e.click == ClickType.SHIFT_LEFT) {
                    if (island.getPermissions(if (u.islandID == island.getId() || island.isCoop(u.island)) if (island.isCoop(u.island)) Role.Member else u.getRole() else Role.Visitor).withdrawBank || u.bypassing) {
                        Utils.setTotalExperience(p, Utils.getTotalExperience(p) + island.exp)
                        island.exp = 0
                    }
                } else if (e.click == ClickType.SHIFT_RIGHT) {
                    island.exp += Utils.getTotalExperience(p)
                    Utils.setTotalExperience(p, 0)
                } else if (e.click == ClickType.RIGHT) {
                    if (Utils.getTotalExperience(p) > 100) {
                        island.exp += 100
                        Utils.setTotalExperience(p, Utils.getTotalExperience(p) - 100)
                    } else {
                        island.exp += Utils.getTotalExperience(p)
                        Utils.setTotalExperience(p, 0)
                    }
                } else if (e.click == ClickType.LEFT) {
                    if (island.getPermissions(if (u.islandID == island.getId() || island.isCoop(u.island)) if (island.isCoop(u.island)) Role.Member else u.getRole() else Role.Visitor).withdrawBank || u.bypassing) {
                        if (island.exp > 100) {
                            island.exp -= 100
                            Utils.setTotalExperience(p, Utils.getTotalExperience(p) + 100)
                        } else {
                            Utils.setTotalExperience(p, Utils.getTotalExperience(p) + island.exp)
                            island.exp = 0
                        }
                    }
                }
            }
            if (e.slot == (if (getInventories().crystals.slot == null) 13 else getInventories().crystals.slot)) {
                if (e.click == ClickType.SHIFT_LEFT) {
                    if (island.getPermissions(if (u.islandID == island.getId() || island.isCoop(u.island)) if (island.isCoop(u.island)) Role.Member else u.getRole() else Role.Visitor).withdrawBank || u.bypassing) {
                        if (island.getCrystals() > 0) {
                            if (p.inventory.firstEmpty() != -1) {
                                p.inventory.addItem(getCrystals(island.getCrystals()))
                                island.setCrystals(0)
                            } else {
                                p.sendMessage(
                                    color(
                                        getMessages().inventoryFull
                                            .replace("%prefix%", getConfiguration().prefix)
                                    )
                                )
                            }
                        }
                    }
                } else if (e.click == ClickType.SHIFT_RIGHT) {
                    var i = 0
                    for (itemStack in p.inventory.contents) {
                        if (itemStack == null) {
                            i++
                            continue
                        }
                        val crystals = Utils.getCrystals(itemStack) * itemStack.amount
                        if (crystals != 0) {
                            island.setCrystals(island.getCrystals() + crystals)
                            p.inventory.clear(i)
                        }
                        i++
                    }
                } else if (e.click == ClickType.RIGHT) {
                    var i = 0
                    for (itemStack in p.inventory.contents) {
                        if (itemStack == null) {
                            i++
                            continue
                        }
                        val crystals = Utils.getCrystals(itemStack) * itemStack.amount
                        if (crystals != 0) {
                            island.setCrystals(island.getCrystals() + crystals)
                            p.inventory.clear(i)
                            return
                        }
                        i++
                    }
                } else if (e.click == ClickType.LEFT) {
                    if (island.getPermissions(if (u.islandID == island.getId() || island.isCoop(u.island)) if (island.isCoop(u.island)) Role.Member else u.getRole() else Role.Visitor).withdrawBank || u.bypassing) {
                        if (island.getCrystals() > 0) {
                            if (p.inventory.firstEmpty() != -1) {
                                island.setCrystals(island.getCrystals() - 1)
                                p.inventory.addItem(Utils.getCrystals(1))
                            } else {
                                p.sendMessage(
                                    color(
                                        getMessages().inventoryFull
                                            .replace("%prefix%", getConfiguration().prefix)
                                    )
                                )
                            }
                        }
                    }
                }
            }
            if (e.slot == (if (getInventories().money.slot == null) 15 else getInventories().money.slot)) {
                if (Vault.Companion.econ != null) {
                    if (e.click == ClickType.SHIFT_LEFT) {
                        if (island.getPermissions(if (u.islandID == island.getId() || island.isCoop(u.island)) if (island.isCoop(u.island)) Role.Member else u.getRole() else Role.Visitor).withdrawBank || u.bypassing) {
                            Vault.Companion.econ.depositPlayer(p, island.money)
                            island.money = 0
                        }
                    } else if (e.click == ClickType.SHIFT_RIGHT) {
                        island.money += Vault.Companion.econ.getBalance(p)
                        Vault.Companion.econ.withdrawPlayer(p, Vault.Companion.econ.getBalance(p))
                    } else if (e.click == ClickType.RIGHT) {
                        val depositValue: Double = if (Vault.Companion.econ.getBalance(p) > 1000) 1000 else Vault.Companion.econ.getBalance(p)
                        if (island.money <= Double.MAX_VALUE - depositValue) {
                            island.money += depositValue
                            Vault.Companion.econ.withdrawPlayer(p, depositValue)
                        }
                    } else if (e.click == ClickType.LEFT) {
                        if (island.getPermissions(if (u.islandID == island.getId() || island.isCoop(u.island)) if (island.isCoop(u.island)) Role.Member else u.getRole() else Role.Visitor).withdrawBank || u.bypassing) {
                            if (island.money > 1000) {
                                island.money -= 1000
                                Vault.Companion.econ.depositPlayer(p, 1000.0)
                            } else {
                                Vault.Companion.econ.depositPlayer(p, island.money)
                                island.money = 0
                            }
                        }
                    }
                }
            }
        }
    }

    init {
        getInstance().registerListeners(this)
    }
}