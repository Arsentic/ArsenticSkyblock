package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import net.arsentic.arsenticskyblock.data.User
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

class GiveUpgradeCommand(plugin: ArsenticSkyblock) : Command(plugin, listOf("giveupgrade"), "Give an Island an Upgrade", "iridiumskyblock.giveupgrade", false) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 4 && args.size != 3) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix)
                    .toString() + "/is giveupgrade <player> <upgrade> <level>"
            )
            return
        }
        if (Bukkit.getPlayer(args[1]) != null) {
            val player: OfflinePlayer? = Bukkit.getPlayer(args[1])
            if (player != null) {
                val island: Island? = User.getUser(player).island
                if (island != null) {
                    try {
                        if (args[2].equals("size", ignoreCase = true)) {
                            val amount = if (args.size == 3) island.getSizeLevel() + 1 else args[3].toInt()
                            if (_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getUpgrades().sizeUpgrade.upgrades.containsKey(amount)) {
                                island.setSizeLevel(amount)
                            }
                        }
                        if (args[2].equals("member", ignoreCase = true)) {
                            val amount = if (args.size == 3) island.getMemberLevel() + 1 else args[3].toInt()
                            if (_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getUpgrades().memberUpgrade.upgrades.containsKey(amount)) {
                                island.setMemberLevel(amount)
                            }
                        }
                        if (args[2].equals("warp", ignoreCase = true)) {
                            val amount = if (args.size == 3) island.getWarpLevel() + 1 else args[3].toInt()
                            if (_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getUpgrades().warpUpgrade.upgrades.containsKey(amount)) {
                                island.setWarpLevel(amount)
                            }
                        }
                        if (args[2].equals("ores", ignoreCase = true)) {
                            val amount = if (args.size == 3) island.getOreLevel() + 1 else args[3].toInt()
                            if (_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getUpgrades().oresUpgrade.upgrades.containsKey(amount)) {
                                island.setOreLevel(amount)
                            }
                        }
                    } catch (e: NumberFormatException) {
                        sender.sendMessage(args[2] + " is not a number")
                    }
                } else {
                    sender.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().playerNoIsland.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                            )
                        )
                    )
                }
            } else {
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().playerOffline.replace(
                            "%prefix%",
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                        )
                    )
                )
            }
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
        return if (args.size == 3) {
            listOf("size", "member", "warp", "ores")
        } else null
    }
}