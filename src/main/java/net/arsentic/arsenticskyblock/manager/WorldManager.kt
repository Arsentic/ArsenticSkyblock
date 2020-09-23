package net.arsentic.arsenticskyblock.manager

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.island.VoidGenerator
import net.arsentic.arsenticskyblock.library.Manager
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.generator.ChunkGenerator

class WorldManager(plugin: ArsenticSkyblock) : Manager(plugin) {
    override fun reload() {
        createWorld("islands", World.Environment.NORMAL)
        createWorld("islands_nether", World.Environment.NETHER)
    }

    override fun disable() {

    }

    private fun createWorld(worldName: String, environment: World.Environment) {
        if (Bukkit.getWorld(worldName) == null) {
            val worldCreator = WorldCreator.name(worldName)
                .type(WorldType.NORMAL)
                .environment(environment)
                .generateStructures(false)
                .generator(worldGenerator)

            worldCreator.createWorld()
        }
    }

    private val worldGenerator: ChunkGenerator
        get() = VoidGenerator()

}