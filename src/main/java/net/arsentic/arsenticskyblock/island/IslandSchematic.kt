package net.arsentic.arsenticskyblock.island

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.session.ClipboardHolder
import net.arsentic.arsenticskyblock.ArsenticSkyblock
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import java.io.File
import java.io.FileInputStream

/**
 * @author Esophose
 *
 * I can't code this shit :)
 */
class IslandSchematic(val name: String, private val file: File, val displayName: String, val icon: Material, val lore: List<String>) {

    private val clipboardFormat = ClipboardFormats.findByFile(this.file) ?: error("Not a valid schematic: ${this.name}")

    fun paste(plugin: ArsenticSkyblock, location: Location) {
        val clipboard: Clipboard
        this.clipboardFormat.getReader(FileInputStream(this.file)).use { clipboard = it.read() }


        val task = Runnable {
            // FastAsyncWorldEdit isn't updated to include the non-deprecated version yet
            @Suppress("DEPRECATION")
            WorldEdit.getInstance().editSessionFactory.getEditSession(BukkitAdapter.adapt(location.world), -1).use {
                val operation = ClipboardHolder(clipboard)
                    .createPaste(it)
                    .to(BukkitAdapter.asBlockVector(location))
                    .copyEntities(true)
                    .ignoreAirBlocks(true)
                    .build()

                Operations.complete(operation)
            }
        }

        val pluginManager = Bukkit.getPluginManager()
        if (pluginManager.isPluginEnabled("FastAsyncWorldEdit") || pluginManager.isPluginEnabled("AsyncWorldEdit")) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin,  task)
        } else {
            task.run()
        }
    }

}