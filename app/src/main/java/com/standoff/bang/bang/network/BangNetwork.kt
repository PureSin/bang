package com.standoff.bang.bang.network

import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket

/**
 * Created by kelvinhanma on 9/28/17.
 */
class BangNetwork {
    companion object {
        private var ourInstance: BangNetwork? = null
        private var ourSocket: Socket? = null

        fun getNetwork(): BangNetwork {
            if (ourInstance == null) {
                ourSocket = IO.socket("http://chat.socket.io");
                ourInstance = BangNetwork()
            }
            return ourInstance!!
        }
    }

    fun connect() {
        ourSocket!!.connect()
    }
}