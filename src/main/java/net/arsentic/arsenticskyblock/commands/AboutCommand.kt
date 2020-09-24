package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.island.Island
import net.arsentic.core.library.HexUtils.colorify
import org.bukkit.command.CommandSender

class AboutCommand(plugin: ArsenticSkyblock) : Command(plugin, true, false, "", "Displays plugin info", listOf("about", "version")) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        sender.sendMessage(colorify("&8Plugin Name: &7ArsenticSkyblock"))
        sender.sendMessage(colorify("&8Plugin Version: &7${plugin.description.version}"))
        sender.sendMessage(colorify("&8Coded by Arsentic LTD"))
    }

    override fun admin(sender: CommandSender, args: Array<String>, island: Island) {
        execute(sender, args)
    }

    override fun tabComplete(cs: CommandSender, cmd: org.bukkit.command.Command, s: String, args: Array<String>): List<String>? {
        return null
    }
}