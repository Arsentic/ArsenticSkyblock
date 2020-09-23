package net.arsentic.arsenticskyblock.placeholders

import be.maximvdw.placeholderapi.PlaceholderAPI
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent
import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.Bukkit
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MVDWPlaceholderAPIManager {
    fun register() {
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_value") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) NumberFormat.getInstance().format(user.island.getValue()).toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_level") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) NumberFormat.getInstance().format(Math.floor(user.island.getValue() / getConfiguration().valuePerLevel)) + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_rank") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) NumberFormat.getInstance().format(Utils.getIslandRank(user.island).toLong()) + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_owner") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) getUser(user.island.getOwner()).name else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_name") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.getName() else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_crystals") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) NumberFormat.getInstance().format(user.island.getCrystals()).toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_members") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.getMembers().size().toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_members_online") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island == null) return@registerPlaceholder getConfiguration().placeholderDefaultValue
            var online = 0
            for (member in user.island.getMembers()) {
                if (Bukkit.getPlayer(User.Companion.getUser(member).name!!) != null) {
                    online++
                }
            }
            online.toString() + ""
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_upgrade_member_level") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.getMemberLevel().toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_upgrade_member_amount") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.get(user.island.getMemberLevel())!!.size.toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_upgrade_size_level") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.getSizeLevel().toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_upgrade_size_dimensions") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(user.island.getSizeLevel())!!.size.toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_upgrade_ore_level") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.getOreLevel().toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_upgrade_warp_level") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.getWarpLevel().toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_booster_spawner") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.getSpawnerBooster().toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_booster_exp") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.getExpBooster().toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_booster_farming") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.getFarmingBooster().toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_booster_flight") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.getFlightBooster().toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_bank_vault") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.money.toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_bank_experience") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.exp.toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_biome") { e: PlaceholderReplaceEvent ->
            val player = e.player ?: return@registerPlaceholder getConfiguration().placeholderDefaultValue
            val user: User = User.Companion.getUser(player)
            if (user.island != null) user.island.getBiome().name().toString() + "" else getConfiguration().placeholderDefaultValue
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_midnight_seconds") { e: PlaceholderReplaceEvent? ->
            val c = Calendar.getInstance()
            c.add(Calendar.DAY_OF_MONTH, 1)
            c[Calendar.HOUR_OF_DAY] = 0
            c[Calendar.MINUTE] = 0
            c[Calendar.SECOND] = 0
            c[Calendar.MILLISECOND] = 0
            val time = (c.timeInMillis - System.currentTimeMillis()) / 1000
            val day = TimeUnit.SECONDS.toDays(time).toInt()
            val hours = Math.floor(TimeUnit.SECONDS.toHours(time - day * 86400).toDouble()).toInt()
            val second = Math.floor((time - day * 86400 - hours * 3600) % 60.00).toInt()
            second.toString() + ""
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_midnight_minutes") { e: PlaceholderReplaceEvent? ->
            val c = Calendar.getInstance()
            c.add(Calendar.DAY_OF_MONTH, 1)
            c[Calendar.HOUR_OF_DAY] = 0
            c[Calendar.MINUTE] = 0
            c[Calendar.SECOND] = 0
            c[Calendar.MILLISECOND] = 0
            val time = (c.timeInMillis - System.currentTimeMillis()) / 1000
            val day = TimeUnit.SECONDS.toDays(time).toInt()
            val hours = Math.floor(TimeUnit.SECONDS.toHours(time - day * 86400).toDouble()).toInt()
            val minute = Math.floor((time - day * 86400 - hours * 3600) / 60.00).toInt()
            minute.toString() + ""
        }
        PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_midnight_hours") { e: PlaceholderReplaceEvent? ->
            val c = Calendar.getInstance()
            c.add(Calendar.DAY_OF_MONTH, 1)
            c[Calendar.HOUR_OF_DAY] = 0
            c[Calendar.MINUTE] = 0
            c[Calendar.SECOND] = 0
            c[Calendar.MILLISECOND] = 0
            val time = (c.timeInMillis - System.currentTimeMillis()) / 1000
            val day = TimeUnit.SECONDS.toDays(time).toInt()
            val hours = Math.floor(TimeUnit.SECONDS.toHours(time - day * 86400).toDouble()).toInt()
            hours.toString() + ""
        }
        for (i in 0..9) { //TODO there is probably a more efficient way to do this?
            PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_top_name_" + (i + 1)) { e: PlaceholderReplaceEvent? ->
                val islands: List<Island?>? = Utils.getTopIslands()
                if (islands!!.size > i) getUser(Utils.getTopIslands()[i].getOwner()).name else getConfiguration().placeholderDefaultValue
            }
            PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_top_value_" + (i + 1)) { e: PlaceholderReplaceEvent? ->
                val islands: List<Island?>? = Utils.getTopIslands()
                if (islands!!.size > i) Utils.getTopIslands()[i].getValue().toString() + "" else getConfiguration().placeholderDefaultValue
            }
            PlaceholderAPI.registerPlaceholder(getInstance(), "iridiumskyblock_island_top_level_" + (i + 1)) { e: PlaceholderReplaceEvent? ->
                val islands: List<Island?>? = Utils.getTopIslands()
                if (islands!!.size > i) NumberFormat.getInstance().format(Math.floor(Utils.getTopIslands()[i].getValue() / getConfiguration().valuePerLevel)) + "" else getConfiguration().placeholderDefaultValue
            }
        }
    }
}