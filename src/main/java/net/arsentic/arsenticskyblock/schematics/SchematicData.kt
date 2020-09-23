package net.arsentic.arsenticskyblock.schematics

import org.jnbt.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class SchematicData {
    enum class SchematicVersion {
        v1_13, v_1_8
    }

    @Getter
    private val width: Short

    @Getter
    private val length: Short

    @Getter
    private val height: Short

    @Getter
    private var tileEntities: List<Tag>? = null

    @Getter
    private var blocks: ByteArray

    @Getter
    private var data: ByteArray

    @Getter
    private var blockdata: ByteArray

    @Getter
    private var palette: Map<String, Tag>? = null

    @Getter
    private val file: File

    @Getter
    private var version: Int? = null

    @Getter
    private val schematicVersion: SchematicVersion

    constructor(file: File, width: Short, length: Short, height: Short, tileEntities: List<Tag>?, blocks: ByteArray, data: ByteArray, entities: List<Tag?>?) {
        this.blocks = blocks
        this.data = data
        this.width = width
        this.length = length
        this.height = height
        this.tileEntities = tileEntities
        schematicVersion = SchematicVersion.v_1_8
        this.file = file
    }

    constructor(file: File, width: Short, length: Short, height: Short, blockdata: ByteArray, palette: Map<String, Tag>?, version: Int) {
        this.width = width
        this.length = length
        this.height = height
        this.palette = palette
        this.blockdata = blockdata
        schematicVersion = SchematicVersion.v1_13
        this.version = version
        this.file = file
    }

    constructor(file: File, width: Short, length: Short, height: Short, tileEntities: List<Tag>?, blockdata: ByteArray, palette: Map<String, Tag>?, version: Int) {
        this.width = width
        this.length = length
        this.height = height
        this.palette = palette
        this.blockdata = blockdata
        schematicVersion = SchematicVersion.v1_13
        this.tileEntities = tileEntities
        this.version = version
        this.file = file
    }

    companion object {
        @Throws(IOException::class)
        fun loadSchematic(file: File): SchematicData {
            val stream = FileInputStream(file)
            val nbtStream = NBTInputStream(stream)
            val schematicTag = nbtStream.readTag() as CompoundTag
            stream.close()
            nbtStream.close()
            val schematic = schematicTag.value
            val width = getChildTag(schematic, "Width", ShortTag::class.java).value
            val length = getChildTag(schematic, "Length", ShortTag::class.java).value
            val height = getChildTag(schematic, "Height", ShortTag::class.java).value
            return if (!schematic.containsKey("Blocks")) {
                // 1.13 Schematic
                val version = getChildTag(schematic, "Version", IntTag::class.java).value
                val palette = getChildTag(schematic, "Palette", CompoundTag::class.java).value
                val blockdata = getChildTag(schematic, "BlockData", ByteArrayTag::class.java).value
                if (version == 1) {
                    val TileEntities = getChildTag(schematic, "TileEntities", ListTag::class.java).value
                    SchematicData(file, width, length, height, TileEntities, blockdata, palette, version)
                } else if (version == 2) {
                    val BlockEntities = getChildTag(schematic, "BlockEntities", ListTag::class.java).value
                    SchematicData(file, width, length, height, BlockEntities, blockdata, palette, version)
                } else {
                    SchematicData(file, width, length, height, blockdata, palette, version)
                }
            } else {
                val TileEntities = getChildTag(schematic, "TileEntities", ListTag::class.java).value
                val materials = getChildTag(schematic, "Materials", StringTag::class.java).value
                require(materials == "Alpha") { "Schematic file is not an Alpha schematic" }
                val blocks = getChildTag(schematic, "Blocks", ByteArrayTag::class.java).value
                val blockData = getChildTag(schematic, "Data", ByteArrayTag::class.java).value
                val entities = getChildTag(schematic, "Entities", ListTag::class.java).value
                SchematicData(file, width, length, height, TileEntities, blocks, blockData, entities)
            }
        }

        @Throws(IllegalArgumentException::class)
        fun <T : Tag?> getChildTag(items: Map<String?, Tag?>, key: String, expected: Class<T>): T {
            require(items.containsKey(key)) { "Schematic file is missing a \"$key\" tag" }
            val tag = items[key]
            require(expected.isInstance(tag)) { key + " tag is not of tag type " + expected.name }
            return expected.cast(tag)
        }
    }
}