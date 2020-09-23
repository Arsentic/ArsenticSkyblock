package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.data.User
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

class RemoveCrystalsCommand :
    Command(listOf("removecrystals"), "remove a player's Crystals", "iridiumskyblock.removecrystals", false) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 3) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix)
                    .toString() + "/is removecrystals <player> <amount>"
            )
            sender.sendMessage("/is removecrystals <player> <amount>")
            return
        }
        if (Bukkit.getPlayer(args[1]) != null) {
            val player: OfflinePlayer? = Bukkit.getPlayer(args[1])
            if (player != null) {
                val island: Island? = User.getUser(player).island
                if (island != null) {
                    if (StringUtils.isNumeric(args[2])) {
                        val amount = args[2].toInt()
                        if (amount <= island.getCrystals()) {
                            island.setCrystals(island.getCrystals() - amount)
                            sender.sendMessage(
                                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                    _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().removedcrystals.replace(
                                        "%crystals%",
                                        args[2]
                                    ).replace("%player%", player.name)
                                        .replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix)
                                )
                            )
                        } else {
                            sender.sendMessage(
                                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                    _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().playerNotEnoughCrystals.replace(
                                        "%player%",
                                        player.name
                                    ).replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix)
                                )
                            )
                        }
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