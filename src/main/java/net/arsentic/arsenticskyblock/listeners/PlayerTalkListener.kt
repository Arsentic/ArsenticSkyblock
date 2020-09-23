package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.data.User
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.lang.String

class PlayerTalkListener : Listener {
    @EventHandler(ignoreCancelled = true)
    fun onPlayerTalk(event: AsyncPlayerChatEvent) {
        try {
            val player = event.player
            val user: User = User.Companion.getUser(player)
            if (user.warp != null) {
                if (user.warp.getPassword().equals(event.message)) {
                    Bukkit.getScheduler().runTask(getInstance(), Runnable { player.teleport(user.warp.getLocation()) })
                    player.sendMessage(
                        color(
                            getMessages().teleporting
                                .replace("%prefix%", getConfiguration().prefix)
                        )
                    )
                } else {
                    player.sendMessage(
                        color(
                            getMessages().wrongPassword
                                .replace("%prefix%", getConfiguration().prefix)
                        )
                    )
                    user.warp = null
                }
                event.isCancelled = true
            }
            val island: Island? = user.island
            var format = event.format
            if (format.contains(getConfiguration().chatRankPlaceholder)) {
                if (island == null) {
                    format = format.replace(getConfiguration().chatRankPlaceholder, "")
                } else {
                    format = format.replace(getConfiguration().chatRankPlaceholder, Utils.getIslandRank(island).toString() + "")
                }
            }
            if (format.contains(getConfiguration().chatNAMEPlaceholder)) {
                if (island == null) {
                    format = format.replace(getConfiguration().chatNAMEPlaceholder, "")
                } else {
                    format = format.replace(getConfiguration().chatNAMEPlaceholder, island.getName())
                }
            }
            if (format.contains(getConfiguration().chatValuePlaceholder)) {
                if (island == null) {
                    format = format.replace(getConfiguration().chatValuePlaceholder, "")
                } else {
                    format = format.replace(getConfiguration().chatValuePlaceholder, island.getValue().toString() + "")
                }
            }
            if (format.contains(getConfiguration().chatLevelPlaceholder)) {
                if (island == null) {
                    format = format.replace(getConfiguration().chatLevelPlaceholder, "")
                } else {
                    format = format.replace(getConfiguration().chatLevelPlaceholder, String.format("%.2f", island.getValue()))
                }
            }
            if (island != null && user.islandChat) {
                for (member in island.getMembers()) {
                    val islandPlayer: Player = Bukkit.getPlayer(User.Companion.getUser(member).name!!) ?: continue
                    islandPlayer.sendMessage(
                        color(getMessages().chatFormat)
                            .replace(getConfiguration().chatValuePlaceholder, island.getValue().toString() + "")
                            .replace(getConfiguration().chatNAMEPlaceholder, island.getName())
                            .replace(getConfiguration().chatLevelPlaceholder, String.format("%.2f", island.getValue()))
                            .replace(getConfiguration().chatRankPlaceholder, Utils.getIslandRank(island).toString() + "")
                            .replace("%player%", player.name)
                            .replace("%message%", event.message)
                    )
                }
                event.isCancelled = true
            }
            event.format = Utils.color(format)
        } catch (e: Exception) {
            getInstance().sendErrorMessage(e)
        }
    }
}