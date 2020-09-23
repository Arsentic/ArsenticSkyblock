package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TopCommand(plugin: ArsenticSkyblock) : Command(plugin, listOf("top"), "View the top islands", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val p = sender as Player
        p.openInventory(_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.topGUI.inventory)
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