package net.arsentic.arsenticskyblock.hook

import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.plugin.Plugin

abstract class PluginHook(val plugin: IridiumSkyblock, val hookedPlugin: Plugin?) {

    fun registerHook() {
        if (hookedPlugin != null && hookedPlugin.isEnabled) {
            this.addMethods()
        }
    }

    abstract fun addMethods()
}