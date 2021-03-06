package net.arsentic.arsenticskyblock.library

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.event.Listener

/**
 * @author Oribuin
 */
abstract class ArsenticCommand(val plugin: ArsenticPlugin, private val name: String) : TabExecutor, Listener {
    // Define prefix
    var prefix = "&8[&b&lArsentic&8] &7➜"

    fun register() {
        val cmd = Bukkit.getPluginCommand(name)
        if (cmd != null) {
            cmd.setExecutor(this)
            cmd.tabCompleter = this
        }

        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    /**
     * Execute the plugin's command
     *
     * @param sender The person sending the command
     * @param args   The arguments provided in the command.
     */
    abstract fun executeCommand(sender: CommandSender, args: Array<String>)

    /**
     * The tab complete for the command executed.
     *
     * @param sender The person typing the command.
     * @param args   The arguments provided by sender
     * @return List<String>?
     * */
    abstract fun tabComplete(sender: CommandSender, args: Array<String>): List<String>?

    // Execute the command.
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        executeCommand(sender, args)
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String>? {
        return tabComplete(sender, args)
    }
}