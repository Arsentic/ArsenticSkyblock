package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TransferCommand(plugin: IridiumSkyblock): Command(plugin, listOf("transfer"), "Transfer island ownership", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 2) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix).toString() + "/is transfer <player>"
            )
            return
        }
        val p = sender as Player
        val user = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(p)
        if (user.island != null) {
            val island: Island = user.island
            if (island.getOwner().equals(p.uniqueId.toString())) {
                val player = Bukkit.getOfflinePlayer(args[1])
                if (_root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player).island === island) {
                    p.openInventory(
                        _root_ide_package_.net.arsentic.arsenticskyblock.gui.ConfirmationGUI(
                            user.island,
                            { island.setOwner(player) },
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().transferAction.replace("%player%", player.name)
                        ).inventory
                    )
                } else {
                    sender.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().notInYourIsland.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                            )
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
        if (args.size != 4) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
                    .toString() + "/is admin <island> transfer <player>"
            )
            return
        }
        val p = sender as Player
        if (island != null) {
            val player = Bukkit.getOfflinePlayer(args[1])
            if (_root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player).island === island) {
                p.openInventory(
                    _root_ide_package_.net.arsentic.arsenticskyblock.gui.ConfirmationGUI(
                        island,
                        { island.setOwner(player) },
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().transferAction.replace("%player%", player.name)
                    ).inventory
                )
            } else {
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().notInYourIsland.replace(
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