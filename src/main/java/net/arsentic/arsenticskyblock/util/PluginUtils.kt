package net.arsentic.arsenticskyblock.util

import kotlin.reflect.KClass

object PluginUtils {

    /**
     * @author Esophose
     */
    @JvmStatic
    fun <T : Enum<T>> parseEnum(enum: KClass<T>, value: String): T {
        try {
            return enum.java.enumConstants.first { it.name.equals(value, true) } ?: error("")
        } catch (ex: Exception) {
            error("Invalid ${enum.simpleName} specified: $value")
        }
    }

}