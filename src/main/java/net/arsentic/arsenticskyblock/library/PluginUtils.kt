package net.arsentic.arsenticskyblock.library

import org.apache.commons.lang.StringUtils
import org.bukkit.inventory.ItemStack
import java.text.SimpleDateFormat
import java.util.*

object PluginUtils {
    fun formatTime(time: Long): String {
        return SimpleDateFormat("HH:mm dd/m/yyy").format(Date(time))
    }

    fun transformName(itemStack: ItemStack): String {
        return StringUtils.capitalize(itemStack.type.name.replace("_", " ").toLowerCase())
    }

    fun isNumber(string: String): Boolean {
        try {
            string.toInt()
        } catch (ex: NumberFormatException) {
            return false
        }
        return true
    }
}