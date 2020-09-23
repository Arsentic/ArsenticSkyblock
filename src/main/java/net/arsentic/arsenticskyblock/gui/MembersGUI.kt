package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.Role
import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.api.IslandDemoteEvent
import net.arsentic.arsenticskyblock.api.IslandPromoteEvent
import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class MembersGUI(island: Island?) : GUI(island, getInventories().membersGUISize, getInventories().membersGUITitle), Listener {
    var users: MutableMap<Int, User> = HashMap()
    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        users.clear()
        val island: Island? = island
        if (island != null) {
            var i = 0
            for (member in island.getMembers()) {
                val u: User = User.Companion.getUser(member)
                users[i] = u
                val head: ItemStack = makeItem(getInventories().islandmember, listOf(Utils.Placeholder("demote", if (u.getRole() == Role.Visitor) getMessages().Kick else getMessages().Demote), Utils.Placeholder("player", User.Companion.getUser(member).name), Utils.Placeholder("role", u.getRole().toString())))
                setItem(i, head)
                i++
            }
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            if (User.Companion.getUser(e.whoClicked as OfflinePlayer)!!.bypassing || island.equals(User.Companion.getUser(e.whoClicked as OfflinePlayer).getIsland())) {
                if (users.containsKey(e.slot)) {
                    val u = users[e.slot]
                    val user: User = User.Companion.getUser(e.whoClicked as Player)
                    if (e.click == ClickType.LEFT) {
                        if (user.island.getPermissions(user.role).demote) {
                            if (u!!.getRole().getRank() < user.role.getRank()) {
                                if (u.getRole() == Role.Member) {
                                    if (user.island.getPermissions(user.role).kickMembers) {
                                        user.island.removeUser(u)
                                        inventory.clear()
                                        val player = Bukkit.getPlayer(u.name!!)
                                        player?.sendMessage(color(getMessages().youHaveBeenKicked.replace("%prefix%", getConfiguration().prefix)))
                                    } else {
                                        e.whoClicked.sendMessage(color(getMessages().noPermission.replace("%prefix%", getConfiguration().prefix)))
                                    }
                                } else {
                                    val event = IslandDemoteEvent(u.island, u, user, Role.Companion.getViaRank(u.getRole().getRank() + 1)!!)
                                    Bukkit.getPluginManager().callEvent(event)
                                    if (!event.isCancelled()) {
                                        u.role = Role.Companion.getViaRank(u.getRole().getRank() - 1)
                                        for (member in u.island.getMembers()) {
                                            val p: Player = Bukkit.getPlayer(User.Companion.getUser(member).name!!)
                                            p?.sendMessage(color(getMessages().playerDemoted.replace("%rank%", u.getRole().toString()).replace("%player%", u.name).replace("%prefix%", getConfiguration().prefix)))
                                        }
                                    }
                                }
                            } else {
                                e.whoClicked.sendMessage(color(getMessages().cantDemoteUser.replace("%prefix%", getConfiguration().prefix)))
                            }
                        } else {
                            e.whoClicked.sendMessage(color(getMessages().noPermission.replace("%prefix%", getConfiguration().prefix)))
                        }
                    } else {
                        if (user.island.getPermissions(user.role).promote) {
                            if (u!!.getRole() != Role.Owner) {
                                if (u.getRole().getRank() < user.role.getRank()) {
                                    if (u.getRole().getRank() >= Role.CoOwner.getRank()) {
                                        e.whoClicked.openInventory(ConfirmationGUI(user.island, {
                                            val p = Bukkit.getOfflinePlayer(u.name!!)
                                            if (p != null) {
                                                u.island.setOwner(p)
                                            }
                                        }, getMessages().transferAction.replace("%player%", u.name)).inventory)
                                    } else {
                                        val event = IslandPromoteEvent(u.island, u, user, Role.Companion.getViaRank(u.getRole().getRank() + 1)!!)
                                        Bukkit.getPluginManager().callEvent(event)
                                        if (!event.isCancelled()) {
                                            u.role = Role.Companion.getViaRank(u.getRole().getRank() + 1)
                                            for (member in u.island.getMembers()) {
                                                val p: Player = Bukkit.getPlayer(User.Companion.getUser(member).name!!)
                                                p?.sendMessage(color(getMessages().playerPromoted.replace("%rank%", u.getRole().toString()).replace("%player%", u.name).replace("%prefix%", getConfiguration().prefix)))
                                            }
                                        }
                                    }
                                } else {
                                    e.whoClicked.sendMessage(color(getMessages().cantPromoteUser.replace("%prefix%", getConfiguration().prefix)))
                                }
                            } else {
                                e.whoClicked.sendMessage(color(getMessages().noPermission.replace("%prefix%", getConfiguration().prefix)))
                            }
                        } else {
                            e.whoClicked.sendMessage(color(getMessages().cantDemoteOwner.replace("%prefix%", getConfiguration().prefix)))
                        }
                    }
                }
            }
        }
    }

    init {
        getInstance().registerListeners(this)
    }
}