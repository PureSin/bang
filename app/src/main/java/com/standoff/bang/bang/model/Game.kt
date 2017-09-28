package com.standoff.bang.bang.model

/**
 * Created by kelvinhanma on 9/28/17.
 */
data class Game(val player: Player, var opponent: Player?, var score: Int, var round: Int)

fun createDefaultGame(): Game {
    return Game(createDefaultPlayer(""), createDefaultPlayer(""), 0, 0)
}
