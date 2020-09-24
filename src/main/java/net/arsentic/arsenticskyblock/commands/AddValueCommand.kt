package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.data.User
import net.arsentic.arsenticskyblock.island.Island
import net.arsentic.arsenticskyblock.manager.IslandManager
import net.arsentic.core.library.HexUtils.colorify
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

class AddValueCommand(plugin: ArsenticSkyblock) : Command(plugin, true, false, "Give an island extra value", "iridiumskyblock.addvalue", listOf("addvalue")) {
    val islandManager = plugin.getManager(IslandManager::class)

    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 3) {
            sender.sendMessage(colorify("${config.prefix} /is addvalue <player> <amount>"))
            return
        }

        val player = Bukkit.getPlayer(args[1])

        if (player != null) {

            val island = plugin.getManager(IslandManager::class).getUser(player)?.island


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
                        ArsenticSkyblock.getMessages().playerNoIsland.replace(
                            "%prefix%",
                            ArsenticSkyblock.getConfiguration().prefix
                        )
                    )
                )
            }
        }
    }

    override fun admin(sender: CommandSender, args: Array<String>, island: Island) {
        execute(sender, args)
    }

    override fun tabComplete(cs: CommandSender, cmd: org.bukkit.command.Command, string: String, args: Array<String>): List<String>? {
        return null
    }
}