package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.data.User
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VisitCommand(plugin: ArsenticSkyblock) : Command(plugin, listOf("visit"), "Visit another players island", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val p = sender as Player
        if (args.size != 2) {
            p.openInventory(_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.visitGUI[1]!!.inventory)
            return
        }
        val player = Bukkit.getOfflinePlayer(args[1])
        val user = User.getUser(player)
        if (user.island != null) {
            if (user.island.isVisit() || User.getUser(p).bypassing) {
                user.island.teleportHome(p)
            } else {
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().playersIslandIsPrivate.replace(
                            "%prefix%",
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                        )
                    )
                )
            }
        } else {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                    _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().noIsland.replace(
                        "%prefix%",
                        _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
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