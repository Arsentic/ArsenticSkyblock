package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.data.User
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetWarpCommand(plugin: ArsenticSkyblock) : Command(plugin, listOf("setwarp", "addwarp"), "Set a new island warp", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val p = sender as Player
        if (args.size == 2 || args.size == 3) {
            val user = User.getUser(p)
            if (user.island != null) {
                val password = if (args.size == 3) args[2] else ""
                if (_root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.isSafe(p.location, user.island)) {
                    user.island.addWarp(p, p.location, args[1], password)
                } else {
                    p.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().isNotSafe.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                            )
                        )
                    )
                }
            } else {
                p.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().noIsland.replace(
                            "%prefix%",
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                        )
                    )
                )
            }
        } else {
            p.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix).toString() + "/is setwarp <name> (password)"
            )
        }
    }

    override fun admin(sender: CommandSender, args: Array<String>, island: Island) {
        val p = sender as Player
        if (args.size == 2 || args.size == 3) {
            if (island != null) {
                val password = if (args.size == 3) args[2] else ""
                if (_root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.isSafe(p.location, island)) {
                    island.addWarp(p, p.location, args[1], password)
                } else {
                    p.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().isNotSafe.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                            )
                        )
                    )
                }
            } else {
                p.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().noIsland.replace(
                            "%prefix%",
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                        )
                    )
                )
            }
        } else {
            p.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix).toString() + "/is setwarp <name> (password)"
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