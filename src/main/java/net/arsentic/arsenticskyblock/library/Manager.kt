package net.arsentic.arsenticskyblock.library

abstract class Manager(val plugin: ArsenticPlugin) {
    abstract fun reload()
    abstract fun disable()
}