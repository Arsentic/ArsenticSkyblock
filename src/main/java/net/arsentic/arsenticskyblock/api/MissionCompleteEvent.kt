package net.arsentic.arsenticskyblock.api

import net.arsentic.arsenticskyblock.mission.MissionType
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class MissionCompleteEvent(var missionName: String, var missionType: MissionType, var missionLevel: Int) : Event() {
    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        val handlerList = HandlerList()
    }
}