package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class FlyCommand(plugin: IridiumSkyblock): Command(plugin, listOf("fly", "flight"), "Toggle your ability to fly", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val p = sender as Player
        val user = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(p)
        if (user.island != null) {
            if (user.island.isInIsland(p.location)) {
                if (user.island.getFlightBooster() !== 0 || p.hasPermission("iridiumskyblock.Fly")) {
                    if (p.allowFlight) {
                        p.allowFlight = false
                        p.isFlying = false
                        p.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().flightDisabled.replace(
                                    "%prefix%",
                                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                                )
                            )
                        )
                    } else {
                        p.allowFlight = true
                        p.isFlying = true
                        p.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().flightEnabled.replace(
                                    "%prefix%",
                                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                                )
                            )
                        )
                    }
                    user.flying = p.isFlying
                } else {
                    p.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().flightBoosterNotActive.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                            )
                        )
                    )
                }
            } else {
                p.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().mustBeInIsland.replace(
                            "%prefix%",
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                        )
                    )
                )
            }
        } else {
            p.sendMessage(
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
        execute(sender, args)
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