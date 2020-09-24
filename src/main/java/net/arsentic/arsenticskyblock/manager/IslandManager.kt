package net.arsentic.arsenticskyblock.manager

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.configs.Messages
import net.arsentic.arsenticskyblock.configs.Options
import net.arsentic.arsenticskyblock.configs.Upgrades
import net.arsentic.arsenticskyblock.data.User
import net.arsentic.arsenticskyblock.island.Island
import net.arsentic.arsenticskyblock.library.Manager
import net.arsentic.core.library.HexUtils.colorify
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.entity.Player
import java.util.*
import java.util.stream.Collectors


class IslandManager(plugin: ArsenticSkyblock) : Manager(plugin) {

    val islands = mutableMapOf<Int, Island?>()
    val users = mutableMapOf<String, User>()
    val islandCache = mutableMapOf<MutableList<Int>, MutableSet<Int>>()
    val normalWorld: World? = Bukkit.getWorld("islands")
    val netherWorld: World? = Bukkit.getWorld("islands_nether")

    private var nextId = 1

    override fun reload() {

    }

    override fun disable() {

    }

    fun createIsland(player: Player) {
        val config = plugin.getManager(ConfigManager::class)


        val user = User(plugin as ArsenticSkyblock, Bukkit.getOfflinePlayer(player.uniqueId))
        if (user.lastCreate != null && Date().before(user.lastCreate) && config.getConfig(Options::class).createCooldown && !user.bypassing) {
            player.sendMessage(colorify(config.getConfig(Messages::class).createCooldown))
            return
        }

        val calender = Calendar.getInstance()
        calender.add(Calendar.SECOND, config.getConfig(Options::class).regenCooldown)
        user.lastCreate = calender.time

        // Define location :monkaW:
        val sizeUpgrade = config.getConfig(Upgrades::class).sizeUpgrade

        val pos1 = sizeUpgrade.upgrades[1]?.size?.div(2.00)?.let { sizeUpgrade.upgrades[1]?.size?.div(2.00)?.let { it1 -> nextLoc().clone().subtract(it1, 0.0, it) } } ?: return
        val pos2 = sizeUpgrade.upgrades[1]?.size?.div(2.00)?.let { sizeUpgrade.upgrades[1]?.size?.div(2.00)?.let { it1 -> nextLoc().clone().add(it1, 0.0, it) } } ?: return
        val center = nextLoc().clone().add(0.0, 100.0, 0.0)
        val home = nextLoc().clone()
        val netherHome = home.clone()

        if (config.getConfig(Options::class).netherIslands) {
            netherHome.world = plugin.getManager(IslandManager::class).netherWorld
        }

        val island = Island(player, pos1, pos2, center, home, netherHome, nextId)
        islands[nextId] = island

        user.islandID = nextId
        user.role = User.Role.Owner
    }

    fun getIslandFromId(id: Int): Island? {
        return islands[id]
    }

    fun isInIsland(island: Island, location: Location?): Boolean {
        return if (location == null)
            false
        else
            isInIsland(island, location.x, location.z)
    }

    fun isInIsland(island: Island?, x: Double, z: Double): Boolean {
        if (island == null)
            return false

        return x >= island.pos1.x && x <= island.pos2.x && z >= island.pos1.z && z <= island.pos2.z
    }

    fun getIslandFromLocation(location: Location): Island? {
        if (!(location.world ?: return null).name.toLowerCase().contains("islands", true))
            return null

        val chunk = location.chunk
        val chunkKey = Collections.unmodifiableList(Arrays.asList(chunk.x, chunk.z))

        val x = location.x
        val z = location.z

        val islandIds = islandCache.computeIfAbsent(chunkKey) { hash ->
            islands
                .values
                .stream()
                .filter { island -> isInIsland(island, x, z) }
                .map { x -> x?.id }
                .collect(Collectors.toSet())
        }

        for (id in islandIds) {
            val island = islands[id] ?: continue
            if (isInIsland(island, x, z)) return island
        }

        for (island in islands.values) {
            if (!isInIsland(island ?: continue, x, z))
                continue

            islandIds.add(island.id)
            return island
        }

        return null
    }

    private fun nextLoc(): Location {
        return Location(normalWorld, 0.0, 0.0, 0.0)
    }

    // User Methods
    fun getUser(player: String): User? {
        return users[player]
    }

    fun getUser(player: OfflinePlayer): User? {
        return if (users.containsKey(player.uniqueId.toString()))
            users[player.uniqueId.toString()]
        else
            User(plugin as ArsenticSkyblock, player)
    }

    // Value Methods

    fun addExtraValue(island: Island, amount: Double) {
        island.extraValue += amount
    }

    fun removeExtraValue(island: Island, amount: Double) {
        island.extraValue -= amount
    }
}