package net.arsentic.arsenticskyblock.manager

import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.arsentic.arsenticskyblock.Role
import net.arsentic.arsenticskyblock.SkyblockGenerator
import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.configs.Config
import net.arsentic.arsenticskyblock.enum.Direction
import net.arsentic.arsenticskyblock.island.Island.Island
import net.arsentic.arsenticskyblock.library.Manager
import org.bukkit.*
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Function
import java.util.function.Predicate
import java.util.stream.Collectors

class IslandManager(plugin: IridiumSkyblock) : Manager(plugin) {
    var islands = mutableMapOf<Int, Island>()
    var users: Map<String, User> = HashMap()
    var islandCache = mutableMapOf<MutableList<Int>, MutableSet<Int>>()
    var length = 1
    var current = 0
    var direction = Direction.NORTH
    var nextLocation: Location
    var nextID = 1

    override fun reload() {

    }

    override fun disable() {

    }

    fun createIsland(player: Player) {
        val user = User.Companion.getUser(player)
        if (user.lastCreate != null && Date().before(user.lastCreate) && getConfiguration().createCooldown && !user.bypassing) {
            //The user cannot create an island
            val time = (user.lastCreate!!.time - System.currentTimeMillis()) / 1000
            val day = TimeUnit.SECONDS.toDays(time).toInt()
            val hours = Math.floor(TimeUnit.SECONDS.toHours(time - day * 86400).toDouble()).toInt()
            val minute = Math.floor((time - day * 86400 - hours * 3600) / 60.00).toInt()
            val second = Math.floor((time - day * 86400 - hours * 3600) % 60.00).toInt()
            player.sendMessage(color(getMessages().createCooldown.replace("%days%", day.toString() + "").replace("%hours%", hours.toString() + "").replace("%minutes%", minute.toString() + "").replace("%seconds%", second.toString() + "").replace("%prefix%", getConfiguration().prefix)))
            return
        }
        val c = Calendar.getInstance()
        c.add(Calendar.SECOND, getConfiguration().regenCooldown)
        user.lastCreate = c.time
        val pos1 = nextLocation.clone().subtract(IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(1)!!.size / 2.00, 0.0, IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(1)!!.size / 2.00)
        val pos2 = nextLocation.clone().add(IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(1)!!.size / 2.00, 0.0, IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(1)!!.size / 2.00)
        val center = nextLocation.clone().add(0.0, 100.0, 0.0)
        val home = nextLocation.clone()
        val netherhome = home.clone()
        if (getConfiguration().netherIslands) {
            netherhome.world = getIslandManager().getNetherWorld()
        }
        val island = Island(player, pos1, pos2, center, home, netherhome, nextID)
        islands[nextID] = island
        user.islandID = nextID
        user.role = Role.Owner
        if (getSchematics().schematics.size() === 1) {
            for (schematic in getSchematics().schematics) {
                island.setSchematic(schematic.name)
                island.setNetherschematic(schematic.netherisland)
                island.setHome(island.getHome().add(schematic.x, schematic.y, schematic.z))
                island.setNetherhome(island.getNetherhome().add(schematic.x, schematic.y, schematic.z))
            }
            island.pasteSchematic(player, false)
        } else {
            player.openInventory(island.getSchematicSelectGUI().getInventory())
        }
        when (direction) {
            NORTH -> nextLocation.add(getConfiguration().distance, 0.0, 0.0)
            EAST -> nextLocation.add(0.0, 0.0, getConfiguration().distance)
            SOUTH -> nextLocation.subtract(getConfiguration().distance, 0.0, 0.0)
            WEST -> nextLocation.subtract(0.0, 0.0, getConfiguration().distance)
        }
        current++
        if (current == length) {
            current = 0
            direction = direction.next()
            if (direction === Direction.SOUTH || direction === Direction.NORTH) {
                length++
            }
        }
        getInstance().saveConfigs()
        nextID++
    }

    private fun makeWorld() {
        makeWorld(World.Environment.NORMAL, getConfiguration().worldName)
        if (getConfiguration().netherIslands) makeWorld(World.Environment.NETHER, getConfiguration().netherWorldName)
    }

    private fun makeWorld(env: World.Environment, name: String) {
        val wc = WorldCreator(name)
        wc.type(WorldType.FLAT)
        wc.generateStructures(false)
        wc.generator(SkyblockGenerator())
        wc.environment(env)
        wc.createWorld()
    }

    fun getIslandViaLocation(location: Location?): Island? {
        if (location == null) return null
        if (!isIslandWorld(location)) return null
        val chunk = location.chunk
        val chunkKey = Collections.unmodifiableList(listOf(chunk.x, chunk.z))
        val x = location.x
        val z = location.z
        val islandIds = islandCache.computeIfAbsent(chunkKey, Function<List<Int>, MutableSet<Int>> { hash: List<Int>? ->
            islands
                .values
                .stream()
                .filter(Predicate<Island> { island: Island -> island.isInIsland(x, z) })
                .map<Any>(Island::getId)
                .collect(Collectors.toSet<Any>())
        })
        for (id in islandIds) {
            val island: Island = islands[id] ?: continue
            if (island.isInIsland(x, z)) return island
        }
        for (island in islands.values) {
            if (!island.isInIsland(x, z)) continue
            islandIds.add(island.getId())
            return island
        }
        return null
    }

    fun getIslandViaId(i: Int): Island? {
        return islands[i]
    }

    fun isIslandWorld(location: Location?): Boolean {
        return if (location == null) false else isIslandWorld(location.world)
    }

    fun isIslandWorld(world: World?): Boolean {
        if (world == null) return false
        val name = world.name
        return isIslandWorld(name)
    }

    fun isIslandWorld(name: String): Boolean {
        val config: Config = getConfiguration()
        return name == config.worldName || name == config.netherWorldName
    }

    fun removeIsland(island: Island) {
        val id: Int = island.getId()
        islands.remove(id)
        islandCache
            .forEach { (key: List<Int>?, value: MutableSet<Int>) -> value.remove(id) }
    }

    init {
        makeWorld()
        nextLocation = Location(world, 0, 0, 0)
    }
}