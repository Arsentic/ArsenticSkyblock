package net.arsentic.arsenticskyblock.enum

import java.io.Serializable

enum class Direction : Serializable {
    NORTH, EAST, SOUTH, WEST;

    operator fun next(): Direction {
        if (this == NORTH) return EAST
        if (this == EAST) return SOUTH
        return if (this == SOUTH) WEST else NORTH
    }
}