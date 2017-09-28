package com.standoff.bang.bang

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import timber.log.Timber

/**
 * Created by gavra on 9/28/17.
 */

class WaitForPlayersActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.waiting_for_players)

        // connect to server logic here, on success
        if (tryConnect()) {
            launchPlayingScreen()
        }
    }

    fun launchPlayingScreen() {
        startActivity(Intent(this, GameActivity::class.java))
        finish()
    }

    /** Connect and return true, false otherwise */
    fun tryConnect():Boolean {
        // connecting logic here
        return true
    }
}
