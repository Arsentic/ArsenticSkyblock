package net.arsentic.arsenticskyblock.placeholders

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.User
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ClipPlaceholderAPIManager : PlaceholderExpansion() {
    // Identifier for this expansion
    override fun getIdentifier(): String {
        return "iridiumskyblock"
    }

    override fun getAuthor(): String {
        return "Peaches_MLG"
    }

    // Since we are registering this expansion from the dependency, this can be null
    override fun getPlugin(): String {
        return null
    }

    // Return the plugin version since this expansion is bundled with the dependency
    override fun getVersion(): String {
        return getInstance().getDescription().getVersion()
    }

    override fun onPlaceholderRequest(player: Player, placeholder: String): String {
        if (player == null || placeholder == null) {
            return ""
        }
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
        val second = Math.floor((time - day * 86400 - hours * 3600) % 60.00).toInt()
        val user: User = User.Companion.getUser(player)
        when (placeholder) {
            "island_value" -> return if (user.island != null) NumberFormat.getInstance().format(user.island.getValue()).toString() + "" else getConfiguration().placeholderDefaultValue
            "island_level" -> return if (user.island != null) NumberFormat.getInstance().format(Math.floor(user.island.getValue() / getConfiguration().valuePerLevel)) + "" else getConfiguration().placeholderDefaultValue
            "island_rank" -> return if (user.island != null) NumberFormat.getInstance().format(Utils.getIslandRank(user.island).toLong()) + "" else getConfiguration().placeholderDefaultValue
            "island_owner" -> return if (user.island != null) getUser(user.island.getOwner()).name else getConfiguration().placeholderDefaultValue
            "island_name" -> return if (user.island != null) user.island.getName() else getConfiguration().placeholderDefaultValue
            "island_crystals" -> return if (user.island != null) NumberFormat.getInstance().format(user.island.getCrystals()).toString() + "" else getConfiguration().placeholderDefaultValue
            "island_members" -> return if (user.island != null) user.island.getMembers().size().toString() + "" else getConfiguration().placeholderDefaultValue
            "island_members_online" -> {
                if (user.island == null) return getConfiguration().placeholderDefaultValue
                var online = 0
                for (member in user.island.getMembers()) {
                    if (Bukkit.getPlayer(User.Companion.getUser(member).name!!) != null) {
                        online++
                    }
                }
                return online.toString() + ""
            }
            "island_upgrade_member_level" -> return if (user.island != null) NumberFormat.getInstance().format(user.island.getMemberLevel()).toString() + "" else getConfiguration().placeholderDefaultValue
            "island_upgrade_member_amount" -> return if (user.island != null) ArsenticSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.get(user.island.getMemberLevel())!!.size.toString() + "" else getConfiguration().placeholderDefaultValue
            "island_upgrade_size_level" -> return if (user.island != null) user.island.getSizeLevel().toString() + "" else getConfiguration().placeholderDefaultValue
            "island_upgrade_size_dimensions" -> return if (user.island != null) ArsenticSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(user.island.getSizeLevel())!!.size.toString() + "" else getConfiguration().placeholderDefaultValue
            "island_upgrade_ore_level" -> return if (user.island != null) user.island.getOreLevel().toString() + "" else getConfiguration().placeholderDefaultValue
            "island_upgrade_warp_level" -> return if (user.island != null) user.island.getWarpLevel().toString() + "" else getConfiguration().placeholderDefaultValue
            "island_booster_spawner" -> return if (user.island != null) user.island.getSpawnerBooster().toString() + "" else getConfiguration().placeholderDefaultValue
            "island_booster_exp" -> return if (user.island != null) user.island.getExpBooster().toString() + "" else getConfiguration().placeholderDefaultValue
            "island_booster_farming" -> return if (user.island != null) user.island.getFarmingBooster().toString() + "" else getConfiguration().placeholderDefaultValue
            "island_booster_flight" -> return if (user.island != null) user.island.getFlightBooster().toString() + "" else getConfiguration().placeholderDefaultValue
            "island_bank_vault" -> return if (user.island != null) user.island.money.toString() + "" else getConfiguration().placeholderDefaultValue
            "island_bank_experience" -> return if (user.island != null) user.island.exp.toString() + "" else getConfiguration().placeholderDefaultValue
            "island_biome" -> return if (user.island != null) user.island.getBiome().name().toString() + "" else getConfiguration().placeholderDefaultValue
            "midnight_seconds" -> return second.toString() + ""
            "midnight_minutes" -> return minute.toString() + ""
            "midnight_hours" -> return hours.toString() + ""
        }
        if (placeholder.startsWith("island_top_name_")) {
            try {
                val integer = placeholder.replace("island_top_name_", "").toInt()
                val islands: List<Island?>? = Utils.getTopIslands()
                return if (islands!!.size > integer - 1) getUser(Utils.getTopIslands()[integer - 1].getOwner()).name else getConfiguration().placeholderDefaultValue
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }
        if (placeholder.startsWith("island_top_value_")) {
            try {
                val integer = placeholder.replace("island_top_value_", "").toInt()
                val islands: List<Island?>? = Utils.getTopIslands()
                return if (islands!!.size > integer - 1) NumberFormat.getInstance().format(Utils.getTopIslands()[integer - 1].getValue()).toString() + "" else getConfiguration().placeholderDefaultValue
            } catch (ignored: NumberFormatException) {
            }
        }
        if (placeholder.startsWith("island_top_level_")) {
            try {
                val integer = placeholder.replace("island_top_level_", "").toInt()
                val islands: List<Island?>? = Utils.getTopIslands()
                return if (islands!!.size > integer - 1) NumberFormat.getInstance().format(Math.floor(Utils.getTopIslands()[integer - 1].getValue() / getConfiguration().valuePerLevel)) + "" else getConfiguration().placeholderDefaultValue
            } catch (ignored: NumberFormatException) {
            }
        }
        return null
    }
}