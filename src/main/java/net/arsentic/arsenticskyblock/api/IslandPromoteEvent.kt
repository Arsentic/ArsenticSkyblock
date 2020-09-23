package net.arsentic.arsenticskyblock.api

import net.arsentic.arsenticskyblock.Role
import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.island.Island
import org.bukkit.event.Cancellable

class IslandPromoteEvent(island: Island, private val target: User, private val promoter: User, private val role: Role) : IslandEvent(island), Cancellable {
    private var isCancelled = false

    override fun isCancelled(): Boolean {
        return isCancelled
    }

    override fun setCancelled(b: Boolean) {
        isCancelled = b
    }
}