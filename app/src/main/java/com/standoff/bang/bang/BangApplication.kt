package com.standoff.bang.bang

import android.app.Application
import com.standoff.bang.bang.model.Game
import com.standoff.bang.bang.model.StartGame
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