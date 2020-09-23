package net.arsentic.arsenticskyblock.listeners

import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.manager.IslandManager
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.vehicle.VehicleDamageEvent
import java.util.*
import java.util.function.Supplier

class EntityDamageByEntityListener : Listener {
    @EventHandler
    fun onEntityDamageEvent(event: EntityDamageEvent) {
        val damagee = event.entity as? Player ?: return
        val user: User = User.Companion.getUser(damagee)
        val damageeLocation = damagee.location
        val islandManager: IslandManager = getIslandManager()
        val island: Island = islandManager.getIslandViaLocation(damageeLocation) ?: return
        if (event.cause == EntityDamageEvent.DamageCause.VOID) return

        //The user is visiting this island, so disable damage
        if (user.islandID != island.getId() && getConfiguration().disablePvPOnIslands) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        try {
            val damagee = event.entity
            val damageeLocation = damagee.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(damageeLocation) ?: return
            val damager = event.damager

            // Using suppliers to defer work if unnecessary
            // This includes seemingly innocuous downcast operations
            val damageePlayerSupplier = Supplier { damagee as Player }
            val damageeUserSupplier = Supplier<User?> { User.Companion.getUser(damageePlayerSupplier.get()) }
            val damageeIslandSupplier: Supplier<Island?> = Supplier { damageeUserSupplier.get().getIsland() }
            val arrowSupplier = Supplier { damager as Arrow }
            val projectileSourceSupplier = Supplier { arrowSupplier.get().shooter }
            val shooterSupplier = Supplier { projectileSourceSupplier.get() as Player? }
            val shootingUserSupplier = Supplier<User?> { User.Companion.getUser(Objects.requireNonNull(shooterSupplier.get())) }
            val damagingPlayerSupplier = Supplier { damager as Player }
            val damagingUserSupplier = Supplier<User?> { User.Companion.getUser(damagingPlayerSupplier.get()) }

            // Deals with two players pvping in IridiumSkyblock world
            if (getConfiguration().disablePvPOnIslands
                && damagee is Player
                && damager is Player
            ) {
                event.isCancelled = true
                return
            }

            // Deals with A player getting damaged by a bow fired from a player in IridiumSkyblock world
            if (getConfiguration().disablePvPOnIslands
                && damagee is Player
                && damager is Arrow
                && projectileSourceSupplier.get() is Player
            ) {
                event.isCancelled = true
                return
            }

            // Deals with a player attacking animals with bows that are not from their island
            if (damager is Arrow
                && damagee !is Player
                && projectileSourceSupplier.get() is Player
                && !island.getPermissions(shootingUserSupplier.get()).killMobs
            ) {
                event.isCancelled = true
                return
            }

            // Deals with a player attacking animals that are not from their island
            if (damager is Player
                && damagee !is Player
                && !island.getPermissions(damagingUserSupplier.get()).killMobs
            ) {
                event.isCancelled = true
                return
            }

            //Deals with a mob attacking a player that doesn't belong to the island (/is home traps?)
            if (getConfiguration().disablePvPOnIslands
                && damagee is Player
                && damager !is Player
            ) {
                if (damageeIslandSupplier.get() != null) {
                    if (!damageeIslandSupplier.get().isInIsland(damager.location)) {
                        event.isCancelled = true
                        return
                    }
                } else {
                    event.isCancelled = true
                    return
                }
            }

            // Deals with two allies pvping
            if (getConfiguration().disablePvPBetweenIslandMembers
                && damagee is Player
                && damager is Player
                && damageeIslandSupplier.get() != null && damageeIslandSupplier.get().equals(damagingUserSupplier.get().getIsland())
            ) {
                event.isCancelled = true
                return
            }

            // Deals with two allies pvping with bows
            if (getConfiguration().disablePvPBetweenIslandMembers
                && damagee is Player
                && damager is Arrow
                && projectileSourceSupplier.get() is Player
                && damageeIslandSupplier.get() != null && damageeIslandSupplier.get().equals(damagingUserSupplier.get().getIsland())
            ) {
                event.isCancelled = true
                return
            }
        } catch (ex: Exception) {
            getInstance().sendErrorMessage(ex)
        }
    }

    @EventHandler
    fun onVehicleDamage(event: VehicleDamageEvent) {
        try {
            val vehicle = event.vehicle
            val location = vehicle.location
            val islandManager: IslandManager = getIslandManager()
            val island: Island = islandManager.getIslandViaLocation(location) ?: return
            val attacker = event.attacker as? Player ?: return
            val attackerUser: User = User.Companion.getUser(attacker)
            if (!island.getPermissions(attackerUser).killMobs) event.isCancelled = true
        } catch (ex: Exception) {
            getInstance().sendErrorMessage(ex)
        }
    }
}