package net.arsentic.arsenticskyblock.util

import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object FileUtils {
    /**
     * Creates a file on disk from a file located in the jar
     *
     * @param fileName The name of the file to create
     */
    @JvmStatic
    fun createFile(plugin: Plugin, fileName: String) {
        val file = File(plugin.dataFolder, fileName)

        if (!file.exists()) {
            plugin.getResource(fileName).use { inStream ->
                if (inStream == null) {
                    file.createNewFile()
                    return
                }

                Files.copy(inStream, Paths.get(file.absolutePath))
            }
        }
    }

    @JvmStatic
    fun createMenuFile(plugin: Plugin, fileName: String) {
        val dir = File(plugin.dataFolder, "menus")
        val file = File(dir, "$fileName.yml")

        if (!dir.exists())
            dir.mkdirs()

        if (!file.exists()) {
            plugin.getResource("menus/${fileName}.yml").use { inputStream ->
                if (inputStream == null) {
                    file.createNewFile()
                    return
                }

                Files.copy(inputStream, Paths.get(file.absolutePath))
            }
        }
    }

    @JvmStatic
    fun createDirFile(plugin: Plugin, directory: String, fileName: String) {
        val dir = File(plugin.dataFolder, directory)
        val file = File(dir, fileName)

        if (!dir.exists())
            dir.mkdirs()

        if (!file.exists()) {
            plugin.getResource("$directory/$fileName").use { inputStream ->
                if (inputStream == null) {
                    if (!file.parentFile.exists())
                        file.mkdirs()

                    file.createNewFile()
                    return
                }

                Files.copy(inputStream, Paths.get(file.absolutePath))
            }
        }
    }

    @JvmStatic
    fun createFile(plugin: Plugin, file: File) {
        if (!file.exists()) {
            try {
                plugin.getResource(file.name).use { inStream ->
                    if (inStream == null) {
                        file.createNewFile()
                        return
                    }

                    Files.copy(inStream, Paths.get(file.absolutePath))
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun createFolder(plugin: Plugin, folderName: String) {
        val file = File(plugin.dataFolder, folderName)

        if (!file.exists()) {
            file.mkdir()
        }
    }
}
