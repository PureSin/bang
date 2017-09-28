package com.standoff.bang.bang.moves

import android.content.res.Resources
import android.graphics.Point
import android.view.MotionEvent
import timber.log.Timber

object MovementTracker {
    var moves: MutableList<Point> = mutableListOf()

    val width = Resources.getSystem().getDisplayMetrics().widthPixels

    /**
     * Invoke this with the motion event, and it will process it, and decide if a game action has
     * been executed by the player.
     */
    fun processEvent(event: MotionEvent): GameAction? {
        moves.add(Point(event.x.toInt(), event.y.toInt()))

        return if (event.action == MotionEvent.ACTION_UP) {
            val action = determineAction()
            init()
            return action
        } else {
            null
        }
    }

    /** Clean the state. */
    fun init() {
        moves = mutableListOf()
    }


    /** Figure out if there is a corresponding game action. */
    private fun determineAction(): GameAction? {
        if (!verifyMovement()) {
            return null
        }

        Timber.d("Number of events is %d", moves.size)

        val midX = moves.sumBy { it.x } / moves.size
        val midY = moves.sumBy { it.y } / moves.size

        val avgDiffX = moves.sumBy { Math.abs(midX - it.x) } / moves.size.toFloat()
        val avgDiffY = moves.sumBy { Math.abs(midY - it.y) } / moves.size.toFloat()

        val ratio = avgDiffX / avgDiffY
        Timber.d("Ratio is %f", ratio)

        if (ratio > 0.5 && ratio < 1.5) {
            return GameAction.RELOAD
        } else if (ratio < 0.8) {
            return GameAction.BLOCK
        } else {
            return GameAction.SHOOT
        }
    }

    /** Avoid reporting random clicks. */
    private fun verifyMovement(): Boolean {
        if (moves.isEmpty()) {
            return false
        }
        var totalPath = 0.0
        for (i in 0..moves.size - 2) {
            totalPath += distance(moves.get(i), moves.get(i + 1))
        }

        return totalPath > 0.3 * width
    }

    /** Distance between 2 points. */
    private fun distance(start: Point, end: Point): Double {
        return Math.sqrt(Math.pow(start.x.toDouble() - end.x, 2.0) + Math.pow(start.y.toDouble() - end.y, 2.0))
    }
}

enum class GameAction(name: String) {
    SHOOT("Shoot"),
    BLOCK("Block"),
    RELOAD("Reload"),
}