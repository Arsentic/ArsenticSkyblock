package net.arsentic.arsenticskyblock.listeners

import me.clip.placeholderapi.events.ExpansionUnregisterEvent
import net.arsentic.arsenticskyblock.ArsenticSkyblock
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ExpansionUnregisterListener : Listener {
    @EventHandler
    fun onExpansionUnregister(event: ExpansionUnregisterEvent) {
        try {
            if (event.expansion.identifier != "iridiumskyblock") return
            val plugin: ArsenticSkyblock = getInstance()
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin) { plugin.setupClipsPlaceholderAPI() }
        } catch (ex: Exception) {
            getInstance().sendErrorMessage(ex)
        }
    }
}