package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.data.User
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PermissionsCommand(plugin: IridiumSkyblock) : Command(plugin, listOf("permissions"), "Edit Island Permissions", "", true) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        val player = sender as Player
        val user = User.getUser(player)
        val island: Island? = user.island
        if (island != null) {
            player.openInventory(island.getPermissionsGUI().getInventory())
        }
    }

    override fun admin(sender: CommandSender, args: Array<String>, island: Island) {
        val player = sender as Player
        if (island != null) {
            player.openInventory(island.getPermissionsGUI().getInventory())
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