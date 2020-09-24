package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import org.bukkit.command.CommandSender

class ReloadCommand(plugin: ArsenticSkyblock) : Command(plugin, listOf("reload"), "Reload your configurations", "iridiumskyblock.reload", false) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getInstance().loadConfigs()
        sender.sendMessage(
            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().reloaded.replace(
                    "%prefix%",
                    _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                )
            )
        )
    }

    override fun admin(sender: CommandSender, args: Array<String>, island: Island) {
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