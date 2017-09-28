package com.standoff.bang.bang.model

import java.lang.Math.random
import java.util.*

/**
 * Created by kelvinhanma on 9/28/17.
 */
enum class Action {
    DEFEND,
    RELOAD,
    SHOOT,
    NONE

    fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) +  start

    fun getAction(): Action {
        when((1..5).random()) {
            1 -> return DEFEND
            2 -> return RELOAD
            3 -> return SHOOT
            4 -> return NONE
            else -> return NONE
        }
    }
}