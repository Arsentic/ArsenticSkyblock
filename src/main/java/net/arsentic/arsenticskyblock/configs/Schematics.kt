package net.arsentic.arsenticskyblock.configs

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import org.bukkit.Material

class Schematics(plugin: ArsenticSkyblock) : Config(plugin) {
    var schematics = listOf(FakeSchematic("island.schematic", "nether.schematic", -0.5, 96.00, -2.5, "", Material.GRASS_BLOCK, "&b&lDefault Island", listOf("&7The default island"), 0))

    data class FakeSchematic(var name: String, var netherIsland: String, val x: Double, val y: Double, val z: Double, var permission: String, val item: Material, val displayName: String, val lore: List<String>, val slot: Int)
}