package net.arsentic.arsenticskyblock.util

import de.tr7zw.changeme.nbtapi.NBTItem
import net.arsentic.arsenticskyblock.IridiumSkyblock
import net.arsentic.arsenticskyblock.User
import net.arsentic.arsenticskyblock.configs.Inventories
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.CreatureSpawner
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

object Utils {
    fun makeItem(material: Material?, amount: Int, type: Int, name: String?, lore: List<String?>?, `object`: Any?): ItemStack {
        val item = ItemStack(material!!, amount, type.toShort())
        val m = item.itemMeta
        m!!.lore = lore
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name!!))
        item.itemMeta = m
        return item
    }

    fun makeItem(material: Material?, amount: Int, name: String?): ItemStack? {
        val item: ItemStack = material.parseItem(true) ?: return null
        item.amount = amount
        val m = item.itemMeta
        m!!.setDisplayName(ChatColor.translateAlternateColorCodes('&', name!!))
        item.itemMeta = m
        return item
    }

    fun makeItem(material: Material?, amount: Int, name: String?, lore: List<String?>?): ItemStack? {
        val item: ItemStack = material.parseItem(true) ?: return null
        item.amount = amount
        val m = item.itemMeta
        m!!.lore = color(lore)
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name!!))
        item.itemMeta = m
        return item
    }

    fun makeItem(item: Inventories.Item, placeholders: List<Placeholder>): ItemStack? {
        return try {
            val itemstack = makeItem(item.material, item.amount, processMultiplePlaceholders(item.title, placeholders), processMultiplePlaceholders(item.lore, placeholders))
            if (item.material === Material.PLAYER_HEAD && item.headOwner != null) {
                val m = itemstack!!.itemMeta as SkullMeta?
                m!!.owner = processMultiplePlaceholders(item.headOwner, placeholders)
                itemstack.itemMeta = m
            }
            itemstack
        } catch (e: Exception) {
            makeItem(Material.STONE, item.amount, processMultiplePlaceholders(item.title, placeholders), processMultiplePlaceholders(item.lore, placeholders))
        }
    }

    fun makeItem(item: Inventories.Item): ItemStack? {
        return try {
            val itemstack = makeItem(item.material, item.amount, item.title, item.lore)
            if (item.material === Material.PLAYER_HEAD && item.headOwner != null) {
                val m = itemstack!!.itemMeta as SkullMeta?
                m!!.owner = item.headOwner
                itemstack.itemMeta = m
            }
            itemstack
        } catch (e: Exception) {
            makeItem(Material.STONE, item.amount, item.title, item.lore)
        }
    }

    fun makeItem(item: Inventories.Item, island: Island): ItemStack? {
        return try {
            val itemstack = makeItem(item.material, item.amount, processIslandPlaceholders(item.title, island), color(processIslandPlaceholders(item.lore, island)))
            if (item.material === Material.PLAYER_HEAD && item.headOwner != null) {
                val m = itemstack!!.itemMeta as SkullMeta?
                m!!.owner = item.headOwner
                itemstack.itemMeta = m
            }
            itemstack
        } catch (e: Exception) {
            makeItem(Material.STONE, item.amount, processIslandPlaceholders(item.title, island), color(processIslandPlaceholders(item.lore, island)))
        }
    }

    fun makeItemHidden(item: Inventories.Item): ItemStack? {
        return try {
            val itemstack = makeItemHidden(item.material, item.amount, item.title, item.lore)
            if (item.material === Material.PLAYER_HEAD && item.headOwner != null) {
                val m = itemstack!!.itemMeta as SkullMeta?
                m!!.owner = item.headOwner
                itemstack.itemMeta = m
            }
            itemstack
        } catch (e: Exception) {
            makeItemHidden(Material.STONE, item.amount, item.title, item.lore)
        }
    }

    fun makeItemHidden(item: Inventories.Item?, island: Island): ItemStack {
        return makeItemHidden(item, getIslandPlaceholders(island))
    }

    fun makeItemHidden(item: Inventories.Item, placeholders: List<Placeholder>): ItemStack? {
        return try {
            val itemstack = makeItemHidden(item.material, item.amount, processMultiplePlaceholders(item.title, placeholders), color(processMultiplePlaceholders(item.lore, placeholders)))
            if (item.material === Material.PLAYER_HEAD && item.headOwner != null) {
                val m = itemstack!!.itemMeta as SkullMeta?
                m!!.owner = item.headOwner
                itemstack.itemMeta = m
            }
            itemstack
        } catch (e: Exception) {
            e.printStackTrace()
            makeItemHidden(Material.STONE, item.amount, processMultiplePlaceholders(item.title, placeholders), color(processMultiplePlaceholders(item.lore, placeholders)))
        }
    }

    fun makeItemHidden(material: Material?, amount: Int, name: String?, lore: List<String?>?): ItemStack? {
        val item: ItemStack = material.parseItem(true) ?: return null
        item.amount = amount
        val m = item.itemMeta
        m!!.lore = color(lore)
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE)
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name!!))
        item.itemMeta = m
        return item
    }

    fun color(string: String?): String {
        return ChatColor.translateAlternateColorCodes('&', string!!)
    }

    fun color(strings: List<String?>?): List<String?> {
        return strings!!.stream().map(Function<String?, String?> { obj: String? -> color() }).collect(Collectors.toList())
    }

    fun isBlockValuable(b: Block): Boolean {
        return getBlockValues().blockvalue.containsKey(Material.matchMaterial(b.type)) || b.state is CreatureSpawner || getConfiguration().limitedBlocks.containsKey(Material.matchMaterial(b.type))
    }

    fun isBlockValuable(material: Material?): Boolean {
        return getBlockValues().blockvalue.containsKey(material) || getConfiguration().limitedBlocks.containsKey(material)
    }

    val topIslands: List<Any?>
        get() {
            val islands: List<Island?> = ArrayList<Any?>(getIslandManager().islands.values())
            islands.sort(Comparator.comparingDouble<Island>(Island::getValue))
            Collections.reverse(islands)
            return islands
        }
    val islands: List<Any?>
        get() {
            val islands: List<Island?> = ArrayList<Any?>(getIslandManager().islands.values())
            islands.sort(Comparator.comparingInt<Island>(Island::getVotes))
            Collections.reverse(islands)
            return islands
        }

    fun isSafe(loc: Location?, island: Island): Boolean {
        if (loc == null) return false
        if (loc.y < 1) return false
        if (!island.isInIsland(loc)) return false
        if (!loc.block.type.name.endsWith("AIR")) return false
        return if (loc.clone().add(0.0, -1.0, 0.0).block.type.name.endsWith("AIR")) false else !loc.clone().add(0.0, -1.0, 0.0).block.isLiquid
    }

    fun getIslandRank(island: Island?): Int {
        var i = 1
        for (`is` in topIslands) {
            if (`is`.equals(island)) {
                return i
            }
            i++
        }
        return 0
    }

    fun getNewHome(island: Island, loc: Location?): Location? {
        var b: Block
        if (loc != null) {
            b = loc.world!!.getHighestBlockAt(loc)
            while (!Material.matchMaterial(b.type).name().endsWith("AIR")) {
                b = b.location.clone().add(0.0, 1.0, 0.0).block
            }
            if (isSafe(b.location, island)) {
                return b.location.add(0.5, 0.0, 0.5)
            }
        }
        for (X in island.getPos1().getX()..island.getPos2().getX()) {
            for (Z in island.getPos1().getZ()..island.getPos2().getZ()) {
                b = loc!!.world!!.getHighestBlockAt(X as Int, Z as Int)
                while (!Material.matchMaterial(b.type).name().endsWith("AIR")) {
                    b = b.location.clone().add(0.0, 1.0, 0.0).block
                }
                if (isSafe(b.location, island)) {
                    return b.location.add(0.5, 0.0, 0.5)
                }
            }
        }
        return null
    }

    fun getIslandPlaceholders(island: Island): MutableList<Placeholder> {
        return ArrayList(
            listOf( // Upgrades
                Placeholder("sizevaultcost", if (IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.containsKey(island.getSizeLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(island.getSizeLevel() + 1)!!.vaultCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("membervaultcost", if (IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.containsKey(island.getMemberLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.get(island.getMemberLevel() + 1)!!.vaultCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("warpvaultcost", if (IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.upgrades.containsKey(island.getWarpLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.upgrades.get(island.getWarpLevel() + 1)!!.vaultCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("oresvaultcost", if (IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.upgrades.containsKey(island.getOreLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.upgrades.get(island.getOreLevel() + 1)!!.vaultCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("sizecrystalscost", if (IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.containsKey(island.getSizeLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(island.getSizeLevel() + 1)!!.crystalsCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("membercrystalscost", if (IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.containsKey(island.getMemberLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.get(island.getMemberLevel() + 1)!!.crystalsCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("warpcrystalscost", if (IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.upgrades.containsKey(island.getWarpLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.upgrades.get(island.getWarpLevel() + 1)!!.crystalsCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("orescrystalscost", if (IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.upgrades.containsKey(island.getOreLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.upgrades.get(island.getOreLevel() + 1)!!.crystalsCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("sizecost", if (IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.containsKey(island.getSizeLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(island.getSizeLevel() + 1)!!.crystalsCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("membercost", if (IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.containsKey(island.getMemberLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.get(island.getMemberLevel() + 1)!!.crystalsCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("warpcost", if (IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.upgrades.containsKey(island.getWarpLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.upgrades.get(island.getWarpLevel() + 1)!!.crystalsCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("generatorcost", if (IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.upgrades.containsKey(island.getOreLevel() + 1)) IridiumSkyblock.Companion.getUpgrades()!!.oresUpgrade.upgrades.get(island.getOreLevel() + 1)!!.crystalsCost.toString() + "" else getMessages().maxlevelreached),
                Placeholder("sizeblocks", IridiumSkyblock.Companion.getUpgrades()!!.sizeUpgrade.upgrades.get(island.getSizeLevel())!!.size.toString() + ""),
                Placeholder("membercount", IridiumSkyblock.Companion.getUpgrades()!!.memberUpgrade.upgrades.get(island.getMemberLevel())!!.size.toString() + ""),
                Placeholder("warpcount", IridiumSkyblock.Companion.getUpgrades()!!.warpUpgrade.upgrades.get(island.getWarpLevel())!!.size.toString() + ""),
                Placeholder("sizelevel", island.getSizeLevel().toString() + ""),
                Placeholder("memberlevel", island.getMemberLevel().toString() + ""),
                Placeholder("warplevel", island.getWarpLevel().toString() + ""),
                Placeholder("oreslevel", island.getOreLevel().toString() + ""),
                Placeholder("generatorlevel", island.getOreLevel().toString() + ""),  // Boosters
                Placeholder("spawnerbooster", island.getSpawnerBooster().toString() + ""),
                Placeholder("farmingbooster", island.getFarmingBooster().toString() + ""),
                Placeholder("expbooster", island.getExpBooster().toString() + ""),
                Placeholder("flightbooster", island.getFlightBooster().toString() + ""),
                Placeholder("spawnerbooster_seconds", island.getSpawnerBooster() % 60.toString() + ""),
                Placeholder("farmingbooster_seconds", island.getFarmingBooster() % 60.toString() + ""),
                Placeholder("expbooster_seconds", island.getExpBooster() % 60.toString() + ""),
                Placeholder("flightbooster_seconds", island.getFlightBooster() % 60.toString() + ""),
                Placeholder("spawnerbooster_minutes", Math.floor(island.getSpawnerBooster() / 60.00) as Int.toString() + ""),
                Placeholder("farmingbooster_minutes", Math.floor(island.getFarmingBooster() / 60.00) as Int.toString() + ""),
                Placeholder("expbooster_minutes", Math.floor(island.getExpBooster() / 60.00) as Int.toString() + ""),
                Placeholder("flightbooster_minutes", Math.floor(island.getFlightBooster() / 60.00) as Int.toString() + ""),
                Placeholder("spawnerbooster_crystalcost", getBoosters().spawnerBooster.crystalsCost.toString() + ""),
                Placeholder("farmingbooster_crystalcost", getBoosters().farmingBooster.crystalsCost.toString() + ""),
                Placeholder("expbooster_crystalcost", getBoosters().experianceBooster.crystalsCost.toString() + ""),
                Placeholder("flightbooster_crystalcost", getBoosters().flightBooster.crystalsCost.toString() + ""),
                Placeholder("spawnerbooster_vaultcost", getBoosters().spawnerBooster.vaultCost.toString() + ""),
                Placeholder("farmingbooster_vaultcost", getBoosters().farmingBooster.vaultCost.toString() + ""),
                Placeholder("expbooster_vaultcost", getBoosters().experianceBooster.vaultCost.toString() + ""),
                Placeholder("flightbooster_vaultcost", getBoosters().flightBooster.vaultCost.toString() + ""),  //Bank
                Placeholder("experience", island.exp.toString() + ""),
                Placeholder("crystals", island.getCrystals().toString() + ""),
                Placeholder("money", island.money.toString() + ""),
                Placeholder("value", island.getValue().toString() + "")
            )
        )
    }

    fun processIslandPlaceholders(line: String?, island: Island): String {
        return processMultiplePlaceholders(line, getIslandPlaceholders(island))
    }

    fun processIslandPlaceholders(lines: List<String?>?, island: Island): List<String?> {
        val newlist: MutableList<String?> = ArrayList()
        for (string in lines!!) {
            newlist.add(processIslandPlaceholders(string, island))
        }
        return newlist
    }

    fun processMultiplePlaceholders(lines: List<String?>?, placeholders: List<Placeholder>): List<String?> {
        val newlist: MutableList<String?> = ArrayList()
        for (string in lines!!) {
            newlist.add(processMultiplePlaceholders(string, placeholders))
        }
        return newlist
    }

    fun processMultiplePlaceholders(line: String?, placeholders: List<Placeholder>): String {
        var line = line
        for (placeholder in placeholders) {
            line = placeholder.process(line)
        }
        return color(line)
    }

    fun pay(p: Player?, vault: Double, crystals: Int) {
        val u: User = User.Companion.getUser(p)
        if (u.island != null) {
            u.island.setCrystals(u.island.getCrystals() + crystals)
            if (Vault.Companion.econ == null) {
                u.island.money += vault
            } else {
                Vault.Companion.econ.depositPlayer(p, vault)
            }
        } else {
            if (Vault.Companion.econ == null) {
                getInstance().getLogger().warning("Vault plugin not found")
                return
            }
            Vault.Companion.econ.depositPlayer(p, vault)
        }
    }

    fun canBuy(p: Player?, vault: Double, crystals: Int): Boolean {
        val u: User = User.Companion.getUser(p)
        if (u.island != null) {
            if (Vault.Companion.econ != null) {
                if (Vault.Companion.econ.getBalance(p) >= vault && u.island.getCrystals() >= crystals) {
                    Vault.Companion.econ.withdrawPlayer(p, vault)
                    u.island.setCrystals(u.island.getCrystals() - crystals)
                    return true
                }
            }
            if (u.island.money >= vault && u.island.getCrystals() >= crystals) {
                u.island.money -= vault
                u.island.setCrystals(u.island.getCrystals() - crystals)
                return true
            }
        }
        if (Vault.Companion.econ != null) {
            if (Vault.Companion.econ.getBalance(p) >= vault && crystals == 0) {
                Vault.Companion.econ.withdrawPlayer(p, vault)
                return true
            }
        }
        return false
    }

    fun getExpAtLevel(level: Int): Int {
        if (level <= 15) {
            return 2 * level + 7
        } else if (level <= 30) {
            return 5 * level - 38
        }
        return 9 * level - 158
    }

    fun getTotalExperience(player: Player): Int {
        var exp = Math.round(getExpAtLevel(player.level) * player.exp)
        var currentLevel = player.level
        while (currentLevel > 0) {
            currentLevel--
            exp += getExpAtLevel(currentLevel)
        }
        if (exp < 0) {
            exp = Int.MAX_VALUE
        }
        return exp
    }

    fun setTotalExperience(player: Player, exp: Int) {
        require(exp >= 0) { "Experience is negative!" }
        player.exp = 0f
        player.level = 0
        player.totalExperience = 0
        var amount = exp
        while (amount > 0) {
            val expToLevel = getExpAtLevel(player.level)
            amount -= expToLevel
            if (amount >= 0) {
                // give until next level
                player.giveExp(expToLevel)
            } else {
                // give the rest
                amount += expToLevel
                player.giveExp(amount)
                amount = 0
            }
        }
    }

    fun getCrystals(amount: Int): ItemStack {
        val itemStack = makeItemHidden(getInventories().crystal, listOf(Placeholder("amount", amount.toString() + "")))!!
        val nbtItem = NBTItem(itemStack)
        nbtItem.setInteger("crystals", amount)
        return nbtItem.item
    }

    fun getCrystals(itemStack: ItemStack?): Int {
        if (itemStack == null || itemStack.type == Material.AIR) return 0
        val nbtItem = NBTItem(itemStack)
        return if (nbtItem.hasKey("crystals")) {
            nbtItem.getInteger("crystals")
        } else 0
    }

    class Placeholder(key: String, value: String?) {
        private val key: String
        private val value: String?
        fun process(line: String?): String {
            return line?.replace(key, value!!) ?: ""
        }

        init {
            this.key = "{$key}"
            this.value = value
        }
    }
}