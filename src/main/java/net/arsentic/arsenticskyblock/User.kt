package net.arsentic.arsenticskyblock

import net.arsentic.arsenticskyblock.island.Island.Island
import org.bukkit.OfflinePlayer
import java.util.*

class User(val plugin: IridiumSkyblock, player: OfflinePlayer) {
    var name: String?
    var islandID: Int
    lateinit var role: Role
    var invites = hashSetOf<Int>()
    var warp: Island.Warp? = null
    var bypassing: Boolean
    var islandChat: Boolean
    var flying: Boolean

    @Transient
    var teleportingHome = false
    var lastCreate: Date? = null
    val island: Island?
        get() = plugin..islands.getOrDefault(islandID, null)

    fun getRole(): Role {
        if (role == null) {
            role = if (island != null) {
                if (island.getOwner().equals(player)) {
                    Role.Owner
                } else {
                    Role.Member
                }
            } else {
                Role.Visitor
            }
        }
        return role
    }

    fun getUser(p: String): User {
        if (getIslandManager().users == null) getIslandManager().users = HashMap<Any, Any>()
        return getIslandManager().users.get(p)
    }

    fun getUser(p: OfflinePlayer?): User? {
        if (p == null) return null
        if (getIslandManager().users == null) getIslandManager().users = HashMap<Any, Any>()
        return if (getIslandManager().users.containsKey(p.uniqueId.toString())) getIslandManager().users.get(p.uniqueId.toString()) else User(p)
    }


    init {
        player = p.uniqueId.toString()
        name = p.name
        islandID = 0
        bypassing = false
        islandChat = false
        flying = false
        getIslandManager().users.put(player, this)
    }
}