package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CoopCommand(plugin: IridiumSkyblock): Command(plugin, listOf("coop"), "Coops you to an island", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val p = sender as Player
        val user = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(p)
        if (user.island != null) {
            if (args.size != 2) {
                p.openInventory(user.island.getCoopGUI().getInventory())
                return
            }
            val player = Bukkit.getOfflinePlayer(args[1])
            val u = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player)
            if (!user.island.equals(u.island) && u.island != null) {
                if (user.bypassing || user.island.getPermissions(user.getRole()).coop) {
                    if (user.island.coopInvites.contains(u.islandID)) {
                        user.island.coopInvites.remove(u.islandID)
                        user.island.addCoop(u.island)
                    } else {
                        u.island.inviteCoop(user.island)
                        sender.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().coopInviteSent.replace(
                                    "%player%",
                                    _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(u.island.getOwner()).name
                                ).replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
                            )
                        )
                    }
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
            } else {
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().playerNoIsland.replace(
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
        if (island != null) {
            if (args.size != 4) {
                p.openInventory(island.getCoopGUI().getInventory())
                return
            }
            val player = Bukkit.getOfflinePlayer(args[3])
            val u = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player)
            if (!island.equals(u.island) && u.island != null) {
                if (island.coopInvites.contains(u.islandID)) {
                    island.coopInvites.remove(u.islandID)
                    island.addCoop(u.island)
                } else {
                    u.island.inviteCoop(island)
                    sender.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().coopInviteSent.replace(
                                "%player%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(u.island.getOwner()).name
                            ).replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
                        )
                    )
                }
            } else {
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().playerNoIsland.replace(
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