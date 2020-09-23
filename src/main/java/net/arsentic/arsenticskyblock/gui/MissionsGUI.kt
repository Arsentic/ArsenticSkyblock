package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class MissionsGUI(island: Island?) : GUI(island, getInventories().missionsGUISize, getInventories().missionsGUITitle), Listener {
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        if (getIslandManager().islands.containsKey(islandID)) {
            val island: Island = getIslandManager().islands.get(islandID)
            for (mission in getMissions().missions) {
                val placeholderList = Utils.getIslandPlaceholders(island)
                if (!island.getMissionLevels().containsKey(mission.name)) island.getMissionLevels().put(mission.name, 1)
                val data = mission.levels[island.getMissionLevels().get(mission.name)]
                placeholderList.add(Utils.Placeholder("level", island.getMissionLevels().get(mission.name).toString() + ""))
                placeholderList.add(Utils.Placeholder("vaultReward", data!!.vaultReward.toString() + ""))
                placeholderList.add(Utils.Placeholder("crystalsReward", data!!.crystalReward.toString() + ""))
                placeholderList.add(Utils.Placeholder("amount", data!!.amount.toString() + ""))
                placeholderList.add(Utils.Placeholder("status", if (island.getMission(mission.name) === Int.MIN_VALUE) getMessages().completed else island.getMission(mission.name).toString() + "/" + data!!.amount + ""))
                setItem(mission.item.slot!!, Utils.makeItemHidden(mission.item, placeholderList))
            }
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
        }
    }

    init {
        getInstance().registerListeners(this)
    }
}