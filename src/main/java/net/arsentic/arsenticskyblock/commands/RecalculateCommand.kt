package net.arsentic.arsenticskyblock.commands

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.util.*

class RecalculateCommand(plugin: ArsenticSkyblock) : Command(plugin, listOf("recalc", "recalculate"), "Recalculate all island values", "recalculate", false) {
    private var id = 0
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (id != 0) {
            sender.sendMessage(
                _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                    ArsenticSkyblock.getMessages().calculationAlreadyInProcess.replace(
                        "%prefix%",
                        _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                    )
                )
            )
            return
        }
        val manager: _root_ide_package_.net.arsentic.arsenticskyblock.manager.IslandManager = _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getIslandManager()
        val interval = 5
        val total = manager.islands.size
        val totalSecconds = total * (interval / 20.00)
        val minutes = Math.floor(totalSecconds / 60.00).toInt()
        val seconds: Double = (totalSecconds - minutes * 60) as Int.toDouble()
        sender.sendMessage("$total $totalSecconds $minutes $seconds")
        sender.sendMessage(
            _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().calculatingIslands.replace(
                    "%amount%",
                    total.toString() + ""
                ).replace("%seconds%", seconds.toString() + "").replace("%minutes%", minutes.toString() + "")
                    .replace("%prefix%", _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix)
            )
        )
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(_root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getInstance(), object : Runnable {
            var islands: ListIterator<Int> = ArrayList(manager.islands.keys).listIterator()
            override fun run() {
                if (islands.hasNext()) {
                    val id = islands.next()
                    val island: Island? = manager.getIslandViaId(id)
                    if (island != null) {
                        island.initBlocks()
                    }
                } else {
                    sender.sendMessage(
                        _root_ide_package_.net.arsentic.arsenticskyblock.util.Utils.color(
                            _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getMessages().calculatingFinished.replace(
                                "%prefix%",
                                _root_ide_package_.net.arsentic.arsenticskyblock.ArsenticSkyblock.getConfiguration().prefix
                            )
                        )
                    )
                    Bukkit.getScheduler().cancelTask(id)
                    id = 0
                }
            }
        }, 0, interval.toLong())
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