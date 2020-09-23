package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.data.User
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

class GiveBoosterCommand :
    Command(listOf("givebooster"), "Give an Island a Booster", "iridiumskyblock.givebooster", false) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 4 && args.size != 3) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix)
                    .toString() + "/is givebooster <player> <booster> <amount>"
            )
            return
        }
        if (Bukkit.getPlayer(args[1]) != null) {
            val player: OfflinePlayer? = Bukkit.getPlayer(args[1])
            if (player != null) {
                val island: Island? = User.getUser(player).island
                if (island != null) {
                    if (args.size == 3 || StringUtils.isNumeric(args[3])) {
                        val amount = if (args.size == 3) 3600 else args[3].toInt()
                        if (args[2].equals("exp", ignoreCase = true)) {
                            island.setExpBooster(amount)
                        }
                        if (args[2].equals("farming", ignoreCase = true)) {
                            island.setFarmingBooster(amount)
                        }
                        if (args[2].equals("flight", ignoreCase = true)) {
                            island.setFlightBooster(amount)
                        }
                        if (args[2].equals("spawner", ignoreCase = true)) {
                            island.setSpawnerBooster(amount)
                        }
                    } else {
                        //TODO: Make this message configurable
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
            listOf("exp", "farming", "flight", "spawner")
        } else null
    }
}