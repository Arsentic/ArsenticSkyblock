package net.arsentic.arsenticskyblock.data

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.island.Island
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.OfflinePlayer
import java.util.*


class User(val plugin: ArsenticSkyblock, val offlinePlayer: OfflinePlayer) {

    var islandID = 0
    var invites = mutableSetOf<Int>()
    var warp: Island.Warp? = null
    var bypassing = false
    var islandChat = false
    var flying = false


    var teleportingHome = false
    var lastCreate: Date? = null
    val island: Island?
        get() = plugin.getManager(IslandManager::class).islands.getOrDefault(islandID, null)

    var role: Role? = null
        get() {
            field = if (island != null) {
                if (island?.owner == offlinePlayer.player) {
                    Role.Owner
                } else {
                    Role.Member
                }

            } else {
                Role.Visitor
            }

            return field
        }

    init {
        plugin.getManager(IslandManager::class).users[offlinePlayer.uniqueId.toString()] = this
    }

    enum class Role(val id: Int) {
        Owner(4), CoOwner(3), Moderator(2), Member(1), Visitor(-1);

        companion object {
            fun getViaRank(i: Int): Role? {
                for (role in values()) {
                    if (role.id == i) {
                        return role
                    }
                }
                return null
            }
        }
    }
}