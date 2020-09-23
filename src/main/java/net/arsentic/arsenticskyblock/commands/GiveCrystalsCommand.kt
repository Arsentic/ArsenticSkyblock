package net.arsentic.arsenticskyblock.commands

import org.apache.commons.lang.math.NumberUtils
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

class GiveCrystalsCommand :
    Command(listOf("givecrystals"), "Give a player Crystals", "iridiumskyblock.givecrystals", false) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 3) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix).toString() + "/is givecrystals <player> <amount>"
            )
            return
        }
        if (Bukkit.getPlayer(args[1]) != null) {
            val player: OfflinePlayer? = Bukkit.getPlayer(args[1])
            if (player != null) {
                val island: Island? = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player).island
                if (island != null) {
                    if (NumberUtils.isNumber(args[2])) {
                        val amount = args[2].toInt()
                        island.setCrystals(island.getCrystals() + amount)
                        sender.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().giveCrystals.replace(
                                    "%crystals%",
                                    args[2]
                                ).replace("%player%", player.name)
                                    .replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix)
                            )
                        )
                        if (player.player != null) player.player.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().givenCrystals.replace(
                                    "%crystals%",
                                    args[2]
                                ).replace("%player%", sender.name)
                                    .replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix)
                            )
                        )
                    } else {
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

    override fun tabComplete(
        cs: CommandSender?,
        cmd: org.bukkit.command.Command?,
        s: String?,
        args: Array<String>
    ): List<String>? {
        return null
    }
}