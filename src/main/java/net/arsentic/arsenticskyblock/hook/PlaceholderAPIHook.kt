package net.arsentic.arsenticskyblock.hook

import me.clip.placeholderapi.PlaceholderAPI
import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class PlaceholderAPIHook(plugin: IridiumSkyblock) : PluginHook(plugin, Bukkit.getPluginManager().getPlugin("PlaceholderAPI")) {

    companion object {
        private var enabled: Boolean? = null

        // return true if PlaceholderAPI is enabled
        fun enabled(): Boolean {
            return enabled ?: (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null.also { enabled = it })
        }

        // Apply PlaceholderAPI Placeholders
        fun apply(player: Player, text: String): String {
            return if (enabled()) PlaceholderAPI.setPlaceholders(player, text) else text
        }

    }

    override fun addMethods() {
        plugin.server.logger.info("[ArsenticSkyblock]")
    }
}