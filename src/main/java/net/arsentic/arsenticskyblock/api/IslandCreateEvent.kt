package net.arsentic.arsenticskyblock.api

import net.arsentic.arsenticskyblock.island.Island

import org.bukkit.entity.Player

class IslandCreateEvent(private val player: Player, island: Island) : IslandEvent(island)