package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPortalEvent
import org.bukkit.event.player.PlayerTeleportEvent
import java.lang.reflect.InvocationTargetException

class PlayerPortalListener : Listener {
    val supports: Boolean = Material.supports(15)
    @EventHandler
    fun onPlayerPortal(event: PlayerPortalEvent) {
        try {
            val fromLocation = event.from.clone()
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(fromLocation) ?: return
            if (event.cause != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) return
            if (!getConfiguration().netherIslands) {
                event.isCancelled = true
                return
            }
            val player = event.player
            val user: User = User.Companion.getUser(player)
            if (!island.getPermissions(user).useNetherPortal) {
                event.isCancelled = true
                return
            }
            if (supports) {
                event.canCreatePortal = true

                // This setting forces portal search radius to 16, avoiding conflicts with bordering portals
                // (May have unintended consequences...?)
                if (getConfiguration().forceShortPortalRadius) event.searchRadius = 16
            } else {
                try {
                    PlayerPortalEvent::class.java.getMethod("useTravelAgent", Boolean::class.javaPrimitiveType).invoke(event, true)
                    Class.forName("org.bukkit.TravelAgent")
                        .getMethod("setCanCreatePortal", Boolean::class.javaPrimitiveType)
                        .invoke(PlayerPortalEvent::class.java.getMethod("getPortalTravelAgent").invoke(event), true)

                    // This setting forces portal search radius to 16, avoiding conflicts with bordering portals
                    // (May have unintended consequences...?)
                    if (getConfiguration().forceShortPortalRadius) {
                        Class.forName("org.bukkit.TravelAgent")
                            .getMethod("setSearchRadius", Int::class.javaPrimitiveType)
                            .invoke(PlayerPortalEvent::class.java.getMethod("getPortalTravelAgent").invoke(event), 16)
                    }
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            }
            val world = fromLocation.world ?: return
            val worldName = world.name
            if (worldName == getConfiguration().worldName) event.setTo(island.getNetherhome()) else if (worldName == getConfiguration().netherWorldName) event.setTo(island.getHome())
            Bukkit.getScheduler().runTask(getInstance(), Runnable {
                val `is`: Island = getIslandManager().getIslandViaLocation(player.location)
                if (`is` != null) {
                    `is`.sendBorder(player)
                }
            })
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}