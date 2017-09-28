package com.standoff.bang.bang

import android.app.Application
import com.standoff.bang.bang.model.*
import com.standoff.bang.bang.network.BangNetwork

/**
 * Created by kelvinhanma on 9/28/17.
 */
class BangApplication: Application() {
    lateinit var network: BangNetwork
    var game: Game? = null

    override fun onCreate() {
        super.onCreate()
        network = BangNetwork.getNetwork()
        network.getEvents().subscribe({ event ->
            println("Got event: " + event)
            when(event) {
                is StartGame -> {
                    game = createDefaultGame()
                    game!!.opponent = event.opponent
                }
                is StartRound -> game!!.round = event.round
                is RoundResult -> {
                    game!!.player.lives = event.player.lives
                    game!!.player.bullets = event.player.bullets
                    game!!.opponent!!.lives = event.opponent.lives
                    game!!.opponent!!.bullets = event.opponent.bullets
                    game!!.score = event.score
                }
            }
        })
    }

    override fun onTerminate() {
        super.onTerminate()
        if (network.isConnected()) {
            network.disconnect()
        }
    }
}