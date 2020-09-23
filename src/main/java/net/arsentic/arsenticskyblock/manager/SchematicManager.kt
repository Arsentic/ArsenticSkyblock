package net.arsentic.arsenticskyblock.manager

import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.island.IslandSchematic
import net.arsentic.arsenticskyblock.library.Manager
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class SchematicManager(plugin: ArsenticSkyblock) : Manager(plugin) {

    private val _schematics: MutableMap<String, IslandSchematic> = mutableMapOf()

    val schematics: Map<String, IslandSchematic>
        get() = this._schematics.toMap()

    override fun reload() {
        F
        val schematicFolder = File(this.plugin.dataFolder, "schematics")
        val schematicsFile = File(this.plugin.dataFolder, "schematics.yml")
        val exists = schematicsFile.exists()
        val schematicsConfig = YamlConfiguration.loadConfiguration(schematicsFile)

        if (!exists) {
            this.saveDefaults(schematicsConfig)
        }


        val schematicFiles = schematicFolder.listFiles() ?: error("Schematics directory does not exist")
        schematicsConfig.getKeys(false).forEach { schematicName ->
            try {
                val file = schematicFiles.find { it.nameWithoutExtension.equals(schematicName, true) }
                if (file != null) {
                    if (ClipboardFormats.findByFile(file) != null) {
                        val section = schematicsConfig.getConfigurationSection(schematicName) ?: error(schematicName)
                        val displayName = section.getString("name") ?: error("$schematicName.name")
                        val icon = parseEnum(Material::class, section.getString("icon") ?: error("$schematicName.icon"))
                        val lore = section.getStringList("lore")
                        this._schematics[schematicName.toLowerCase()] = IslandSchematic(schematicName, file, displayName, icon, lore)
                    } else {
                        plugin.logger.warning("File located in the schematics folder is not a valid schematic: ${file.name}")
                    }
                } else {
                    plugin.logger.warning("Unable to locate a schematic that was listed in the schematics.yml file: $schematicName")
                }
            } catch (ex: Exception) {
                plugin.logger.severe("Missing schematics.yml section: ${ex.message}")
            }
        }
    }

    override fun disable() {
        this._schematics.clear()
    }

    private fun saveDefaults(config: FileConfiguration) {
        val section = config.createSection("default")
        section["name"] = "&eDefault Island"
        section["icon"] = Material.GRASS_BLOCK.name
        section["lore"] = listOf(
            "&7The default schematic for RoseSkyblock.",
            "&7Handle with care."
        )

        config.save(schemFile)
    }

    private val schemFile: File
        get() = File(plugin.dataFolder, "schematics.yml")
}