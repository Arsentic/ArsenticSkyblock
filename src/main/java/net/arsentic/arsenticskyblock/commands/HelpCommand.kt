package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HelpCommand(plugin: IridiumSkyblock): Command(plugin, listOf("help"), "Displays the plugin commands", "", true) {
    override fun execute(cs: CommandSender, args: Array<String>) {
        val p = cs as Player
        var page = 1
        if (args.size == 2) {
            if (!StringUtils.isNumeric(args[1])) {
                return
            }
            page = args[1].toInt()
        }
        val maxpage = Math.ceil(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommandManager().commands.size() / 18.00).toInt()
        var current = 0
        p.sendMessage(_root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().helpHeader))
        for (command in _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getCommandManager().commands) {
            if ((p.hasPermission(command.getPermission()) || command.getPermission()
                    .equalsIgnoreCase("") || command.getPermission()
                    .equalsIgnoreCase("iridiumskyblock.")) && command.isEnabled()
            ) {
                if (current >= (page - 1) * 18 && current < page * 18) p.sendMessage(
                    _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().helpMessage.replace(
                            "%command%",
                            command.getAliases().get(0)
                        ).replace("%description%", command.getDescription())
                    )
                )
                current++
            }
        }
        val components = TextComponent.fromLegacyText(
            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().helpfooter.replace(
                    "%maxpage%",
                    maxpage.toString() + ""
                ).replace("%page%", page.toString() + "")
            )
        )
        for (component in components) {
            if (ChatColor.stripColor(component.toLegacyText()).contains(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().nextPage)) {
                if (page < maxpage) {
                    component.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/is help " + (page + 1))
                    component.hoverEvent = HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        ComponentBuilder(
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().helpPageHoverMessage.replace(
                                "%page%",
                                "" + (page + 1)
                            )
                        ).create()
                    )
                }
            } else if (ChatColor.stripColor(component.toLegacyText())
                    .contains(_root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().previousPage)
            ) {
                if (page > 1) {
                    component.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/is help " + (page - 1))
                    component.hoverEvent = HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        ComponentBuilder(
                            _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().helpPageHoverMessage.replace(
                                "%page%",
                                "" + (page - 1)
                            )
                        ).create()
                    )
                }
            }
        }
        p.player!!.spigot().sendMessage(*components)
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