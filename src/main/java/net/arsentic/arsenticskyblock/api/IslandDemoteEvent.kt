package net.arsentic.arsenticskyblock.api

import net.arsentic.arsenticskyblock.Role
import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.island.Island.Island

class IslandDemoteEvent(island: Island, private val target: User, private val demoter: User, private val role: Role) : IslandEvent(island) {
    private val cancelled = false
}