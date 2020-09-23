package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class JoinCommand(plugin: IridiumSkyblock): Command(plugin, listOf("join"), "Join another players island", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 2) {
            sender.sendMessage(_root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix).toString() + "/is join <player>")
            return
        }
        val p = sender as Player
        val user = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(p)
        val player = Bukkit.getOfflinePlayer(args[1])
        val u = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player)
        if (u.island != null) {
            if (user.island == null) {
                val island: Island = u.island
                if (user.invites.contains(island.getId())) {
                    island.addUser(user)
                } else {
                    sender.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().noActiveInvites.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                            )
                        )
                    )
                }
            } else {
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().playerAlreadyHaveIsland.replace(
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