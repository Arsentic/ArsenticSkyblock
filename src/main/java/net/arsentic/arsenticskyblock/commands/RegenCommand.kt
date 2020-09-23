package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

class RegenCommand(plugin: ArsenticSkyblock) : Command(plugin, listOf("regen", "reset"), "Regenerate your island", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val p = sender as Player
        val user = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(p)
        if (user.island != null) {
            if (user.role == _root_ide_package_.net.arsentic.arsenticskyblock.Role.Owner) {
                if (user.bypassing || user.island.getPermissions(user.role).regen) {
                    val time: Long = user.island.canGenerate() / 1000
                    if (time == 0L || user.bypassing) {
                        if (_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getSchematics().schematics.size() === 1) {
                            p.openInventory(_root_ide_package_.net.arsentic.arsenticskyblock.gui.ConfirmationGUI(user.island, {
                                for (schematic in _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getSchematics().schematics) {
                                    user.island.setSchematic(schematic.name)
                                    user.island.setHome(
                                        user.island.getHome().add(schematic.x, schematic.y, schematic.z)
                                    )
                                    user.island.setNetherhome(
                                        user.island.getNetherhome().add(schematic.x, schematic.y, schematic.z)
                                    )
                                }
                                user.island.pasteSchematic(true)
                                if (_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().restartUpgradesOnRegen) {
                                    user.island.resetMissions()
                                    user.island.setSizeLevel(1)
                                    user.island.setMemberLevel(1)
                                    user.island.setWarpLevel(1)
                                    user.island.setOreLevel(1)
                                    user.island.setFlightBooster(0)
                                    user.island.setExpBooster(0)
                                    user.island.setFarmingBooster(0)
                                    user.island.setSpawnerBooster(0)
                                    user.island.setCrystals(0)
                                    user.island.exp = 0
                                    user.island.money = 0
                                }
                                user.island.teleportPlayersHome()
                            }, _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().resetAction).inventory)
                        } else {
                            p.openInventory(user.island.getSchematicSelectGUI().getInventory())
                        }
                    } else {
                        val day = TimeUnit.SECONDS.toDays(time).toInt()
                        val hours = Math.floor(TimeUnit.SECONDS.toHours(time - day * 86400).toDouble())
                            .toInt()
                        val minute = Math.floor((time - day * 86400 - hours * 3600) / 60.00).toInt()
                        val second = Math.floor((time - day * 86400 - hours * 3600) % 60.00).toInt()
                        p.sendMessage(
                            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().regenCooldown.replace(
                                    "%days%",
                                    day.toString() + ""
                                ).replace("%hours%", hours.toString() + "").replace("%minutes%", minute.toString() + "")
                                    .replace("%seconds%", second.toString() + "")
                                    .replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix)
                            )
                        )
                    }
                } else {
                    sender.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().noPermission.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                            )
                        )
                    )
                }
            } else {
                sender.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().mustBeIslandOwner.replace(
                            "%prefix%",
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                        )
                    )
                )
            }
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

    override fun admin(sender: CommandSender, args: Array<String>, island: Island?) {
        val p = sender as Player
        if (island != null) {
            if (_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getSchematics().schematics.size() === 1) {
                p.openInventory(_root_ide_package_.net.arsentic.arsenticskyblock.gui.ConfirmationGUI(island, {
                    for (schematic in _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getSchematics().schematics) {
                        island.setSchematic(schematic.name)
                        island.setHome(island.getHome().add(schematic.x, schematic.y, schematic.z))
                        island.setNetherhome(island.getNetherhome().add(schematic.x, schematic.y, schematic.z))
                    }
                    island.pasteSchematic(true)
                    if (_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().restartUpgradesOnRegen) {
                        island.resetMissions()
                        island.setSizeLevel(1)
                        island.setMemberLevel(1)
                        island.setWarpLevel(1)
                        island.setOreLevel(1)
                        island.setFlightBooster(0)
                        island.setExpBooster(0)
                        island.setFarmingBooster(0)
                        island.setSpawnerBooster(0)
                        island.setCrystals(0)
                        island.exp = 0
                        island.money = 0
                    }
                    island.teleportPlayersHome()
                }, _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().resetAction).inventory)
            } else {
                p.openInventory(island.getSchematicSelectGUI().getInventory())
            }
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