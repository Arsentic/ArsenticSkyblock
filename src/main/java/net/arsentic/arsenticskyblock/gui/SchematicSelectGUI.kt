package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.User
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class SchematicSelectGUI(island: Island?) : GUI(island, getInventories().schematicselectGUISize, getInventories().schematicselectGUITitle), Listener {
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        if (getIslandManager().islands.containsKey(islandID)) {
            var i = 0
            for (fakeSchematic in getSchematics().schematics) {
                if (fakeSchematic.slot == null) fakeSchematic.slot = i
                try {
                    setItem(fakeSchematic.slot!!, Utils.makeItem(fakeSchematic.item, 1, fakeSchematic.displayname, fakeSchematic.lore))
                } catch (e: Exception) {
                    setItem(fakeSchematic.slot!!, Utils.makeItem(Material.STONE, 1, fakeSchematic.displayname, fakeSchematic.lore))
                }
                i++
            }
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            for (fakeSchematic in getSchematics().schematics) {
                if (e.slot == fakeSchematic.slot && (fakeSchematic.permission.isEmpty() || e.whoClicked.hasPermission(fakeSchematic.permission))) {
                    e.whoClicked.closeInventory()
                    if (island.getSchematic() != null) {
                        for (player in island.getMembers()) {
                            val user: User = User.Companion.getUser(player)
                            val p = Bukkit.getPlayer(user.name!!)
                            p?.sendMessage(color(getMessages().regenIsland.replace("%prefix%", getConfiguration().prefix)))
                        }
                    }
                    if (island.getSchematic() == null) {
                        island.setSchematic(fakeSchematic.name)
                        island.setNetherschematic(fakeSchematic.netherisland)
                        island.pasteSchematic(e.whoClicked as Player, false)
                    } else {
                        island.setSchematic(fakeSchematic.name)
                        island.setNetherschematic(fakeSchematic.netherisland)
                        island.pasteSchematic(true)
                    }
                    island.setHome(island.getHome().add(fakeSchematic.x, fakeSchematic.y, fakeSchematic.z))
                    island.setNetherhome(island.getNetherhome().add(fakeSchematic.x, fakeSchematic.y, fakeSchematic.z))
                    if (getConfiguration().restartUpgradesOnRegen) {
                        island.resetMissions()
                        island.setSizeLevel(1)
                        island.setMemberLevel(1)
                        island.setWarpLevel(1)
                        island.setOreLevel(1)
                        island.setFlightBooster(0)
                        island.setExpBooster(0)
                        island.setFarmingBooster(0)
                        island.setSpawnerBooster(0)
                    }
                    return
                }
            }
        }
    }

    init {
        getInstance().registerListeners(this)
    }
}