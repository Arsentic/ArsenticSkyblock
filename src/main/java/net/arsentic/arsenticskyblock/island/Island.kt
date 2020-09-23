package net.arsentic.arsenticskyblock.island

import org.bukkit.Location
import org.bukkit.entity.Player

data class Island(var owner: Player, var pos1: Location, var pos2: Location, var center: Location, var home: Location, var netherHome: Location, val id: Int) {
    var isLocked = false
}
