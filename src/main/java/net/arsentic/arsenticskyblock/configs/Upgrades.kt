package net.arsentic.arsenticskyblock.configs

import java.util.*

class Upgrades {
    var sizeUpgrade = Upgrade(true, 10, object : HashMap<Int, IslandUpgrade>() {
        init {
            put(1, IslandUpgrade(15, 0, 50))
            put(2, IslandUpgrade(15, 0, 100))
            put(3, IslandUpgrade(15, 0, 150))
        }
    })
    var memberUpgrade = Upgrade(true, 12, object : HashMap<Int, IslandUpgrade>() {
        init {
            put(1, IslandUpgrade(15, 0, 9))
            put(2, IslandUpgrade(15, 0, 18))
            put(3, IslandUpgrade(15, 0, 27))
        }
    })
    var warpUpgrade = Upgrade(true, 14, object : HashMap<Int, IslandUpgrade>() {
        init {
            put(1, IslandUpgrade(15, 0, 2))
            put(2, IslandUpgrade(15, 0, 5))
            put(3, IslandUpgrade(15, 0, 9))
        }
    })
    var oresUpgrade = Upgrade(true, 16, object : HashMap<Int, IslandUpgrade>() {
        init {
            put(1, IslandUpgrade(15, 0, mutableListOf("COBBLESTONE:80", "IRON_ORE:30", "COAL_ORE:30", "DIAMOND_ORE:10", "REDSTONE_ORE:30", "LAPIS_ORE:30", "GOLD_ORE:30", "EMERALD_ORE:10"), mutableListOf("NETHERRACK:80", "GLOWSTONE:30", "NETHER_QUARTZ_ORE:30", "SOUL_SAND:30")))
            put(2, IslandUpgrade(15, 0, mutableListOf("COBBLESTONE:55", "IRON_ORE:30", "COAL_ORE:30", "DIAMOND_ORE:20", "REDSTONE_ORE:30", "LAPIS_ORE:30", "GOLD_ORE:30", "EMERALD_ORE:20"), mutableListOf("NETHERRACK:50", "GLOWSTONE:30", "NETHER_QUARTZ_ORE:30", "SOUL_SAND:30")))
            put(3, IslandUpgrade(15, 0, mutableListOf("COBBLESTONE:30", "IRON_ORE:30", "COAL_ORE:30", "DIAMOND_ORE:30", "REDSTONE_ORE:30", "LAPIS_ORE:30", "GOLD_ORE:30", "EMERALD_ORE:30"), mutableListOf("NETHERRACK:30", "GLOWSTONE:30", "NETHER_QUARTZ_ORE:30", "SOUL_SAND:30")))
        }
    })

    class Upgrade(var enabled: Boolean, var slot: Int, var upgrades: Map<Int, IslandUpgrade>)

    class IslandUpgrade {
        var crystalsCost: Int
        var vaultCost: Int
        var size = 0
        var ores = mutableListOf<String>()
        var netherOres = mutableListOf<String>()

        constructor(crystalsCost: Int, vaultCost: Int, ores: MutableList<String>, netherOres: MutableList<String>) {
            this.crystalsCost = crystalsCost
            this.vaultCost = vaultCost
            this.ores = ores
            this.netherOres = netherOres
        }

        constructor(crystalsCost: Int, vaultCost: Int, size: Int) {
            this.crystalsCost = crystalsCost
            this.vaultCost = vaultCost
            this.size = size
        }
    }
}