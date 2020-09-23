package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class KickCommand(plugin: IridiumSkyblock): Command(plugin, listOf("kick"), "Kick a player from your island", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 2) {
            sender.sendMessage(_root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix).toString() + "/is kick <player>")
            return
        }
        val p = sender as Player
        val user = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(p) // User kicking the player
        val player = Bukkit.getOfflinePlayer(args[1])
        val u = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player) // Player we want to kick
        if (user.island != null) {
            if (user.island.equals(u.island)) {
                if (u.role == _root_ide_package_.net.arsentic.arsenticskyblock.Role.Owner) {
                    sender.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().cantKickOwner.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                            )
                        )
                    )
                } else {
                    if (user.bypassing || user.island.getPermissions(u.getRole()).kickMembers) {
                        user.island.removeUser(u)
                        if (player.player != null) player.player.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().youHaveBeenKicked.replace(
                                    "%prefix%",
                                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                                )
                            )
                        )
                    } else {
                        sender.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().noPermission.replace(
                                    "%prefix%",
                                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                                )
                            )
                        )
                    }
                }
            } else {
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().notInYourIsland.replace(
                            "%prefix%",
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                        )
                    )
                )
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
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix).toString() + "/is admin <island> kick <player>"
            )
            return
        }
        val player = Bukkit.getOfflinePlayer(args[1])
        val u = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player) // Player we want to kick
        if (island != null) {
            if (island.equals(u.island)) {
                if (u.role == _root_ide_package_.net.arsentic.arsenticskyblock.Role.Owner) {
                    sender.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().cantKickOwner.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                            )
                        )
                    )
                } else {
                    island.removeUser(u)
                    if (player.player != null) player.player.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().youHaveBeenKicked.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                            )
                        )
                    )
                }
            } else {
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().notInYourIsland.replace(
                            "%prefix%",
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                        )
                    )
                )
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