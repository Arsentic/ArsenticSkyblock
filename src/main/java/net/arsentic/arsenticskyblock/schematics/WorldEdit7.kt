package net.arsentic.arsenticskyblock.schematics

import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.Location
import java.io.File
import java.io.FileInputStream

class WorldEdit7 : WorldEdit {
    override fun version(): Int {
        return 7
    }

    override fun paste(file: File, location: Location, island: Island) {
        try {
            val format = ClipboardFormats.findByFile(file)
            val reader = format!!.getReader(FileInputStream(file))
            val clipboard = reader.read()
            com.sk89q.worldedit.WorldEdit.getInstance().editSessionFactory.getEditSession(BukkitWorld(location.world), -1).use { editSession ->
                val operation = ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.x, location.y, location.z))
                    .copyEntities(true)
                    .ignoreAirBlocks(true)
                    .build()
                Operations.complete(operation)
            }
        } catch (e: Exception) {
            getInstance().getLogger().warning("Failed to paste schematic using worldedit")
            IridiumSkyblock.Companion.schematic!!.paste(file, location, island)
        }
    }
}