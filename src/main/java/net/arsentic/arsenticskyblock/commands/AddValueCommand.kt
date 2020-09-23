package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.island.Island
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

class AddValueCommand(plugin: ArsenticSkyblock) : Command(plugin, true, false, "Give an island extra value", "iridiumskyblock.addvalue", listOf("addvalue")) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 3) {
            sender.sendMessage(_root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix).toString() + "/is addvalue <player> <amount>")
            return
        }

        if (Bukkit.getPlayer(args[1]) != null) {
            val player: OfflinePlayer? = Bukkit.getPlayer(args[1])
            if (player != null) {
                val island: Island? = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player).island
                if (island != null) {
                    try {
                        island.addExtraValue(args[2].toDouble())
                    } catch (e: NumberFormatException) {
                        //TODO: Make this message configurable
                        sender.sendMessage(args[2] + " is not a number")
                    }
                } else {
                    sender.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().playerNoIsland.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                            )
                        )
                    )
                }
            } else {
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().playerOffline.replace(
                            "%prefix%",
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                        )
                    )
                )
            }
        }
    }

    override fun admin(sender: CommandSender, args: Array<String>, island: Island?) {
        execute(sender, args)
    }

    override fun tabComplete(cs: CommandSender, cmd: org.bukkit.command.Command, string: String, args: Array<String>): List<String>? {
        return null
    }
}