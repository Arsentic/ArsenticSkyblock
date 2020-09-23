package net.arsentic.arsenticskyblock.configs

class Boosters {
    var spawnerBooster = Booster(15, 0, 3600, true, 10)
    var farmingBooster = Booster(15, 0, 3600, true, 12)
    var experienceBooster = Booster(15, 0, 3600, true, 14)
    var flightBooster = Booster(15, 0, 3600, true, 16)

    class Booster(var crystalCost: Int, var vaultCost: Int, var time: Int, var enabled: Boolean, var slot: Int)
}