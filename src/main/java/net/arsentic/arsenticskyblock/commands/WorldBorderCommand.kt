package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.data.User
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WorldBorderCommand(plugin: ArsenticSkyblock) : Command(plugin, listOf("border", "worldborder", "color", "colour"), "Opens the WorldBorder GUI", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val p = sender as Player
        val user = User.getUser(p)
        if (user.island != null) {
            p.openInventory(user.island.getBorderColorGUI().getInventory())
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

    override fun admin(sender: CommandSender, args: Array<String>, island: Island) {
        val p = sender as Player
        if (island != null) {
            p.openInventory(island.getBorderColorGUI().getInventory())
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

    override fun tabComplete(
        cs: CommandSender?,
        cmd: org.bukkit.command.Command?,
        s: String?,
        args: Array<String>
    ): List<String>? {
        return null
    }
}