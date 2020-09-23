package net.arsentic.arsenticskyblock.listeners

import com.earth2me.essentials.Essentials
import com.earth2me.essentials.spawn.EssentialsSpawn
import net.arsentic.arsenticskyblock.data.User
import net.arsentic.arsenticskyblock.configs.Options
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class PlayerMoveListener : Listener {
    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        try {
            val player = event.player
            val location = player.location
            val islandManager: IslandManager = getIslandManager()
            if (!islandManager.isIslandWorld(location)) return
            val config: Options = getConfiguration()
            if (location.y < 0 && config.voidTeleport) {
                val island: Island? = islandManager.getIslandViaLocation(location)
                val world = location.world ?: return
                if (island != null) {
                    if (world.name == islandManager.world.name) island.teleportHome(player) else island.teleportNetherHome(player)
                } else {
                    val user: User = User.Companion.getUser(player)
                    if (user.island != null) {
                        if (world.name == islandManager.world.name) user.island.teleportHome(player) else if (world.name == islandManager.netherWorld.name) user.island.teleportNetherHome(player)
                    } else if (islandManager.isIslandWorld(world)) {
                        if (Bukkit.getPluginManager().isPluginEnabled("EssentialsSpawn")) {
                            val pluginManager = Bukkit.getPluginManager()
                            val essentialsSpawn: EssentialsSpawn? = pluginManager.getPlugin("EssentialsSpawn") as EssentialsSpawn?
                            val essentials: Essentials? = pluginManager.getPlugin("Essentials") as Essentials?
                            if (essentials != null && essentialsSpawn != null) player.teleport(essentialsSpawn.getSpawn(essentials.getUser(player).getGroup()))
                        } else player.teleport(Bukkit.getWorlds()[0].spawnLocation)
                    }
                }
            }
            val user: User = User.Companion.getUser(player)
            val island: Island = user.island ?: return
            if (user.flying
                && (!island.isInIsland(location) || island.getFlightBooster() === 0)
                && player.gameMode != GameMode.CREATIVE
                && !(player.hasPermission("IridiumSkyblock.Fly")
                        || player.hasPermission("iridiumskyblock.fly"))
            ) {
                player.allowFlight = false
                player.isFlying = false
                user.flying = false
                player.sendMessage(
                    color(
                        getMessages().flightDisabled
                            .replace("%prefix%", config.prefix)
                    )
                )
            }
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}