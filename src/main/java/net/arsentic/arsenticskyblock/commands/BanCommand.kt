package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.data.User
import net.arsentic.core.library.HexUtils.colorify
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

//listOf("ban"), "Ban a player from visiting your island", "", true
class BanCommand(plugin: ArsenticSkyblock) : Command(plugin, true, false, "arsenticskyblock.ban", "Ban a player from visiting your island") {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 2) {
            sender.sendMessage(colorify(ArsenticSkyblock.getConfiguration().prefix) + "/is ban <player>")
            return
        }

        val p = sender as Player
        val user = User.getUser(p)
        if (user.island != null) {
            val player = Bukkit.getOfflinePlayer(args[1])
            if (!user.island.equals(User.getUser(player).island)) {
                user.island.addBan(User.getUser(player))
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        ArsenticSkyblock.getMessages().playerBanned.replace(
                            "%player%",
                            player.name
                        ).replace("%prefix%", ArsenticSkyblock.getConfiguration().prefix)
                    )
                )
                if (player.player != null) {
                    if (user.island.isInIsland(player.player!!.location)) {
                        player.player.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                ArsenticSkyblock.getMessages().bannedFromIsland.replace(
                                    "%player%",
                                    player.name
                                ).replace("%prefix%", ArsenticSkyblock.getConfiguration().prefix)
                            )
                        )
                        user.island.spawnPlayer(player.player)
                    }
                }
            }
        } else sender.sendMessage(
            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                ArsenticSkyblock.getMessages().noIsland.replace(
                    "%prefix%",
                    ArsenticSkyblock.getConfiguration().prefix
                )
            )
        )
    }

    override fun admin(sender: CommandSender, args: Array<String>, island: Island) {
        if (args.size != 4) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(ArsenticSkyblock.getConfiguration().prefix).toString() + "/is admin <island> ban <player>"
            )
            sender.sendMessage("/is admin <island> ban <player>")
            return
        }
        if (island != null) {
            val player = Bukkit.getOfflinePlayer(args[3])
            if (!island.equals(User.getUser(player).island)) {
                island.addBan(User.getUser(player))
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        ArsenticSkyblock.getMessages().playerBanned.replace(
                            "%player%",
                            player.name
                        ).replace("%prefix%", ArsenticSkyblock.getConfiguration().prefix)
                    )
                )
                if (player.player != null) {
                    if (island.isInIsland(player.player!!.location)) {
                        player.player.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                ArsenticSkyblock.getMessages().bannedFromIsland.replace(
                                    "%player%",
                                    player.name
                                ).replace("%prefix%", ArsenticSkyblock.getConfiguration().prefix)
                            )
                        )
                        island.spawnPlayer(player.player)
                    }
                }
            }
        } else {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                    ArsenticSkyblock.getMessages().noIsland.replace(
                        "%prefix%",
                        ArsenticSkyblock.getConfiguration().prefix
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