package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.arsentic.arsenticskyblock.island.Island.Island
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class Command(var plugin: IridiumSkyblock, var enabled: Boolean, val player: Boolean, permission: String?, description: String?, aliases: List<String>?) {

    abstract fun execute(sender: CommandSender, args: Array<String>)

    abstract fun admin(sender: CommandSender, args: Array<String>, island: Island?)

    abstract fun tabComplete(cs: CommandSender, cmd: Command, s: String, args: Array<String>): List<String>?
}