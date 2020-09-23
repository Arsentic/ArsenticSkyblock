package net.arsentic.arsenticskyblock.hook

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.Bukkit

class MultiverseHook(plugin: ArsenticSkyblock) : PluginHook(plugin, Bukkit.getPluginManager().getPlugin("Multiverse-Core")) {
    override fun addMethods() {
        val name = plugin.description.name
        val islandManager = plugin.getManager(IslandManager::class)

        Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv import ${islandManager.normalWorld?.name} normal -g $name")
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv modify set generator $name ${islandManager.normalWorld?.name}")

        Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv import ${islandManager.netherWorld?.name} nether -g " + name)
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv modify set generator $name ${islandManager.netherWorld?.name}")
    }

}