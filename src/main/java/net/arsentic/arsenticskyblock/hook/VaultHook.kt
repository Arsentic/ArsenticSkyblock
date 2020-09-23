package net.arsentic.arsenticskyblock.hook

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.milkbowl.vault.chat.Chat
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit

class VaultHook(plugin: ArsenticSkyblock) : PluginHook(plugin, Bukkit.getPluginManager().getPlugin("Vault")) {
    lateinit var economy: Economy
    lateinit var permission: Permission
    lateinit var chat: Chat

    override fun addMethods() {
        this.setupChat()
        this.setupEconomy()
        this.setupPermissions()
    }


    // Setup chat
    private fun setupChat(): Boolean {
        if (hookedPlugin == null)
            return false

        chat = (plugin.server.servicesManager.getRegistration(Chat::class.java) ?: return false).provider
        return true
    }


    // Setup economy
    private fun setupEconomy(): Boolean {
        if (hookedPlugin == null)
            return false

        economy = (plugin.server.servicesManager.getRegistration(Economy::class.java) ?: return false).provider
        return true
    }

    // Setup permissions
    private fun setupPermissions(): Boolean {
        if (hookedPlugin == null)
            return false

        permission = (plugin.server.servicesManager.getRegistration(Permission::class.java) ?: return false).provider
        return true
    }


}