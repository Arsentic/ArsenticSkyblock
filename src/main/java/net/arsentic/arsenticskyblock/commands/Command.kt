package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.configs.Messages
import net.arsentic.arsenticskyblock.configs.Options
import net.arsentic.arsenticskyblock.island.Island
import net.arsentic.arsenticskyblock.manager.ConfigManager
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class Command(var plugin: ArsenticSkyblock, var enabled: Boolean, val player: Boolean, permission: String?, description: String?, aliases: List<String>?) {

    abstract fun execute(sender: CommandSender, args: Array<String>)

    abstract fun admin(sender: CommandSender, args: Array<String>, island: Island?)

    abstract fun tabComplete(cs: CommandSender, cmd: Command, s: String, args: Array<String>): List<String>?

    val config = plugin.getManager(ConfigManager::class).getConfig(Options::class)

    val messages = plugin.getManager(ConfigManager::class).getConfig(Messages::class)
}