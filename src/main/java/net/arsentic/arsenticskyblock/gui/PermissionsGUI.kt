package net.arsentic.arsenticskyblock.gui

import net.arsentic.arsenticskyblock.*
import net.arsentic.arsenticskyblock.Role
import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.*

class PermissionsGUI : GUI, Listener {
    private var role: Role? = null
    private val permissions: MutableMap<Role, PermissionsGUI> = HashMap()

    constructor(island: Island?) : super(island, getInventories().permissionsGUISize, getInventories().permissionsGUITitle) {
        getInstance().registerListeners(this)
    }

    constructor(island: Island?, role: Role?) : super(island, 27, getInventories().permissionsGUITitle) {
        this.role = role
    }

    override fun addContent() {
        super.addContent()
        if (inventory.viewers.isEmpty()) return
        if (island != null) {
            if (role != null) {
                var i = 0
                try {
                    for (field in Permissions::class.java.getDeclaredFields()) {
                        val `object` = field[island.getPermissions(role)]
                        if (`object` is Boolean) {
                            if (`object`) {
                                setItem(i, makeItem(getInventories().islandPermissionAllow, listOf(Utils.Placeholder("permission", getMessages().permissions.getOrDefault(field.name, field.name)))))
                            } else {
                                setItem(i, makeItem(getInventories().islandPermissionDeny, listOf(Utils.Placeholder("permission", getMessages().permissions.getOrDefault(field.name, field.name)))))
                            }
                        }
                        i++
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                var i = 11
                for (role in Role.values()) {
                    permissions[role] = PermissionsGUI(island, role)
                    setItem(i, makeItem(getInventories().islandRoles, listOf(Utils.Placeholder("role", role.toString()))))
                    i++
                }
            }
        }
    }

    @EventHandler
    override fun onInventoryClick(e: InventoryClickEvent) {
        val p = e.whoClicked as Player
        val u: User = User.Companion.getUser(p)
        if (e.inventory == inventory) {
            e.isCancelled = true
            if (e.clickedInventory == null || e.clickedInventory != inventory) return
            var i = 11
            for (role in Role.values()) {
                if (e.slot == i) {
                    e.whoClicked.openInventory(permissions[role].getInventory())
                }
                i++
            }
        } else {
            for (role in permissions.keys) {
                val gui = permissions[role]
                if (e.inventory == gui.getInventory()) {
                    e.isCancelled = true
                    if (role.getRank() < u.role.getRank()) {
                        var i = 0
                        try {
                            for (field in Permissions::class.java.getDeclaredFields()) {
                                val `object` = field[island.getPermissions(role)]
                                if (i == e.slot) {
                                    field.isAccessible = true
                                    field.setBoolean(island.getPermissions(role), (!`object` as Boolean))
                                    addContent()
                                }
                                i++
                            }
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    } else {
                        e.whoClicked.sendMessage(color(getMessages().noPermission.replace("%prefix%", getConfiguration().prefix)))
                    }
                }
            }
        }
    }
}