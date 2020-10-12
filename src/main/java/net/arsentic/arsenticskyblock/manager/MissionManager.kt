package net.arsentic.arsenticskyblock.manager

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.configs.Messages
import net.arsentic.arsenticskyblock.configs.Missions
import net.arsentic.arsenticskyblock.configs.Options
import net.arsentic.arsenticskyblock.island.Island
import net.arsentic.arsenticskyblock.library.Manager
import net.arsentic.arsenticskyblock.library.StringPlaceholders
import net.arsentic.arsenticskyblock.mission.MissionRestart
import net.arsentic.core.library.HexUtils.colorify
import org.bukkit.Bukkit


class MissionManager(plugin: ArsenticSkyblock, val island: Island) : Manager(plugin) {
    private val missionClass = plugin.getManager(ConfigManager::class).getConfig(Missions::class)
    private var missions = mutableMapOf<String, Int>()
    private var missionLevels = mutableMapOf<String, Int>()
    
    override fun reload() {

    }

    override fun disable() {

    }

    fun getMission(mission: String): Int? {
        if (!missions.containsKey(mission))
            missions[mission] = 0

        return missions[mission]
    }

    fun resetMissions() {
        missions.clear()
        missionLevels.clear()
    }

    fun addMission(mission: String, amount: Int) {
        if (!missions.containsKey(mission))
            missions[mission] = 0

        if (missions[mission] == Integer.MIN_VALUE)
            return

        for (x in missionClass.missions) {
            if (x.name.equals(mission, true)) {
                if (missionLevels.containsKey(mission))
                    missionLevels[mission] = 1

                if ((x.levels[missionLevels[mission]]?.amount ?: return) <= missions[mission] ?: return) {
                    // TODO: Complete mission
                    println("MissionManager.kt TODO #2")
                }
            }
        }
    }

    fun setMission(mission: String, amount: Int) {
        if (!missions.containsKey(mission))
            missions[mission] = 0

        if (missions[mission] == Int.MIN_VALUE)
            return

        missions[mission] = amount

        for (m in missionClass.missions) {
            if (m.name.equals(mission, ignoreCase = true)) {
                if (!missionLevels.containsKey(mission))
                    missionLevels[mission] = 1

                if ((m.levels[missionLevels[mission]]?.amount ?: return) <= missions[mission] ?: return) {
                    // TODO: Complete mission
                    println("MissionManager.kt TODO #2")
                }
            }
        }
    }

    fun completeMission(missionName: String) {
        missionLevels.putIfAbsent(missionName, 1)
        val config = plugin.getManager(ConfigManager::class).getConfig(Options::class)
        missions[missionName] = if (config.missionRestart === MissionRestart.Instantly)
            0
        else
            Int.MIN_VALUE

        val mission = missionClass.missions.toList()
            .stream()
            .filter { x -> x.name.equals(missionName, true) }
            .findAny()
            .orElse(null) ?: return

        val levels = mission.levels
        val levelProgress = missionLevels[missionName] ?: return
        val level = levels[levelProgress]
        val crystalReward = level!!.crystalReward
        val vaultReward = level.vaultReward
//        this.crystals += crystalReward
//        this.money += vaultReward


        val messages = plugin.getManager(ConfigManager::class).getConfig(Messages::class)

        val placeholders = StringPlaceholders.builder()
            .addPlaceholder("mission", missionName)
            .addPlaceholder("level", levelProgress.toString())
            .addPlaceholder("crystalreward", crystalReward.toString())
            .addPlaceholder("vaultreward", vaultReward.toString())

        val titleMessage = placeholders.apply(messages.missionComplete)
        val subTitleMessage = placeholders.apply(messages.rewards)

        for (member in island.members) {
            val p = Bukkit.getPlayer(member) ?: continue

            p.sendTitle(colorify(titleMessage), colorify(subTitleMessage), 20, 40, 20)
        }

        //Reset current mission status
        if (mission.levels.containsKey(levelProgress + 1)) {


            //We have another mission, put us on the next level
            missions.remove(missionName)
            missionLevels[missionName] = levelProgress + 1
        } else if (config.missionRestart === MissionRestart.Instantly) {
            missions.remove(missionName)
            missionLevels.remove(missionName)
        }
    }


}