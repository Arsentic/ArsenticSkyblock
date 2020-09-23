package net.arsentic.arsenticskyblock.schematics

import org.bukkit.Location
import java.io.File

interface WorldEdit {
    fun version(): Int
    fun paste(file: File, location: Location, island: Island)
}