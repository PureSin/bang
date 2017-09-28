package com.standoff.bang.bang

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.github.nkzawa.socketio.client.Socket
import com.standoff.bang.bang.model.joinGame
import com.standoff.bang.bang.model.startGame
import timber.log.Timber


/**
 * Created by gavra on 9/28/17.
 */

class WaitForPlayersActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.waiting_for_players)

        // connect to server logic here, on success
        tryConnect()
    }

    fun launchPlayingScreen() {
        startActivity(Intent(this, GameActivity::class.java))
        finish()
    }

    /** Connect and return true, false otherwise */
    fun tryConnect() {
        val network = (application as BangApplication).network
        network.socket().on(Socket.EVENT_CONNECT, {
            Timber.d("connected, joining game")
            network.emit(joinGame)
        })
        network.getEvents().subscribe { event ->
            when(event) {
                is startGame -> launchPlayingScreen()
            }
        }
        network.connect()
    }
}
