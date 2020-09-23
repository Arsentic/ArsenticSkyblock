package net.arsentic.arsenticskyblock.hook

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import org.bukkit.plugin.Plugin

abstract class PluginHook(val plugin: ArsenticSkyblock, val hookedPlugin: Plugin?) {

    fun registerHook() {
        if (hookedPlugin != null && hookedPlugin.isEnabled) {
            this.addMethods()
        }
    }

    abstract fun addMethods()
}