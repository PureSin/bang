package com.standoff.bang.bang

import android.app.Application
import com.standoff.bang.bang.network.BangNetwork

/**
 * Created by kelvinhanma on 9/28/17.
 */
class BangApplication: Application() {
    lateinit var network: BangNetwork

    override fun onCreate() {
        super.onCreate()
        network = BangNetwork.getNetwork()
    }

    override fun onTerminate() {
        super.onTerminate()
        if (network.isConnected()) {
            network.disconnect()
        }
    }
}