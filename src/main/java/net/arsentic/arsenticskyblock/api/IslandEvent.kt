package net.arsentic.arsenticskyblock.api

import net.arsentic.arsenticskyblock.island.Island.Island
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

abstract class IslandEvent(private val island: Island?) : Event() {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        val handlerList = HandlerList()
    }
}