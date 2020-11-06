package net.arsentic.arsenticskyblock.island

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.configs.Missions
import net.arsentic.arsenticskyblock.manager.ConfigManager
import org.bukkit.Location
import org.bukkit.entity.Player

data class Island(private val plugin: ArsenticSkyblock, var owner: Player, var pos1: Location, var pos2: Location, var center: Location, var home: Location, var netherHome: Location, val id: Int) {
    val missionClass = plugin.getManager(ConfigManager::class).getConfig(Missions::class)
    var missions = mutableMapOf<String, Int>()
    var missionLevels = mutableMapOf<String, Int>()


    var isLocked = false

    // Members
    var members = mutableSetOf(owner.uniqueId.toString())

    // Values
    var value = 0.0
    var extraValue = value
    var startValue = -1.0
}
