package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.command.CommandSender

class ReloadCommand(plugin: IridiumSkyblock): Command(plugin, listOf("reload"), "Reload your configurations", "iridiumskyblock.reload", false) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getInstance().loadConfigs()
        sender.sendMessage(
            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getMessages().reloaded.replace(
                    "%prefix%",
                    _root_ide_package_.net.arsentic.arsenticskyblock.IridiumSkyblock.getConfiguration().prefix
                )
            )
        )
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