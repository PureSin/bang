package com.standoff.bang.bang.network

import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONObject



/**
 * Created by kelvinhanma on 9/28/17.
 */
class BangNetwork {
    companion object {
        val SOCKET_URL: String = "http://chat.socket.io"
        private var ourInstance: BangNetwork? = null
        private var ourSocket: Socket? = null

        fun getNetwork(): BangNetwork {
            if (ourInstance == null) {
                ourSocket = IO.socket(SOCKET_URL);
                ourInstance = BangNetwork()
            }
            return ourInstance!!
        }

        object ourListener : Emitter.Listener {
            override fun call(vararg args: Any?) {
                // pares information and emit events to any listeners
                val data = args[0] as JSONObject
                println(data)
            }
        }
    }

    fun connect() {
        ourSocket!!.on("new message", ourListener)
        ourSocket!!.connect()
    }

    fun disconnect() {
        ourSocket!!.disconnect();
        ourSocket!!.off("new message", ourListener);
    }

    private fun emit() {
    }

    fun isConnected(): Boolean {
        return ourSocket!!.connected();
    }
}