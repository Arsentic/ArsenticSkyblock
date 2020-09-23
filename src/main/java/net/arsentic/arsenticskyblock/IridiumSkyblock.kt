package net.arsentic.arsenticskyblock

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import net.arsentic.arsenticskyblock.hook.PlaceholderAPIHook
import net.arsentic.arsenticskyblock.library.ArsenticPlugin
import net.arsentic.arsenticskyblock.manager.HookManager
import net.arsentic.arsenticskyblock.manager.IslandManager
import net.arsentic.arsenticskyblock.manager.WorldManager

class IridiumSkyblock : ArsenticPlugin() {
    var protocolManager: ProtocolManager? = null

    override fun enablePlugin() {
        // Initialize ProtocolLib
        protocolManager = ProtocolLibrary.getProtocolManager()

        // Register Managers
        this.getManager(HookManager::class)
        this.getManager(WorldManager::class)
        this.getManager(IslandManager::class)

        if (PlaceholderAPIHook.enabled()) {
            // Register Placeholder Expansion
        }

        // Register Listeners
        registerListeners()

        // Register Commands
        registerCommands()

        // Reload plugin
        this.reload()
    }

    override fun disablePlugin() {

    }

}