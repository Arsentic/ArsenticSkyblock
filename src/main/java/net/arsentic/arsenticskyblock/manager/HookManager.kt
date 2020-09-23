package net.arsentic.arsenticskyblock.manager

import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.arsentic.arsenticskyblock.hook.PluginHook
import net.arsentic.arsenticskyblock.hook.VaultHook
import net.arsentic.arsenticskyblock.library.Manager

class HookManager(plugin: IridiumSkyblock) : Manager(plugin) {
    private val hooks = mutableSetOf<PluginHook>()

    override fun reload() {
        hooks.addAll(listOf(VaultHook(plugin as IridiumSkyblock)))
    }

    override fun disable() {
        hooks.clear()
    }

}