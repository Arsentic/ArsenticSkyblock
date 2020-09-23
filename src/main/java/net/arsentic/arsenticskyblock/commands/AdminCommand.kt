package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.arsentic.arsenticskyblock.island.Island.Island
import net.arsentic.arsenticskyblock.util.Utils
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AdminCommand(plugin: IridiumSkyblock) : Command(plugin, listOf("admin"), "Control a players Island", "iridiumskyblock.admin", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val player = sender as Player


        if (args.size == 2) {
            runCommand(args, player)


        } else if (args.size >= 2) {
            val island: Island

            if (!StringUtils.isNumeric(args[1])) {
                runCommand(args, player)
                return
            }


            val id = args[1].toInt()
            island = plugin.getIslandManager().getIslandViaId(id)
            if (island != null) {
                for (command in _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommandManager().commands) {
                    if (command.getAliases().contains(args[2]) && command.isEnabled()) {
                        if ((sender.hasPermission(command.getPermission()) || command.getPermission()
                                .equalsIgnoreCase("") || command.getPermission()
                                .equalsIgnoreCase("iridiumskyblock.")) && command.isEnabled()
                        ) {
                            command.admin(sender, args, island)
                        } else {
                            // No permission
                            sender.sendMessage(
                                Utils.color(
                                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().noPermission.replace(
                                        "%prefix%",
                                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                                    )
                                )
                            )
                        }
                        return
                    }
                }
            }
        } else {
            sender.sendMessage("/is admin <island>")
        }
    }

    private fun runCommand(args: Array<String>, p: Player) {
        val player = Bukkit.getOfflinePlayer(args[1])
        val u = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player)
        if (u.island != null) {
            p.openInventory(u.island.getIslandAdminGUI().getInventory())
        } else {
            p.sendMessage(
                Utils.color(
                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().playerNoIsland.replace(
                        "%prefix%",
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                    )
                )
            )
        }
    }

    override fun admin(sender: CommandSender, args: Array<String>, island: Island) {
        execute(sender, args)
    }

    override fun tabComplete(cs: CommandSender, cmd: org.bukkit.command.Command, s: String, args: Array<String>): List<String>? {
        return null
    }
}