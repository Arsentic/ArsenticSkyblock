package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ShopCommand(plugin: ArsenticSkyblock) : Command(plugin, listOf("shop"), "Access the Skyblock Shop", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val p = sender as Player
        p.openInventory(_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getShopGUI().getInventory())
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