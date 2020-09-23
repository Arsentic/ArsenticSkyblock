package net.arsentic.arsenticskyblock.manager

import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.island.Island.Island
import net.arsentic.arsenticskyblock.library.Manager
import org.bukkit.Bukkit
import org.bukkit.World

class IslandManager(plugin: IridiumSkyblock) : Manager(plugin) {

    val islands = mutableMapOf<Int, Island>()
    val users = mutableMapOf<String, User>()
    val islandCache = mutableMapOf<MutableList<Int>, MutableSet<Int>>()

    override fun reload() {

    }

    override fun disable() {

    }

    val normalWorld: World? = Bukkit.getWorld("islands")
    val netherWorld: World? = Bukkit.getWorld("islands_nether")
}