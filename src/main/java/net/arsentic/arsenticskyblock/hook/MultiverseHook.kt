package net.arsentic.arsenticskyblock.hook

import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.Bukkit

class MultiverseHook(plugin: IridiumSkyblock): PluginHook(plugin, Bukkit.getPluginManager().getPlugin("Multiverse-Core")) {
    override fun addMethods() {
        val name = plugin.description.name

        Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv import ${plugin.getManager(IslandManager::class)} normal -g $name")
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv modify set generator $name ${plugin.getManager(IslandManager::class)}")

            Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv import " + IridiumSkyblock.islandManager.getNetherWorld().name + " nether -g " + name)
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv modify set generator " + name + " " + IridiumSkyblock.islandManager.getNetherWorld().name)
    }

}