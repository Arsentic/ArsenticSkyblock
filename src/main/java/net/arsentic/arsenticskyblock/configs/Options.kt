package net.arsentic.arsenticskyblock.configs

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.data.Permissions
import net.arsentic.arsenticskyblock.mission.MissionRestart
import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.entity.EntityType

class Options(plugin: ArsenticSkyblock) : Config(plugin) {
    var prefix = "&8[&b&lArsentic&8] &7➜"
    var worldName = "Islands"
    var netherWorldName = worldName + "_nether"
    var chatRankPlaceholder = "[ISLAND_RANK]"
    var chatValuePlaceholder = "[ISLAND_VALUE]"
    var chatNAMEPlaceholder = "[ISLAND_NAME]"
    var chatLevelPlaceholder = "[ISLAND_LEVEL]"
    var placeholderDefaultValue = "N/A"
    var createCooldown = true
    var doIslandBackup = true
    var islandShop = true
    var automaticUpdate = true
    var defaultIslandPublic = true
    var netherIslands = true
    var forceShortPortalRadius = true
    var islandMenu = true
    var voidTeleport = true
    var notifyAvailableUpdate = true
    var clearInventories = false
    var restartUpgradesOnRegen = true
    var allowWaterInNether = true
    var disableLeafDecay = true
    var createIslandonHome = true
    var allowExplosions = true
    var disablePvPBetweenIslandMembers = true
    var disablePvPOnIslands = true
    var deleteBackupsAfterDays = 7
    var regenCooldown = 3600
    var distance = 151
    var backupIntervalMinutes = 60
    var valueUpdateInterval = 20 * 30
    var valuePerLevel = 100.00
    var dailyMoneyInterest = 0.5
    var dailyCrystalsInterest = 5.0
    var dailyExpInterest = 0.01
    var defaultBiome = Biome.PLAINS
    var defaultNetherBiome = Biome.NETHER_WASTES
    var missionRestart = MissionRestart.Daily
    var worldSpawn = "world"
    val defaultPermissions = mutableMapOf<Role, Permissions>()
        get() {
            for (role in Role.values()) {
                if (role == Role.Visitor) {
                    field[role] = Permissions(breakBlocks = false, placeBlocks = false, interact = false, kickMembers = false, inviteMembers = false, regen = false, islandPrivate = false, promote = false, demote = false, useNetherPortal = true, useWarps = true, coop = false, withdrawBank = false, killMobs = false, pickupItems = false, breakSpawners = false)
                } else {
                    field[role] = Permissions(breakBlocks = true, placeBlocks = true, interact = true, kickMembers = true, inviteMembers = true, regen = true, islandPrivate = true, promote = true, demote = true, useNetherPortal = true, useWarps = true, coop = true, withdrawBank = true, killMobs = true, pickupItems = true, breakSpawners = true)
                }
            }

            return field
        }


    val islandTopSlots = mutableMapOf<Int, Int>()
        get() {
            field[1] = 4
            field[2] = 12
            field[3] = 14
            field[4] = 19
            field[5] = 20
            field[6] = 21
            field[7] = 22
            field[8] = 23
            field[9] = 24
            field[10] = 25
            return field
        }

    val limitedBlocks = mutableMapOf<Material, Int>()
        get() {
            field[Material.HOPPER] = 50
            field[Material.SPAWNER] = 10
            return field
        }

    var blockvalue = mutableMapOf<Material, Int>()
    var spawnervalue = mutableMapOf<String, Double>()
    var biomes = Biome.values().toList()
    var blockedEntities = listOf(EntityType.PRIMED_TNT, EntityType.MINECART_TNT, EntityType.FIREBALL, EntityType.SMALL_FIREBALL, EntityType.ENDER_PEARL)

}