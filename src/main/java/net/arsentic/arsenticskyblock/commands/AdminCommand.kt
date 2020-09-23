package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.data.User
import net.arsentic.arsenticskyblock.island.Island
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AdminCommand(plugin: ArsenticSkyblock) : Command(plugin, true, false, "Control a player's Island", "iridiumskyblock.admin", listOf("AdminCommand")) {
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
                for (command in _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getCommandManager().commands) {
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
                                    _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().noPermission.replace(
                                        "%prefix%",
                                        _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
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
        val u = User.getUser(player)
        if (u.island != null) {
            p.openInventory(u.island.getIslandAdminGUI().getInventory())
        } else {
            p.sendMessage(
                Utils.color(
                    _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().playerNoIsland.replace(
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

    override fun tabComplete(cs: CommandSender, cmd: org.bukkit.command.Command, s: String, args: Array<String>): List<String>? {
        return null
    }
}