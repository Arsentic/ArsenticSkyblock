package net.arsentic.arsenticskyblock.configs

import org.bukkit.Material

class BlockValues {
    val blockValue = mutableMapOf<Material, Double>()
        get() {
            field[Material.EMERALD_BLOCK] = 20.00
            field[Material.DIAMOND_BLOCK] = 10.00
            field[Material.GOLD_BLOCK] = 5.00
            field[Material.IRON_BLOCK] = 3.00
            field[Material.HOPPER] = 1.00
            field[Material.BEACON] = 100.00
            return field
        }

    val spawnerValue = mutableMapOf<String, Double>()
        get() {
            field["PIG"] = 100.00
            field["IRON_GOLEM"] = 1000.00

            return field
        }
}