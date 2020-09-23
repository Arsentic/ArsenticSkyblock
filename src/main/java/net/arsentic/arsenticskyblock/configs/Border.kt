package net.arsentic.arsenticskyblock.configs

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.enum.Color

class Border(plugin: ArsenticSkyblock) : Config(plugin) {
    var startingColor: Color = Color.Blue
    var blueEnabled = true
    var redEnabled = true
    var greenEnabled = true
    var offEnabled = true
}