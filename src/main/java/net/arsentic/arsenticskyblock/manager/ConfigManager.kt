package net.arsentic.arsenticskyblock.manager

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.configs.*
import net.arsentic.arsenticskyblock.library.Manager
import kotlin.reflect.KClass

class ConfigManager(plugin: ArsenticSkyblock) : Manager(plugin) {

    private var configs = mutableMapOf<KClass<out Config>, Config>()

    override fun reload() {
        getConfig(BlockValues::class)
        getConfig(Boosters::class)
        getConfig(Border::class)
        getConfig(Commands::class)
        getConfig(Inventories::class)
        getConfig(Messages::class)
        getConfig(Missions::class)
        getConfig(Options::class)
        getConfig(Schematics::class)
        getConfig(Upgrades::class)
    }

    override fun disable() {
        configs.clear()
    }

    fun <M : Config> getConfig(configClass: KClass<M>): M {
        synchronized(configs) {
            @Suppress("UNCHECKED_CAST")
            if (configs.containsKey(configClass))
                return configs[configClass] as M

            return try {
                val manager = configClass.constructors.first().call(this)
                configs[configClass] = manager
                manager
            } catch (ex: ReflectiveOperationException) {
                error("Failed to load manager for ${configClass.simpleName}")
            }
        }
    }


}