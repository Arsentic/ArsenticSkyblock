package net.arsentic.arsenticskyblock.manager

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.hook.PluginHook
import net.arsentic.arsenticskyblock.hook.VaultHook
import net.arsentic.arsenticskyblock.library.Manager

class HookManager(plugin: ArsenticSkyblock) : Manager(plugin) {
    private val hooks = mutableSetOf<PluginHook>()

    override fun reload() {
        hooks.addAll(listOf(VaultHook(plugin as ArsenticSkyblock)))
    }

    override fun disable() {
        hooks.clear()
    }

}