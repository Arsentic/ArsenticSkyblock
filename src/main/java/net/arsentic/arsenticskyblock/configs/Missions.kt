package net.arsentic.arsenticskyblock.configs

import net.arsentic.arsenticskyblock.mission.MissionType
import org.bukkit.CropState
import org.bukkit.Material
import java.util.*

class Missions {
    var missions = listOf(
        Mission(
            "Treasure Hunter", object : HashMap<Int, MissionData>() {
                init {
                    put(1, MissionData(5, 1000, 100, MissionType.EXPERIENCE, emptyList()))
                    put(2, MissionData(10, 5000, 1000, MissionType.EXPERIENCE, emptyList()))
                    put(3, MissionData(15, 10000, 10000, MissionType.EXPERIENCE, emptyList()))
                }
            },

            Item(Material.EXPERIENCE_BOTTLE, 10, 1, "&b&lTreasure Hunter Level {level}", listOf("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bCollect {amount} Experience", "&b&l * &7Current Status: &b{status}", "&b&l * &7Reward: &b{crystalsReward} Island Crystals and \${vaultReward}", "", "&b&l[!] &bComplete this mission for rewards."))
        ),

        Mission("Competitor", object : HashMap<Int, MissionData>() {
            init {
                put(1, MissionData(5, 10000, 100, MissionType.VALUE_INCREASE, emptyList()))
                put(2, MissionData(10, 10000, 500, MissionType.VALUE_INCREASE, emptyList()))
                put(3, MissionData(15, 10000, 1000, MissionType.VALUE_INCREASE, emptyList()))
                put(4, MissionData(15, 10000, 5000, MissionType.VALUE_INCREASE, emptyList()))
                put(5, MissionData(15, 10000, 10000, MissionType.VALUE_INCREASE, emptyList()))
            }
        }, Item(Material.GOLD_INGOT, 11, 1, "&b&lCompetitor Level {level}", listOf("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bGain {amount} Island Value", "&b&l * &7Current Status: &b{status}", "&b&l * &7Reward: &b{crystalsReward} Island Crystals and \${vaultReward}", "", "&b&l[!] &bComplete this mission for rewards."))),
        Mission("Miner", object : HashMap<Int, MissionData>() {
            init {
                put(1, MissionData(1, 10000, 50, MissionType.BLOCK_BREAK, listOf(Material.COAL_ORE.name, Material.DIAMOND_ORE.name, Material.EMERALD_ORE.name, Material.GOLD_ORE.name, Material.IRON_ORE.name, Material.LAPIS_ORE.name, Material.NETHER_QUARTZ_ORE.name, Material.REDSTONE_ORE.name)))
                put(2, MissionData(5, 10000, 100, MissionType.BLOCK_BREAK, listOf(Material.COAL_ORE.name, Material.DIAMOND_ORE.name, Material.EMERALD_ORE.name, Material.GOLD_ORE.name, Material.IRON_ORE.name, Material.LAPIS_ORE.name, Material.NETHER_QUARTZ_ORE.name, Material.REDSTONE_ORE.name)))
                put(3, MissionData(10, 10000, 500, MissionType.BLOCK_BREAK, listOf(Material.COAL_ORE.name, Material.DIAMOND_ORE.name, Material.EMERALD_ORE.name, Material.GOLD_ORE.name, Material.IRON_ORE.name, Material.LAPIS_ORE.name, Material.NETHER_QUARTZ_ORE.name, Material.REDSTONE_ORE.name)))
                put(4, MissionData(15, 10000, 1000, MissionType.BLOCK_BREAK, listOf(Material.COAL_ORE.name, Material.DIAMOND_ORE.name, Material.EMERALD_ORE.name, Material.GOLD_ORE.name, Material.IRON_ORE.name, Material.LAPIS_ORE.name, Material.NETHER_QUARTZ_ORE.name, Material.REDSTONE_ORE.name)))
                put(5, MissionData(15, 10000, 2000, MissionType.BLOCK_BREAK, listOf(Material.COAL_ORE.name, Material.DIAMOND_ORE.name, Material.EMERALD_ORE.name, Material.GOLD_ORE.name, Material.IRON_ORE.name, Material.LAPIS_ORE.name, Material.NETHER_QUARTZ_ORE.name, Material.REDSTONE_ORE.name)))
            }
        }, Item(Material.DIAMOND_ORE, 12, 1, "&b&lMiner Level {level}", listOf("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bDestroy {amount} Ores", "&b&l * &7Current Status: &b{status}", "&b&l * &7Reward: &b{crystalsReward} Island Crystals and \${vaultReward}", "", "&b&l[!] &bComplete this mission for rewards."))),
        Mission("Farmer", object : HashMap<Int, MissionData>() {
            init {
                put(1, MissionData(1, 10000, 50, MissionType.BLOCK_BREAK, listOf(CropState.RIPE.toString(), Material.MELON.name, Material.CACTUS.name, Material.PUMPKIN.name, Material.SUGAR_CANE.name)))
                put(2, MissionData(5, 10000, 100, MissionType.BLOCK_BREAK, listOf(CropState.RIPE.toString(), Material.MELON.name, Material.CACTUS.name, Material.PUMPKIN.name, Material.SUGAR_CANE.name)))
                put(3, MissionData(10, 10000, 500, MissionType.BLOCK_BREAK, listOf(CropState.RIPE.toString(), Material.MELON.name, Material.CACTUS.name, Material.PUMPKIN.name, Material.SUGAR_CANE.name)))
                put(4, MissionData(15, 10000, 1000, MissionType.BLOCK_BREAK, listOf(CropState.RIPE.toString(), Material.MELON.name, Material.CACTUS.name, Material.PUMPKIN.name, Material.SUGAR_CANE.name)))
                put(5, MissionData(15, 10000, 5000, MissionType.BLOCK_BREAK, listOf(CropState.RIPE.toString(), Material.MELON.name, Material.CACTUS.name, Material.PUMPKIN.name, Material.SUGAR_CANE.name)))
            }
        }, Item(Material.SUGAR_CANE, 13, 1, "&b&lFarmer Level {level}", listOf("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bHarvest {amount} Crops", "&b&l * &7Current Status: &b{status}", "&b&l * &7Reward: &b{crystalsReward} Island Crystals and \${vaultReward}", "", "&b&l[!] &bComplete this mission for rewards."))),
        Mission("Hunter", object : HashMap<Int, MissionData>() {
            init {
                put(1, MissionData(1, 10000, 10, MissionType.ENTITY_KILL, emptyList()))
                put(2, MissionData(5, 10000, 50, MissionType.ENTITY_KILL, emptyList()))
                put(3, MissionData(10, 10000, 100, MissionType.ENTITY_KILL, emptyList()))
                put(4, MissionData(15, 10000, 500, MissionType.ENTITY_KILL, emptyList()))
                put(5, MissionData(15, 10000, 1000, MissionType.ENTITY_KILL, emptyList()))
            }
        }, Item(Material.BLAZE_POWDER, 14, 1, "&b&lHunter Level {level}", listOf("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bKill {amount} Mobs", "&b&l * &7Current Status: &b{status}", "&b&l * &7Reward: &b{crystalsReward} Island Crystals and \${vaultReward}", "", "&b&l[!] &bComplete this mission for rewards."))),
        Mission("Fisherman", object : HashMap<Int, MissionData>() {
            init {
                put(1, MissionData(1, 10000, 5, MissionType.FISH_CATCH, emptyList()))
                put(2, MissionData(5, 10000, 10, MissionType.FISH_CATCH, emptyList()))
                put(3, MissionData(10, 10000, 50, MissionType.FISH_CATCH, emptyList()))
                put(4, MissionData(15, 10000, 100, MissionType.FISH_CATCH, emptyList()))
                put(5, MissionData(15, 10000, 500, MissionType.FISH_CATCH, emptyList()))
            }
        }, Item(Material.FISHING_ROD, 15, 1, "&b&lFisherman Level {level}", listOf("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bCatch {amount} Fish", "&b&l * &7Current Status: &b{status}", "&b&l * &7Reward: &b{crystalsReward} Island Crystals and \${vaultReward}", "", "&b&l[!] &bComplete this mission for rewards."))),
        Mission("Builder", object : HashMap<Int, MissionData>() {
            init {
                put(1, MissionData(1, 10000, 100, MissionType.BLOCK_PLACE, emptyList()))
                put(2, MissionData(5, 10000, 500, MissionType.BLOCK_PLACE, emptyList()))
                put(3, MissionData(10, 10000, 1000, MissionType.BLOCK_PLACE, emptyList()))
                put(4, MissionData(15, 10000, 5000, MissionType.BLOCK_PLACE, emptyList()))
                put(5, MissionData(15, 10000, 10000, MissionType.BLOCK_PLACE, emptyList()))
                put(6, MissionData(15, 10000, 50000, MissionType.BLOCK_PLACE, emptyList()))
            }
        }, Item(Material.COBBLESTONE, 16, 1, "&b&lBuilder Level {level}", listOf("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bPlace {amount} Blocks", "&b&l * &7Current Status: &b{status}", "&b&l * &7Reward: &b{crystalsReward} Island Crystals and \${vaultReward}", "", "&b&l[!] &bComplete this mission for rewards.")))
    )

    class Mission(var name: String, var levels: MutableMap<Int, MissionData>, var item: Item)
    class MissionData(var crystalReward: Int, var vaultReward: Int, var amount: Int, var type: MissionType, var conditions: List<String>)
}