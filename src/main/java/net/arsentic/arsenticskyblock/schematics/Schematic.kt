package net.arsentic.arsenticskyblock.schematics

import com.google.gson.JsonParser
import net.arsentic.arsenticskyblock.IridiumSkyblock
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.block.Sign
import org.bukkit.inventory.ItemStack
import org.jnbt.*
import java.io.File
import java.io.IOException
import java.util.*

class Schematic : WorldEdit {
    var schematicData = HashMap<String, SchematicData?>()
    override fun version(): Int {
        return 0
    }

    override fun paste(file: File, location: Location, island: Island) {
        val schematicData = getSchematicData(file)
        val length: Short = schematicData.getLength()
        val width: Short = schematicData.getWidth()
        val height: Short = schematicData.getHeight()
        location.subtract(width / 2.00, height / 2.00, length / 2.00) // Centers the schematic
        if (schematicData.getSchematicVersion() === SchematicVersion.v_1_8) {
            val blocks: ByteArray = schematicData.getBlocks()
            val blockData: ByteArray = schematicData.getData()

            //LoadBlocks
            for (x in 0 until width) {
                for (y in 0 until height) {
                    for (z in 0 until length) {
                        val index = y * width * length + z * width + x
                        val block = Location(location.world, x + location.x, y + location.y, z + location.z).block
                        IridiumSkyblock.Companion.nms.setBlockFast(block, blocks[index], blockData[index])
                    }
                }
            }
            //Tile Entities
            if (schematicData.getTileEntities() != null) {
                for (tag in schematicData.getTileEntities()) {
                    if (tag !is CompoundTag) continue
                    val t = tag as CompoundTag
                    val tags = t.value
                    val x: Int = SchematicData.Companion.getChildTag<IntTag>(tags, "x", IntTag::class.java).getValue()
                    val y: Int = SchematicData.Companion.getChildTag<IntTag>(tags, "y", IntTag::class.java).getValue()
                    val z: Int = SchematicData.Companion.getChildTag<IntTag>(tags, "z", IntTag::class.java).getValue()
                    val block = Location(location.world, x + location.x, y + location.y, z + location.z).block
                    val id: String = SchematicData.Companion.getChildTag<StringTag>(tags, "id", StringTag::class.java).getValue().toLowerCase().replace("minecraft:", "")
                    if (id.equals("chest", ignoreCase = true)) {
                        val items: List<Tag> = SchematicData.Companion.getChildTag<ListTag>(tags, "Items", ListTag::class.java).getValue()
                        if (block.state is Chest) {
                            val chest = block.state as Chest
                            for (item in items) {
                                if (item !is CompoundTag) continue
                                val itemtag = item.value
                                val slot: Byte = SchematicData.Companion.getChildTag<ByteTag>(itemtag, "Slot", ByteTag::class.java).getValue()
                                val name: String = SchematicData.Companion.getChildTag<StringTag>(itemtag, "id", StringTag::class.java).getValue().toLowerCase().replace("minecraft:", "")
                                val amount: Byte = SchematicData.Companion.getChildTag<ByteTag>(itemtag, "Count", ByteTag::class.java).getValue()
                                val damage: Short = SchematicData.Companion.getChildTag<ShortTag>(itemtag, "Damage", ShortTag::class.java).getValue()
                                val material: Material = Material.requestOldMaterial(name.toUpperCase(), damage.toByte())
                                if (material != null) {
                                    val itemStack: ItemStack = material.parseItem(true)
                                    if (itemStack != null) {
                                        itemStack.amount = amount.toInt()
                                        chest.blockInventory.setItem(slot.toInt(), itemStack)
                                    }
                                }
                            }
                        }
                    } else if (id.equals("sign", ignoreCase = true)) {
                        if (block.state is Sign) {
                            val sign = block.state as Sign
                            val parser = JsonParser()
                            var line1 = if (parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text1", StringTag::class.java).getValue()).getAsString().isEmpty()) "" else parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text1", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", getUser(island.getOwner()).name)
                            var line2 = if (parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text2", StringTag::class.java).getValue()).getAsString().isEmpty()) "" else parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text2", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", getUser(island.getOwner()).name)
                            var line3 = if (parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text3", StringTag::class.java).getValue()).getAsString().isEmpty()) "" else parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text3", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", getUser(island.getOwner()).name)
                            var line4 = if (parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text4", StringTag::class.java).getValue()).getAsString().isEmpty()) "" else parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text4", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", getUser(island.getOwner()).name)
                            if (!parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text1", StringTag::class.java).getValue()).getAsString().isEmpty() && parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text1", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color")) line1 = ChatColor.valueOf(parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text1", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()).toString() + line1
                            if (!parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text2", StringTag::class.java).getValue()).getAsString().isEmpty() && parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text2", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color")) line2 = ChatColor.valueOf(parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text2", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()).toString() + line2
                            if (!parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text3", StringTag::class.java).getValue()).getAsString().isEmpty() && parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text3", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color")) line3 = ChatColor.valueOf(parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text3", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()).toString() + line3
                            if (!parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text4", StringTag::class.java).getValue()).getAsString().isEmpty() && parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text4", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color")) line4 = ChatColor.valueOf(parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text4", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()).toString() + line4
                            sign.setLine(0, line1)
                            sign.setLine(1, line2)
                            sign.setLine(2, line3)
                            sign.setLine(3, line4)
                            sign.update(true)
                        }
                    }
                }
            }
        } else {
            //LoadBlocks
            if (Material.ISFLAT) {
                try {
                    for (x in 0 until width) {
                        for (y in 0 until height) {
                            for (z in 0 until length) {
                                val index = y * width * length + z * width + x
                                val block = Location(location.world, x + location.x, y + location.y, z + location.z).block
                                for (s in schematicData.getPalette().keySet()) {
                                    val i: Int = SchematicData.Companion.getChildTag<T>(schematicData.getPalette(), s, IntTag::class.java).getValue()
                                    if (schematicData.getBlockdata().get(index) === i) {
                                        block.setBlockData(Bukkit.createBlockData(s), false)
                                    }
                                }
                            }
                        }
                    }
                    if (schematicData.getVersion() === 2) {
                        //Tile Entities
                        if (schematicData.getTileEntities() != null) {
                            for (tag in schematicData.getTileEntities()) {
                                if (tag !is CompoundTag) continue
                                val t = tag as CompoundTag
                                val tags = t.value
                                val pos: IntArray = SchematicData.Companion.getChildTag<IntArrayTag>(tags, "Pos", IntArrayTag::class.java).getValue()
                                val x = pos[0]
                                val y = pos[1]
                                val z = pos[2]
                                val block = Location(location.world, x + location.x, y + location.y, z + location.z).block
                                val id: String = SchematicData.Companion.getChildTag<StringTag>(tags, "Id", StringTag::class.java).getValue().toLowerCase().replace("minecraft:", "")
                                if (id.equals("chest", ignoreCase = true)) {
                                    val items: List<Tag> = SchematicData.Companion.getChildTag<ListTag>(tags, "Items", ListTag::class.java).getValue()
                                    if (block.state is Chest) {
                                        val chest = block.state as Chest
                                        for (item in items) {
                                            if (item !is CompoundTag) continue
                                            val itemtag = item.value
                                            val slot: Byte = SchematicData.Companion.getChildTag<ByteTag>(itemtag, "Slot", ByteTag::class.java).getValue()
                                            val name: String = SchematicData.Companion.getChildTag<StringTag>(itemtag, "id", StringTag::class.java).getValue().toLowerCase().replace("minecraft:", "")
                                            val amount: Byte = SchematicData.Companion.getChildTag<ByteTag>(itemtag, "Count", ByteTag::class.java).getValue()
                                            val material: Material = Material.requestOldMaterial(name.toUpperCase(), (-1).toByte())
                                            if (material != null) {
                                                val itemStack: ItemStack = material.parseItem(true)
                                                if (itemStack != null) {
                                                    itemStack.amount = amount.toInt()
                                                    chest.blockInventory.setItem(slot.toInt(), itemStack)
                                                }
                                            }
                                        }
                                    }
                                } else if (id.equals("sign", ignoreCase = true)) {
                                    if (block.state is Sign) {
                                        val sign = block.state as Sign
                                        val parser = JsonParser()
                                        var line1: String = parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text1", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", getUser(island.getOwner()).name)
                                        var line2: String = parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text2", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", getUser(island.getOwner()).name)
                                        var line3: String = parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text3", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", getUser(island.getOwner()).name)
                                        var line4: String = parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text4", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", getUser(island.getOwner()).name)
                                        if (parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text1", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color")) line1 = ChatColor.valueOf(parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text1", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()).toString() + line1
                                        if (parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text2", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color")) line2 = ChatColor.valueOf(parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text2", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()).toString() + line2
                                        if (parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text3", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color")) line3 = ChatColor.valueOf(parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text3", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()).toString() + line3
                                        if (parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text4", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color")) line4 = ChatColor.valueOf(parser.parse(SchematicData.Companion.getChildTag<StringTag>(tags, "Text4", StringTag::class.java).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()).toString() + line4
                                        sign.setLine(0, line1)
                                        sign.setLine(1, line2)
                                        sign.setLine(2, line3)
                                        sign.setLine(3, line4)
                                        sign.update(true)
                                    }
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    getInstance().sendErrorMessage(e)
                }
            } else {
                location.block.type = Material.STONE
                getInstance().getLogger().warning("Tried to load a 1.13+ schematic in a native minecraft version")
            }
        }
    }

    fun getSchematicData(file: File): SchematicData? {
        if (schematicData.containsKey(file.absolutePath)) {
            return schematicData[file.absolutePath]
        }
        var data: SchematicData? = null
        try {
            data = SchematicData.Companion.loadSchematic(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        schematicData[file.absolutePath] = data
        return data
    }
}