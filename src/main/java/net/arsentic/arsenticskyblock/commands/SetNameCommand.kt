package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetNameCommand(plugin: IridiumSkyblock): Command(plugin, listOf("setname"), "Set your islands name", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val p = sender as Player
        val user = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(p)
        if (args.size != 2) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix).toString() + "/is setname <Island Name>"
            )
            return
        }
        if (user.island != null) {
            if (user.role == _root_ide_package_.net.arsentic.arsenticskyblock.Role.Owner) {
                user.island.setName(args[1])
                for (member in user.island.getMembers()) {
                    val player = Bukkit.getPlayer(_root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(member).name)
                    player?.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().changesIslandName.replace(
                                "%player%",
                                p.name
                            ).replace("%name%", args[1]).replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
                        )
                    )
                }
            } else {
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().mustBeIslandOwner.replace(
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
        val p = sender as Player
        if (args.size != 2) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
                    .toString() + "/is admin <island> setname <Island Name>"
            )
            return
        }
        if (island != null) {
            island.setName(args[1])
            for (member in island.getMembers()) {
                val player = Bukkit.getPlayer(_root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(member).name)
                player?.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().changesIslandName.replace(
                            "%player%",
                            p.name
                        ).replace("%name%", args[1]).replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
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