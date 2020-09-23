package net.arsentic.arsenticskyblock.manager

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.configs.Messages
import net.arsentic.arsenticskyblock.configs.Options
import net.arsentic.arsenticskyblock.configs.Upgrades
import net.arsentic.arsenticskyblock.island.Island
import net.arsentic.arsenticskyblock.library.Manager
import net.arsentic.core.library.HexUtils.colorify
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import java.util.*

class IslandManager(plugin: ArsenticSkyblock) : Manager(plugin) {

    val islands = mutableMapOf<Int, Island>()
    val users = mutableMapOf<String, User>()
    val islandCache = mutableMapOf<MutableList<Int>, MutableSet<Int>>()

    override fun reload() {

    }

    override fun disable() {

    }

    fun createIsland(player: Player) {
        val config = plugin.getManager(ConfigManager::class)


        val user = User(plugin as ArsenticSkyblock, Bukkit.getOfflinePlayer(player.uniqueId))
        if (user.lastCreate != null && Date().before(user.lastCreate) && config.getConfig(Options::class).createCooldown && !user.bypassing) {
            player.sendMessage(colorify(config.getConfig(Messages::class).createCooldown))
            return
        }

        val calender = Calendar.getInstance()
        calender.add(Calendar.SECOND, plugin.config.regenCooldown)
        user.lastCreate = calender.time

        // Define location :monkaW
        val sizeUpgrade = config.getConfig(Upgrades::class).sizeUpgrade

        val pos1 = sizeUpgrade.upgrades[1]?.size?.div(2.00)?.let { sizeUpgrade.upgrades[1]?.size?.div(2.00)?.let { it1 -> nextLoc().clone().subtract(it1, 0.0, it) } }
        val pos2 = sizeUpgrade.upgrades[1]?.size?.div(2.00)?.let { sizeUpgrade.upgrades[1]?.size?.div(2.00)?.let { it1 -> nextLoc().clone().add(it1, 0.0, it) } }
        val center = nextLoc().clone().add(0.0, 100.0, 0.0)
        val home = nextLoc().clone()
        val netherHome = home.clone()

        if (config.getConfig(Options::class).netherIslands) {
            netherHome.world = plugin.getManager(IslandManager::class).netherWorld
        }


    }

    fun getIslandFromId(id: Int): Island? {
        return islands[id]
    }

    val normalWorld: World? = Bukkit.getWorld("islands")
    val netherWorld: World? = Bukkit.getWorld("islands_nether")

    fun nextLoc(): Location {
        return Location(normalWorld, 0.0, 0.0, 0.0)
    }
}