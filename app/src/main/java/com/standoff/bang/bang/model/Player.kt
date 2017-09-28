package com.standoff.bang.bang.model

/**
 * Created by kelvinhanma on 9/28/17.
 */
data class Player(val name: String, var lives: Int, var bullets: Int)

fun createDefaultPlayer(name: String): Player {
    return Player(name, 3, 1)
}