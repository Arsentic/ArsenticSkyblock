package net.arsentic.arsenticskyblock

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.arsentic.arsenticskyblock.commands.CommandManager
import net.arsentic.arsenticskyblock.configs.*
import net.arsentic.arsenticskyblock.gui.LanguagesGUI
import net.arsentic.arsenticskyblock.gui.ShopGUI
import net.arsentic.arsenticskyblock.gui.TopGUI
import net.arsentic.arsenticskyblock.gui.VisitGUI
import net.arsentic.arsenticskyblock.island.Island.Island
import net.arsentic.arsenticskyblock.library.ArsenticPlugin
import net.arsentic.arsenticskyblock.listeners.*
import net.arsentic.arsenticskyblock.manager.IslandManager
import net.arsentic.arsenticskyblock.mission.MissionRestart
import net.arsentic.arsenticskyblock.placeholders.ClipPlaceholderAPIManager
import net.arsentic.arsenticskyblock.placeholders.MVDWPlaceholderAPIManager
import net.arsentic.arsenticskyblock.schematics.Schematic
import net.arsentic.arsenticskyblock.schematics.WorldEdit
import net.arsentic.arsenticskyblock.schematics.WorldEdit7
import net.arsentic.arsenticskyblock.util.Utils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.generator.ChunkGenerator
import java.io.File
import java.io.IOException
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.*

class IridiumSkyblock : ArsenticPlugin() {
    var languages: MutableList<String> = ArrayList()
    var languagesGUI: LanguagesGUI? = null

    private var latest: String? = null
    var entities = mutableMapOf<UUID, Island>()

    private val legacy = HashMap<String, BlockData>()
    override fun enablePlugin() {
        blockspertick = -1
        generator = SkyblockGenerator()
        instance = this
        super.onEnable()
        Bukkit.getUpdateFolderFile().mkdir()
        dataFolder.mkdir()
        persist = Persist()
        Metrics(getInstance())
        if (!loadConfigs()) return
        saveConfigs()
        startCounting()
        getLanguages()
        Bukkit.getScheduler().runTask(this, Runnable {
            // Call this a tick later to ensure all worlds are loaded
            loadIslandManager()
            if (islandManager == null)
                return@Runnable

            if (Bukkit.getPluginManager().getPlugin("Multiverse-Core") != null)
                registerMultiverse()

            // Call it as a delayed task to wait for the server to properly load first
            Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), getInstance()::islandValueManager)
            topGUI = TopGUI()
            shopGUI = ShopGUI()
            visitGUI = HashMap()
            registerListeners(EntitySpawnListener(), LeafDecayListener(), BlockPistonListener(), EntityPickupItemListener(), PlayerTalkListener(), ItemCraftListener(), PlayerTeleportListener(), PlayerPortalListener(), BlockBreakListener(), BlockPlaceListener(), PlayerInteractListener(), BlockFromToListener(), SpawnerSpawnListener(), EntityDeathListener(), PlayerJoinLeaveListener(), BlockGrowListener(), PlayerTalkListener(), PlayerMoveListener(), EntityDamageByEntityListener(), PlayerExpChangeListener(), PlayerFishListener(), EntityExplodeListener(), PlayerBucketEmptyListener())
            Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), { saveIslandManager() }, 0, 20 * 60.toLong())
            if (configuration!!.doIslandBackup) Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), { backupIslandManager() }, 0, 20 * 60 * getConfiguration().backupIntervalMinutes)
            Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), { addPages() }, 0, 20 * 60.toLong())
            setupPlaceholderAPI()
            schematic = Schematic()
            val worldedit = Bukkit.getPluginManager().getPlugin("WorldEdit")
            val asyncworldedit = Bukkit.getPluginManager().getPlugin("AsyncWorldEdit")
            /*
            If AsyncWorldEdit is loaded, then the schematic wont get pasted instantly.
            This will cause the plugin to try to teleport to the island, however as the schematic hasnt been pasted yet
            it will keep retrying to paste the schematic and get caught into a constant loop of pasting the island until the server crashes
             */if (worldedit != null && asyncworldedit == null) {
            if (worldedit.description.version.startsWith("6")) {
                worldEdit = WorldEdit6()
            } else if (worldedit.description.version.startsWith("7")) {
                worldEdit = WorldEdit7()
            } else {
                worldEdit = schematic
            }
        } else {
            worldEdit = schematic
        }
            try {
                loadSchematics()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (Bukkit.getPluginManager().getPlugin("Vault") != null) Vault()
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
                registerListeners(ExpansionUnregisterListener())
        })
    }


    private val version: String
        private get() {
            try {
                val url = URL("https://api.spigotmc.org/simple/0.1/index.php?action=getResource&id=62480")
                val connection = url.openConnection()
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36")
                val response = connection.getInputStream()
                val scanner = Scanner(response)
                val responseBody = scanner.useDelimiter("\\A").next()
                val `object` = JsonParser().parse(responseBody) as JsonObject
                return `object`["current_version"].asString
            } catch (e: Exception) {
                logger.warning("Failed to connect to api.spigotmc.org")
            }
            return description.version
        }

    fun getLanguages() {
        Bukkit.getScheduler().runTaskAsynchronously(this, Runnable {
            languages.clear()
            try {
                val connection = URL("https://iridiumllc.com/languages.php").openConnection()
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
                connection.allowUserInteraction = false
                connection.doOutput = true
                val scanner = Scanner(connection.getInputStream())
                while (scanner.hasNext()) {
                    val language = scanner.next()
                    languages.add(language)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            languagesGUI = LanguagesGUI()
        })
    }

    private fun registerMultiverse() {
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv import ${islandManager?.world?.name} normal -g $name")
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv modify set generator $name ${islandManager?.world?.name}")

        if (configuration?.netherIslands) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv import " + islandManager.getNetherWorld().name + " nether -g " + name)
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "mv modify set generator " + name + " " + islandManager.getNetherWorld().name)
        }
    }

    override fun onDisable() {
        super.onDisable()
        this.saveData()

        for (p in Bukkit.getOnlinePlayers()) {
            p.closeInventory()
        }

        logger.info("-------------------------------")
        logger.info("")
        logger.info(description.name + " Disabled!")
        logger.info("")
        logger.info("-------------------------------")
    }

    fun saveIslandManager() {
        if (islandManager != null) {
            dataFolder.mkdir()
            getPersist().save(islandManager, getPersist().getFile("IslandManager_temp"))
            try {
                if (persist.load<T>(IslandManager::class.java, getPersist().getFile("IslandManager_temp")) == null) {
                    getPersist().getFile("IslandManager_temp").delete()
                    return
                }
            } catch (e: Exception) {
                getPersist().getFile("IslandManager_temp").delete()
                return
            }
            getPersist().getFile(islandManager).delete()
            getPersist().getFile("IslandManager_temp").renameTo(getPersist().getFile(islandManager))
        }
    }

    fun backupIslandManager() {
        if (islandManager != null) {
            val backupsFolder = File(dataFolder, "backups")
            if (!backupsFolder.exists()) backupsFolder.mkdir()
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -getConfiguration().deleteBackupsAfterDays)
            for (file in backupsFolder.listFiles()) {
                val date = getLocalDateTime(file.name.replace(".json", "").replace("IslandManager_", ""))
                if (date == null) {
                    file.delete()
                } else {
                    if (date.before(cal.time)) {
                        file.delete()
                    }
                }
            }
            getPersist().save(islandManager, File(backupsFolder, "IslandManager_$currentTimeStamp.json"))
        }
    }

    //dd/MM/yyyy
    val currentTimeStamp: String
        get() {
            val sdfDate = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss") //dd/MM/yyyy
            val now = Date()
            return sdfDate.format(now)
        }

    fun getLocalDateTime(time: String?): Date? {
        val sdfDate = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss") //dd/MM/yyyy
        return try {
            sdfDate.parse(time)
        } catch (e: ParseException) {
            null
        }
    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator? {
        return if (worldName == configuration!!.worldName || worldName == configuration!!.netherWorldName) generator else super.getDefaultWorldGenerator(worldName, id)
    }

    private fun addPages() {
        val size = (Math.floor(Utils.getIslands().size / 45.00) + 1).toInt()
        for (i in 1..size) {
            if (!visitGUI!!.containsKey(i)) {
                visitGUI!![i] = VisitGUI(i)
            }
        }
    }

    fun startCounting() {
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, 1)
        c[Calendar.HOUR_OF_DAY] = 0
        c[Calendar.MINUTE] = 0
        c[Calendar.SECOND] = 0
        c[Calendar.MILLISECOND] = 0
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (islandManager != null) {
                    val ldt = LocalDateTime.now()
                    if (ldt.dayOfWeek == DayOfWeek.MONDAY && getConfiguration().missionRestart.equals(MissionRestart.Weekly) || getConfiguration().missionRestart.equals(MissionRestart.Daily)) {
                        for (island in islandManager!!.islands.values) {
                            island.resetMissions()
                        }
                    }
                    for (island in islandManager!!.islands.values) {
                        val cm: Double = island.money
                        val cc: Int = island.getCrystals()
                        val ce: Int = island.exp
                        island.money = Math.floor(island.money * (1 + getConfiguration().dailyMoneyInterest / 100.00))
                        island.setCrystals(Math.floor(island.getCrystals() * (1 + getConfiguration().dailyCrystalsInterest / 100.00)).toInt())
                        island.exp = Math.floor(island.exp * (1 + getConfiguration().dailyExpInterest / 100.00)).toInt()
                        for (member in island.getMembers()) {
                            val p: Player = Bukkit.getPlayer(User.Companion.getUser(member).name!!)
                            if (p != null) {
                                if (cm != island.money && cc != island.getCrystals() && ce != island.exp) p.sendMessage(color(getMessages().islandInterest.replace("%exp%", island.exp - ce.toString() + "").replace("%crystals%", island.getCrystals() - cc.toString() + "").replace("%money%", island.money - cm.toString() + "").replace("%prefix%", getConfiguration().prefix)))
                            }
                        }
                    }
                }
                Bukkit.getScheduler().runTask(getInstance(), Runnable { startCounting() })
            }
        }, c.time)
    }

    fun islandValueManager() {
        //Loop through all online islands and make sure Island#valuableBlocks is accurate
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, object : Runnable {
            var islands: ListIterator<Int> = ArrayList(islandManager!!.islands.keys).listIterator()
            override fun run() {
                if (!islands.hasNext()) {
                    islands = ArrayList(islandManager!!.islands.keys).listIterator()
                }
                if (islands.hasNext()) {
                    val id = islands.next()
                    val island: Island? = islandManager!!.getIslandViaId(id)
                    if (island != null) {
                        island.initBlocks()
                    }
                }
            }
        }, 0, getConfiguration().valueUpdateInterval)
    }

    fun sendErrorMessage(e: Exception) {
        e.printStackTrace()
    }

    fun registerListeners(vararg listener: Listener?) {
        for (l in listener) {
            Bukkit.getPluginManager().registerEvents(l!!, this)
        }
    }

    private fun setupPlaceholderAPI() {
        val mvdw = server.pluginManager.getPlugin("MVdWPlaceholderAPI")
        if (mvdw != null && mvdw.isEnabled) {
            MVDWPlaceholderAPIManager().register()
            logger.info("Successfully registered placeholders with MVDWPlaceholderAPI.")
        }
        setupClipsPlaceholderAPI()
    }

    fun setupClipsPlaceholderAPI() {
        val clip = server.pluginManager.getPlugin("PlaceholderAPI")
        if (clip != null && clip.isEnabled) {
            if (ClipPlaceholderAPIManager().register()) {
                logger.info("Successfully registered placeholders with PlaceholderAPI.")
            }
        }
    }

    @Throws(IOException::class)
    fun loadSchematics() {
        schematicFolder = File(dataFolder, "schematics")
        if (!schematicFolder!!.exists()) {
            schematicFolder!!.mkdir()
        }
        if (!File(schematicFolder, "island.schematic").exists()) {
            if (getResource("schematics/island.schematic") != null) {
                saveResource("schematics/island.schematic", false)
            }
        }
        if (!File(schematicFolder, "nether.schematic").exists()) {
            if (getResource("schematics/nether.schematic") != null) {
                saveResource("schematics/nether.schematic", false)
            }
        }
        for (fakeSchematic in schematics!!.schematics) {
            if (fakeSchematic!!.netherisland == null) {
                fakeSchematic.netherisland = fakeSchematic.name
            }
            val overworld = File(schematicFolder, fakeSchematic.name)
            val nether = File(schematicFolder, fakeSchematic.netherisland)
            try {
                if (overworld.exists()) {
                    schematic!!.getSchematicData(overworld)
                } else {
                    getInstance().getLogger().warning("Failed to load schematic: " + fakeSchematic.name)
                }
                if (nether.exists()) {
                    schematic!!.getSchematicData(nether)
                } else {
                    getInstance().getLogger().warning("Failed to load schematic: " + fakeSchematic.netherisland)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                getInstance().getLogger().warning("Failed to load schematic: " + fakeSchematic.name)
            }
        }
    }

    fun loadIslandManager() {
        islandManager = if (persist!!.getFile(IslandManager::class.java).exists()) persist!!.load(IslandManager::class.java) else IslandManager()
        if (islandManager == null) return
        for (island in islandManager!!.islands.values) {
            island.init()
        }
        islandManager.getWorld().worldBorder.size = Double.MAX_VALUE
        if (getConfiguration().netherIslands) islandManager.getNetherWorld().worldBorder.size = Double.MAX_VALUE
    }

    fun loadConfigs(): Boolean {
        configuration = if (persist!!.getFile(Config::class.java).exists()) persist!!.load(Config::class.java) else Config()
        missions = if (persist!!.getFile(Missions::class.java).exists()) persist!!.load(Missions::class.java) else Missions()
        messages = if (persist!!.getFile(Messages::class.java).exists()) persist!!.load(Messages::class.java) else Messages()
        upgrades = if (persist!!.getFile(Upgrades::class.java).exists()) persist!!.load(Upgrades::class.java) else Upgrades()
        boosters = if (persist!!.getFile(Boosters::class.java).exists()) persist!!.load(Boosters::class.java) else Boosters()
        inventories = if (persist!!.getFile(Inventories::class.java).exists()) persist!!.load(Inventories::class.java) else Inventories()
        schematics = if (persist!!.getFile(Schematics::class.java).exists()) persist!!.load(Schematics::class.java) else Schematics()
        commands = if (persist!!.getFile(Commands::class.java).exists()) persist!!.load(Commands::class.java) else Commands()
        blockValues = if (persist!!.getFile(BlockValues::class.java).exists()) persist!!.load(BlockValues::class.java) else BlockValues()
        shop = if (persist!!.getFile(Shop::class.java).exists()) persist!!.load(Shop::class.java) else Shop()
        border = if (persist!!.getFile(Border::class.java).exists()) persist!!.load(Border::class.java) else Border()
        missions!!.missions.remove(null)
        commandManager = CommandManager("island")
        commandManager!!.registerCommands()
        if (configuration == null || missions == null || messages == null || upgrades == null || boosters == null || inventories == null || schematics == null || commands == null || blockValues == null || shop == null) {
            return false
        }
        if (shop!!.shop == null) shop = Shop()
        if (getCommandManager() != null) {
            if (getCommandManager().commands.contains(getCommands().shopCommand)) {
                if (!configuration!!.islandShop) getCommandManager().unRegisterCommand(getCommands().shopCommand)
            } else {
                if (configuration!!.islandShop) getCommandManager().registerCommand(getCommands().shopCommand)
            }
        }
        getBlockValues().blockvalue.remove(Material.AIR)
        oreUpgradeCache.clear()
        for (i in getUpgrades()!!.oresUpgrade.upgrades.keys) {
            val items = ArrayList<String>()
            for (item in getUpgrades()!!.oresUpgrade.upgrades[i]!!.ores!!) {
                if (item != null) {
                    val i1 = item.split(":").toTypedArray()[1].toInt()
                    for (a in 0..i1) {
                        items.add(item.split(":").toTypedArray()[0])
                    }
                } else {
                    getUpgrades()!!.oresUpgrade.upgrades[i]!!.ores!!.remove(null)
                }
            }
            oreUpgradeCache[i] = items
        }
        netherOreUpgradeCache.clear()
        for (i in getUpgrades()!!.oresUpgrade.upgrades.keys) {
            val items = ArrayList<String>()
            for (item in getUpgrades()!!.oresUpgrade.upgrades[i]!!.netherores!!) {
                if (item != null) {
                    val i1 = item.split(":").toTypedArray()[1].toInt()
                    for (a in 0..i1) {
                        items.add(item.split(":").toTypedArray()[0])
                    }
                } else {
                    getUpgrades()!!.oresUpgrade.upgrades[i]!!.netherores!!.remove(null)
                }
            }
            netherOreUpgradeCache[i] = items
        }
        if (getBoosters().flightBooster.time === 0) getBoosters().flightBooster.time = 3600
        if (getBoosters().experianceBooster.time === 0) getBoosters().experianceBooster.time = 3600
        if (getBoosters().farmingBooster.time === 0) getBoosters().farmingBooster.time = 3600
        if (getBoosters().spawnerBooster.time === 0) getBoosters().spawnerBooster.time = 3600
        if (getBoosters().spawnerBooster.crystalsCost === 0 && getBoosters().spawnerBooster.vaultCost === 0) getBoosters().spawnerBooster.crystalsCost = 15
        if (getBoosters().farmingBooster.crystalsCost === 0 && getBoosters().farmingBooster.vaultCost === 0) getBoosters().farmingBooster.crystalsCost = 15
        if (getBoosters().experianceBooster.crystalsCost === 0 && getBoosters().experianceBooster.vaultCost === 0) getBoosters().experianceBooster.crystalsCost = 15
        if (getBoosters().flightBooster.crystalsCost === 0 && getBoosters().flightBooster.vaultCost === 0) getBoosters().flightBooster.crystalsCost = 15
        if (getConfiguration().blockvalue != null) {
            getBlockValues().blockvalue = HashMap<Any, Any>(getConfiguration().blockvalue)
            getConfiguration().blockvalue = null
        }
        if (getConfiguration().spawnervalue != null) {
            getBlockValues().spawnervalue = HashMap<Any, Any>(getConfiguration().spawnervalue)
            getConfiguration().spawnervalue = null
        }
        var max = 0
        for (size in getUpgrades()!!.sizeUpgrade.upgrades.values) {
            if (max < size!!.size) {
                max = size.size
            }
        }
        if (getConfiguration().distance <= max) {
            getConfiguration().distance = max + 1
        }
        if (islandManager != null) {
            for (island in islandManager!!.islands.values) {
                if (island.getIslandMenuGUI() != null) island.getIslandMenuGUI().getInventory().clear()
                if (island.getSchematicSelectGUI() != null) island.getSchematicSelectGUI().getInventory().clear()
                if (island.getBankGUI() != null) island.getBankGUI().getInventory().clear()
                if (island.getBoosterGUI() != null) island.getBoosterGUI().getInventory().clear()
                if (island.getCoopGUI() != null) island.getCoopGUI().getInventory().clear()
                if (island.getMembersGUI() != null) island.getMembersGUI().getInventory().clear()
                if (island.getMissionsGUI() != null) island.getMissionsGUI().getInventory().clear()
                if (island.getPermissionsGUI() != null) island.getPermissionsGUI().getInventory().clear()
                if (island.getUpgradeGUI() != null) island.getUpgradeGUI().getInventory().clear()
                if (island.getWarpGUI() != null) island.getWarpGUI().getInventory().clear()
                if (island.getBorderColorGUI() != null) island.getBorderColorGUI().getInventory().clear()
                if (getConfiguration().missionRestart === MissionRestart.Instantly) {
                    island.resetMissions()
                }
            }
        }
        try {
            for (field in Permissions::class.java.declaredFields) {
                if (!getMessages().permissions.containsKey(field.name)) {
                    getMessages().permissions.put(field.name, field.name)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        getConfiguration().biomes.sort(Comparator.comparing(XBiome::toString))
        return true
    }

    fun fromLegacy(material: Material, data: Byte): BlockData? {
        if (!legacy.containsKey(material.name + data)) legacy[material.name + data] = Bukkit.getUnsafe().fromLegacy(material, data)
        return legacy[material.name + data]
    }

    fun saveData() {
        if (islandManager != null) persist!!.save(islandManager)
    }

    fun saveConfigs() {
        Bukkit.getScheduler().runTaskAsynchronously(this, Runnable {
            if (configuration != null) persist!!.save(configuration)
            if (missions != null) persist!!.save(missions)
            if (messages != null) persist!!.save(messages)
            if (upgrades != null) persist!!.save(upgrades)
            if (boosters != null) persist!!.save(boosters)
            if (inventories != null) persist!!.save(inventories)
            if (schematics != null) persist!!.save(schematics)
            if (commands != null) persist!!.save(commands)
            if (blockValues != null) persist!!.save(blockValues)
            if (shop != null) persist!!.save(shop)
            if (border != null) persist!!.save(border)
        })
    }

    companion object {
        @Getter
        var configuration: Config? = null

        @Getter
        var messages: Messages? = null

        @Getter
        var missions: Missions? = null
        var upgrades: Upgrades? = null

        @Getter
        var boosters: Boosters? = null

        @Getter
        var inventories: Inventories? = null

        @Getter
        var schematics: Schematics? = null

        @Getter
        var commands: Commands? = null

        @Getter
        var blockValues: BlockValues? = null

        @Getter
        var shop: Shop? = null
        var topGUI: TopGUI? = null

        @Getter
        var shopGUI: ShopGUI? = null
        var border: Border? = null
        var visitGUI: MutableMap<Int, VisitGUI>? = null
        var oreUpgradeCache: MutableMap<Int, List<String>> = HashMap()
        var netherOreUpgradeCache: MutableMap<Int, List<String>> = HashMap()
        var generator: SkyblockGenerator? = null
        var worldEdit: WorldEdit? = null
        var schematic: Schematic? = null

        @Getter
        private var instance: IridiumSkyblock? = null

        @Getter
        private var persist: Persist? = null

        @Getter
        var islandManager: IslandManager? = null

        @Getter
        private var commandManager: CommandManager? = null
        var nms: NMS? = null
        var blockspertick = 0
        fun getUpgrades(): Upgrades? {
            if (upgrades == null) {
                upgrades = Upgrades()
                getPersist().getFile(upgrades).delete()
                getInstance().saveConfigs()
            }
            return upgrades
        }

        var schematicFolder: File? = null
    }
}