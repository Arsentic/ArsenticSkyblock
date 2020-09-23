package net.arsentic.arsenticskyblock

data class Permissions(
    val breakBlocks: Boolean,
    val placeBlocks: Boolean,
    val interact: Boolean,
    val kickMembers: Boolean,
    val inviteMembers: Boolean,
    val regen: Boolean,
    val islandPrivate: Boolean,
    val promote: Boolean,
    val demote: Boolean,
    val useNetherPortal: Boolean,
    val useWarps: Boolean,
    val coop: Boolean,
    val withdrawBank: Boolean,
    val killMobs: Boolean,
    val pickupItems: Boolean,
    val breakSpawners: Boolean
)