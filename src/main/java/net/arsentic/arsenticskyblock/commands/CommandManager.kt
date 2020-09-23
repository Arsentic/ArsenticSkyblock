package net.arsentic.arsenticskyblock.commands

import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.*

class CommandManager(command: String?) : CommandExecutor, TabCompleter {
    var commands: MutableList<Command> = ArrayList()
    fun registerCommands() {
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().aboutCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().boosterCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().bypassCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().createCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().crystalsCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().deleteCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().flyCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().homeCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().inviteCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().joinCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().kickCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().leaveCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().membersCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().missionsCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().privateCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().publicCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().regenCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().reloadCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().topCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().upgradeCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().valueCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().visitCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().warpCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().warpsCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().giveCrystalsCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().removeCrystalsCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().worldBorderCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().setHomeCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().permissionsCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().transferCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().adminCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().giveBoosterCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().banCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().unBanCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().coopCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().unCoopCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().setNameCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().bankCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().chatCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().giveUpgradeCommand)
        if (_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().islandShop) registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().shopCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().biomeCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().helpCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().languagesCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().recalculateCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().removeValueCommand)
        registerCommand(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommands().addValueCommand)
    }

    fun registerCommand(command: Command) {
        commands.add(command)
    }

    fun unRegisterCommand(command: Command) {
        commands.remove(command)
    }

    override fun onCommand(
        cs: CommandSender,
        cmd: org.bukkit.command.Command,
        s: String,
        args: Array<String>
    ): Boolean {
        try {
            if (args.size != 0) {
                for (command in commands) {
                    if (command.getAliases().contains(args[0]) && command.isEnabled()) {
                        if (command.isPlayer() && cs !is Player) {
                            // Must be a player
                            cs.sendMessage(
                                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().mustBeAPlayer.replace(
                                        "%prefix%",
                                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                                    )
                                )
                            )
                            return true
                        }
                        if ((cs.hasPermission(command.getPermission()) || command.getPermission()
                                .equalsIgnoreCase("") || command.getPermission()
                                .equalsIgnoreCase("iridiumskyblock.")) && command.isEnabled()
                        ) {
                            command.execute(cs, args)
                        } else {
                            // No permission
                            cs.sendMessage(
                                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().noPermission.replace(
                                        "%prefix%",
                                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                                    )
                                )
                            )
                        }
                        return true
                    }
                }
            } else {
                if (cs is Player) {
                    val p = cs
                    val u = _root_ide_package_.net.arsentic.arsenticskyblock.User.getUser(p)
                    if (u.island != null) {
                        if (u.island.getSchematic() == null) {
                            if (_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getSchematics().schematics.size() === 1) {
                                for (schematic in _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getSchematics().schematics) {
                                    u.island.setSchematic(schematic.name)
                                }
                            } else {
                                p.openInventory(u.island.getSchematicSelectGUI().getInventory())
                                return true
                            }
                        }
                        if (_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().islandMenu) {
                            p.openInventory(u.island.getIslandMenuGUI().getInventory())
                        } else {
                            u.island.teleportHome(p)
                        }
                    } else {
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getIslandManager().createIsland(p)
                    }
                    return true
                }
            }
            cs.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().unknownCommand.replace(
                        "%prefix%",
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                    )
                )
            )
        } catch (e: Exception) {
            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getInstance().sendErrorMessage(e)
        }
        return true
    }

    override fun onTabComplete(
        cs: CommandSender,
        cmd: org.bukkit.command.Command,
        s: String,
        args: Array<String>
    ): List<String>? {
        try {
            if (args.size == 1) {
                val result = ArrayList<String>()
                for (command in commands) {
                    for (alias in command.getAliases()) {
                        if (alias.toLowerCase()
                                .startsWith(args[0].toLowerCase()) && command.isEnabled() && (cs.hasPermission(command.getPermission()) || command.getPermission()
                                .equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("iridiumskyblock."))
                        ) {
                            result.add(alias)
                        }
                    }
                }
                return result
            }
            for (command in commands) {
                if (command.getAliases()
                        .contains(args[0]) && command.isEnabled() && (cs.hasPermission(command.getPermission()) || command.getPermission()
                        .equalsIgnoreCase("") || command.getPermission().equalsIgnoreCase("iridiumskyblock."))
                ) {
                    return command.TabComplete(cs, cmd, s, args)
                }
            }
        } catch (e: Exception) {
            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getInstance().sendErrorMessage(e)
        }
        return null
    }

    init {
        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getInstance().getCommand(command).setExecutor(this)
        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getInstance().getCommand(command).setTabCompleter(this)
    }
}