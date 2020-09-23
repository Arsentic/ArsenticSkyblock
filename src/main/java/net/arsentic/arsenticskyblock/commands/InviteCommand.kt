package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class InviteCommand(plugin: IridiumSkyblock): Command(plugin, listOf("invite"), "Invite a player to your island", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 2) {
            sender.sendMessage(_root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix).toString() + "/is invite player")
            return
        }
        val p = sender as Player
        val user = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(p)
        val player = Bukkit.getOfflinePlayer(args[1])
        val u = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player)
        if (user.island != null) {
            if (u.island == null) {
                if (user.bypassing || user.island.getPermissions(user.role).inviteMembers) {
                    u.invites.add(user.island.getId())
                    runCommand(p, player)
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
        if (args.size != 4) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix).toString() + "/is admin <island> invite player"
            )
            return
        }
        val p = sender as Player
        val player = Bukkit.getOfflinePlayer(args[1])
        val u = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(player)
        if (island != null) {
            if (u.island == null) {
                u.invites.add(island.getId())
                runCommand(p, player)
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

    private fun runCommand(player: Player, invitedPlayer: OfflinePlayer) {
        player.sendMessage(
            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils
                .color(
                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().playerInvited.replace("%player%", invitedPlayer.name)
                        .replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
                )
        )
        if (invitedPlayer.player != null) {
            val components = TextComponent.fromLegacyText(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().invitedByPlayer.replace(
                        "%player%", player
                            .name
                    ).replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix)
                )
            )
            val clickEvent = ClickEvent(
                ClickEvent.Action.RUN_COMMAND, "/is join " + player
                    .name
            )
            val hoverEvent =
                HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("Click to join players island!").create())
            for (component in components) {
                component.clickEvent = clickEvent
                component.hoverEvent = hoverEvent
            }
            invitedPlayer.player!!.spigot().sendMessage(*components)
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