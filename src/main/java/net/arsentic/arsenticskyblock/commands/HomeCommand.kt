package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HomeCommand(plugin: IridiumSkyblock): Command(plugin, listOf("home"), "Teleport to your island home", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val p = sender as Player
        val user = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(p)
        if (user.island != null) {
            user.island.teleportHome(p)
        } else {
            if (_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().createIslandonHome) {
                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getIslandManager().createIsland(p)
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
    }

    override fun admin(sender: CommandSender, args: Array<String>, island: Island?) {
        val p = sender as Player
        if (island != null) {
            island.teleportHome(p)
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