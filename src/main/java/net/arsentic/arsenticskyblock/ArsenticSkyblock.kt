package net.arsentic.arsenticskyblock

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import net.arsentic.arsenticskyblock.hook.PlaceholderAPIHook
import net.arsentic.arsenticskyblock.library.ArsenticPlugin
import net.arsentic.arsenticskyblock.manager.*

// Gonna be honest with you here alex, The code is a lot better than it was
class ArsenticSkyblock : ArsenticPlugin() {
    var protocolManager: ProtocolManager? = null

    override fun enablePlugin() {
        // Initialize ProtocolLib
        protocolManager = ProtocolLibrary.getProtocolManager()

        // Register Managers
        this.getManager(ConfigManager::class)
        this.getManager(HookManager::class)
        this.getManager(WorldManager::class)
        this.getManager(IslandManager::class)
        this.getManager(SchematicManager::class)
        this.getManager(MissionManager::class)

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