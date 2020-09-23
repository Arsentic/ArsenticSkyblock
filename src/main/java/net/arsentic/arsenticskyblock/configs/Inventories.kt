package net.arsentic.arsenticskyblock.configs

import net.arsentic.arsenticskyblock.ArsenticSkyblock
import org.bukkit.Material
import java.util.*

class Inventories(plugin: ArsenticSkyblock) : Config(plugin) {
    var upgradeGUITitle = "&7Upgrade"
    var boosterGUITitle = "&7Booster"
    var confirmationGUITitle = "&7Are you sure?"
    var missionsGUITitle = "&7Missions"
    var membersGUITitle = "&7Members"
    var coopGUITitle = "&7Co-op Islands"
    var islandMenuGUITitle = "&7Menu"
    var warpGUITitle = "&7Warps"
    var topGUITitle = "&7Top Islands"
    var borderColorGUITitle = "&7Border Color"
    var permissionsGUITitle = "&7Permissions"
    var schematicSelectGUITitle = "&7Select an Island"
    var bankGUITitle = "&7Island Bank"
    var visitGUITitle = "&7Visit an Island"
    var shopGUITitle = "&7Island Shop"
    var biomeGUITitle = "&7Island Biome"
    var upgradeGUISize = 27
    var boosterGUISize = 27
    var missionsGUISize = 27
    var membersGUISize = 27
    var coopGUISize = 27
    var islandMenuGUISize = 45
    var warpGUISize = 27
    var topGUISize = 27
    var borderColorGUISize = 27
    var permissionsGUISize = 27
    var schematicSelectGUISize = 27
    var bankGUISize = 27
    var visitGUISize = 54
    var shopGUISize = 54
    var biomeGUISize = 54

    //Boosters
    var spawner = Item(Material.SPAWNER, 1, "&b&lIncreased Mobs", listOf("&7Are your spawners too slow? Buy this", "&7booster and increase spawner rates x2.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b{spawnerbooster_minutes} minutes and {spawnerbooster_seconds}seconds", "&b&l * &7Booster Cost: &b{spawnerbooster_crystalcost} Crystals and \${spawnerbooster_vaultcost}", "", "&b&l[!] &bRight Click to Purchase this Booster."))
    var farming = Item(Material.WHEAT, 1, "&b&lIncreased Crops", listOf("&7Are your crops too slow? Buy this", "&7booster and increase crop growth rates x2.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b{farmingbooster_minutes} minutes and {farmingbooster_seconds}seconds", "&b&l * &7Booster Cost: &b{farmingbooster_crystalcost} Crystals and \${farmingbooster_vaultcost}", "", "&b&l[!] &bRight Click to Purchase this Booster."))
    var exp = Item(Material.EXPERIENCE_BOTTLE, 1, "&b&lIncreased Experience", listOf("&7Takes too long to get exp? Buy this", "&7booster and exp rates x2.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b{expbooster_minutes} minutes and {expbooster_seconds}seconds", "&b&l * &7Booster Cost: &b{expbooster_crystalcost} Crystals and \${expbooster_vaultcost}", "", "&b&l[!] &bRight Click to Purchase this Booster."))
    var flight = Item(Material.FEATHER, 1, "&b&lIncreased Flight", listOf("&7Tired of falling off your island? Buy this", "&7booster and allow all members to fly.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b{flightbooster_minutes} minutes and {flightbooster_seconds}seconds", "&b&l * &7Booster Cost: &b{flightbooster_crystalcost} Crystals and \${flightbooster_vaultcost}", "", "&b&l[!] &bRight Click to Purchase this Booster."))

    //Upgrade
    var size = Item(Material.GRASS_BLOCK, 1, "&b&lIsland Size", listOf("&7Need more room to expand? Buy this", "&7upgrade to increase your island size.", "", "&b&lInformation:", "&b&l * &7Current Level: &b{sizelevel}", "&b&l * &7Current Size: &b{sizeblocks}x{sizeblocks} Blocks", "&b&l * &7Upgrade Cost: &b{sizecrystalscost} Crystals and \${sizevaultcost}", "&b&lLevels:", "&b&l * &7Level 1: &b50x50 Blocks", "&b&l * &7Level 2: &b100x100 Blocks", "&b&l * &7Level 3: &b150x150 Blocks", "", "&b&l[!] &bLeft Click to Purchase this Upgrade"))
    var member = Item(Material.ARMOR_STAND, 1, "&b&lMember Count", listOf("&7Need more members? Buy this", "&7upgrade to increase your member count.", "", "&b&lInformation:", "&b&l * &7Current Level: &b{memberlevel}", "&b&l * &7Current Members: &b{membercount} Members", "&b&l * &7Upgrade Cost: &b{membercrystalscost} Crystals and \${membervaultcost}", "&b&lLevels:", "&b&l * &7Level 1: &b9 Members", "&b&l * &7Level 2: &b18 Members", "&b&l * &7Level 3: &b27 Members", "", "&b&l[!] &bLeft Click to Purchase this Upgrade"))
    var warp = Item(Material.END_PORTAL_FRAME, 1, "&b&lIsland Warps", listOf("&7Need more island warps? Buy this", "&7upgrade to increase your warp count.", "", "&b&lInformation:", "&b&l * &7Current Level: &b{warplevel}", "&b&l * &7Current Warps: &b{warpcount} Warps", "&b&l * &7Upgrade Cost: &b{warpcrystalscost} Crystals and \${warpvaultcost}", "&b&lLevels:", "&b&l * &7Level 1: &b2 Warps", "&b&l * &7Level 2: &b5 Warps", "&b&l * &7Level 3: &b9 Warps", "", "&b&l[!] &bLeft Click to Purchase this Upgrade"))
    var ores = Item(Material.DIAMOND_ORE, 1, "&b&lIsland Generator", listOf("&7Want to improve your generator? Buy this", "&7upgrade to increase your island generator.", "", "&b&lInformation:", "&b&l * &7Current Level: &b{oreslevel}", "&b&l * &7Upgrade Cost: &b{orescrystalscost} Crystals and \${oresvaultcost}", "", "&b&l[!] &bLeft Click to Purchase this Upgrade"))

    //Bank
    var experience = Item(Material.EXPERIENCE_BOTTLE, 11, 1, "&b&lIsland Experience", listOf("&7{experience} Experience", "&b&l[!] &bLeft click to withdraw", "&b&l[!] &bRight click to deposit"))
    var crystals = Item(Material.NETHER_STAR, 13, 1, "&b&lIsland Crystals", listOf("&7{crystals} Crystals", "&b&l[!] &bLeft click to withdraw", "&b&l[!] &bRight click to deposit"))
    var money = Item(Material.PAPER, 15, 1, "&b&lIsland Money", listOf("&7\${money}", "&b&l[!] &bLeft click to withdraw", "&b&l[!] &bRight click to deposit"))
    var crystal = Item(Material.NETHER_STAR, 1, "&b*** &b&lIsland Crystal &b***", listOf("", "&b{amount} Island Crystals", "&b&l[!] &bRight-Click to Redeem"))
    var background = Item(Material.BLACK_STAINED_GLASS_PANE, 1, " ", ArrayList())
    var nextPage = Item(Material.LIME_STAINED_GLASS_PANE, 1, "&a&lNext Page", ArrayList())
    var previousPage = Item(Material.RED_STAINED_GLASS_PANE, 1, "&c&lPrevious Page", ArrayList())
    var biome = Item(Material.GRASS_BLOCK, 1, "&b&l{biome} Biome", ArrayList())
    var back = Item(Material.NETHER_STAR, 1, "&c&lBack", ArrayList())
    var islandmember = Item(Material.PLAYER_HEAD, 1, "&b&l{player}", "{player}", listOf("&bRole: {role}", "", "&b&l[!] &bLeft Click to {demote}" + " this Player.", "&b&l[!] &bRight Click to Promote this Player."))
    var islandcoop = Item(Material.PLAYER_HEAD, 1, "&b&l{player}", "{player}", listOf("&b&l * &7Island: &b{name}", "&b&l * &7Rank: &b{rank}", "&b&l * &7Value: &b{value}", "", "&b&l[!] &bLeft Click to Teleport to this island.", "&b&l[!] &bRight Click to un co-op this island."))
    var islandRoles = Item(Material.RED_STAINED_GLASS_PANE, 1, "&b&l{role}", emptyList())
    var islandPermissionAllow = Item(Material.LIME_STAINED_GLASS_PANE, 1, "&b&l{permission}", emptyList())
    var islandPermissionDeny = Item(Material.RED_STAINED_GLASS_PANE, 1, "&b&l{permission}", emptyList())
    var islandWarp = Item(Material.YELLOW_STAINED_GLASS_PANE, 1, "&b&l{warp}", listOf("", "&b&l[!] &bLeft Click to Teleport to this warp.", "&b&l[!] &bRight Click to Delete to warp."))
    var topisland = Item(Material.PLAYER_HEAD, 1, "&b&l{player}", "{player}", listOf("&b&l * &7Island: &b{name}", "", "&b&l * &7Emerald Blocks: &b{EMERALD_BLOCK_amount}", "&b&l * &7Diamond Blocks: &b{DIAMOND_BLOCK_amount}", "&b&l * &7Gold Blocks: &b{GOLD_BLOCK_amount}", "&b&l * &7Iron Blocks: &b{IRON_BLOCK_amount}", "&b&l * &7Hopper Blocks: &b{HOPPER_amount}", "&b&l * &7Beacon Blocks: &b{BEACON_amount}", "", "&b&l * &7Rank: &b{rank}", "&b&l * &7Value: &b{value}", "", "&b&l[!] &bLeft Click to Teleport to this island."))
    var visitisland = Item(Material.PLAYER_HEAD, 1, "&b&l{player}", "{player}", listOf("&b&l * &7Island: &b{name}", "&b&l * &7Rank: &b{rank}", "&b&l * &7Value: &b{value}", "&b&l * &7Votes: &b{votes}", "", "&b&l[!] &bLeft Click to Teleport to this island.", "&b&l[!] &bRight Click to (un)vote for this island."))
    var red = Item(Material.RED_STAINED_GLASS_PANE, 1, "&c&lRed", ArrayList())
    var green = Item(Material.LIME_STAINED_GLASS_PANE, 1, "&a&lGreen", ArrayList())
    var blue = Item(Material.BLUE_STAINED_GLASS_PANE, 1, "&b&lBlue", ArrayList())
    var off = Item(Material.WHITE_STAINED_GLASS_PANE, 1, "&f&lOff", ArrayList())


    var menu = mutableMapOf<Item, String>()
        get() {
            field[Item(Material.WHITE_BED, 13, 1, "&b&lIsland Home", listOf("&7Teleport to your island home"))] = "is home"
            field[Item(Material.PLAYER_HEAD, 14, 1, "&b&lIsland Members", "Peaches_MLG", listOf("&7View your island Members."))] = "is members"
            field[Item(Material.GRASS_BLOCK, 36, 1, "&b&lIsland Regen", listOf("&7Regenerate your island."))] = "is regen"
            field[Item(Material.PLAYER_HEAD, 21, 1, "&b&lIsland Upgrades", "ABigDwarf", listOf("&7Upgrade your island."))] = "is upgrade"
            field[Item(Material.IRON_SWORD, 22, 1, "&b&lIsland Missions", listOf("&7View island missions."))] = "is missions"
            field[Item(Material.EXPERIENCE_BOTTLE, 23, 1, "&b&lIsland Boosters", listOf("&7Boost your island."))] = "is booster"
            field[Item(Material.BOOK, 31, 1, "&b&lIsland Permissions", listOf("&7Change island permissions."))] = "is permissions"
            field[Item(Material.DIAMOND, 0, 1, "&b&lIsland Top", listOf("&7View top islands."))] = "is top"
            field[Item(Material.END_PORTAL_FRAME, 20, 1, "&b&lIsland Warps", listOf("&7View your island warps."))] = "is warps"
            field[Item(Material.BEACON, 24, 1, "&b&lIsland Border", listOf("&7Change your island border."))] = "is border"
            field[Item(Material.NAME_TAG, 32, 1, "&b&lIsland Coop", listOf("&7View your Co-op Islands."))] = "is coop"
            field[Item(Material.GOLD_INGOT, 30, 1, "&b&lIsland Bank", listOf("&7View your Island Bank."))] = "is bank"
            field[Item(Material.PLAYER_HEAD, 12, 1, "&b&lIsland Biome", "BlockminersTV", listOf("&7Change your island biome."))] = "is biome"
            field[Item(Material.BARRIER, 44, 1, "&b&lIsland Delete", listOf("&7Delete your island."))] = "is delete"

            return field
        }
}

class Item {
    var material: Material
    var amount: Int
    var title: String
    var headOwner: String? = null
    var lore: List<String>
    var slot: Int? = null

    constructor(material: Material, amount: Int, title: String, lore: List<String>) {
        this.material = material
        this.amount = amount
        this.lore = lore
        this.title = title
    }

    constructor(material: Material, slot: Int, amount: Int, title: String, lore: List<String>) {
        this.material = material
        this.amount = amount
        this.lore = lore
        this.title = title
        this.slot = slot
    }

    constructor(material: Material, slot: Int, amount: Int, title: String, headOwner: String?, lore: List<String>) {
        this.material = material
        this.amount = amount
        this.lore = lore
        this.title = title
        this.headOwner = headOwner
        this.slot = slot
    }

    constructor(material: Material, amount: Int, title: String, headOwner: String?, lore: List<String>) {
        this.material = material
        this.amount = amount
        this.lore = lore
        this.title = title
        this.headOwner = headOwner
    }
}