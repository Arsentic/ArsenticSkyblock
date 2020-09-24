package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.data.User
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ValueCommand(plugin: ArsenticSkyblock) : Command(plugin, listOf("value"), "Shows your island value", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val p = sender as Player
        val user = User.getUser(p)
        val island: Island?
        island = if (args.size == 2) {
            val player = Bukkit.getOfflinePlayer(args[1])
            User.getUser(player).island
        } else {
            user.island
        }
        if (island != null) {
            p.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                    _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().islandValue.replace(
                        "%value%",
                        island.getValue().toString() + ""
                    ).replace("%rank%", _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.getIslandRank(island).toString() + "")
                        .replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix)
                )
            )
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
    }

    override fun admin(sender: CommandSender, args: Array<String>, island: Island) {
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