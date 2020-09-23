package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BanCommand(plugin: IridiumSkyblock): Command(plugin, listOf("ban"), "Ban a player from visiting your island", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 2) {
            sender.sendMessage(_root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix).toString() + "/is ban <player>")
            sender.sendMessage("/is ban <player>")
            return
        }
        val p = sender as Player
        val user = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(p)
        if (user.island != null) {
            val player = Bukkit.getOfflinePlayer(args[1])
            if (!user.island.equals(_root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player).island)) {
                user.island.addBan(_root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player))
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().playerBanned.replace(
                            "%player%",
                            player.name
                        ).replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
                    )
                )
                if (player.player != null) {
                    if (user.island.isInIsland(player.player!!.location)) {
                        player.player.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().bannedFromIsland.replace(
                                    "%player%",
                                    player.name
                                ).replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
                            )
                        )
                        user.island.spawnPlayer(player.player)
                    }
                }
            }
        } else {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().noIsland.replace(
                        "%prefix%",
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                    )
                )
            )
        }
    }

    override fun admin(sender: CommandSender, args: Array<String>, island: Island?) {
        if (args.size != 4) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix).toString() + "/is admin <island> ban <player>"
            )
            sender.sendMessage("/is admin <island> ban <player>")
            return
        }
        if (island != null) {
            val player = Bukkit.getOfflinePlayer(args[3])
            if (!island.equals(_root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player).island)) {
                island.addBan(_root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player))
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().playerBanned.replace(
                            "%player%",
                            player.name
                        ).replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
                    )
                )
                if (player.player != null) {
                    if (island.isInIsland(player.player!!.location)) {
                        player.player.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().bannedFromIsland.replace(
                                    "%player%",
                                    player.name
                                ).replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
                            )
                        )
                        island.spawnPlayer(player.player)
                    }
                }
            }
        } else {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().noIsland.replace(
                        "%prefix%",
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                    )
                )
            )
        }
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