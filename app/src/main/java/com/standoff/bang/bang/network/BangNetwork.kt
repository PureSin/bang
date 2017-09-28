package com.standoff.bang.bang.network

import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.standoff.bang.bang.model.Event
import com.standoff.bang.bang.model.createDefaultPlayer
import com.standoff.bang.bang.model.joinGame
import com.standoff.bang.bang.model.startGame
import io.reactivex.Observable
import io.reactivex.ObservableEmitter


/**
 * Created by kelvinhanma on 9/28/17.
 */
class BangNetwork {
    companion object {
        val SOCKET_URL: String = "https://hackathon-standoff.herokuapp.com/"
        private var ourInstance: BangNetwork? = null
        private var ourSocket: Socket? = null

        fun getNetwork(): BangNetwork {
            if (ourInstance == null) {
                ourSocket = IO.socket(SOCKET_URL);
                ourInstance = BangNetwork()
            }
            return ourInstance!!
        }
    }

    fun socket(): Socket {
        return ourSocket!!
    }

    fun emit(event: Event) {
        when(event) {
            is joinGame -> ourSocket!!.emit("joinGame")
        }
    }

    fun connect() {
        ourSocket!!.connect()
    }

    fun disconnect() {
        ourSocket!!.disconnect();
    }

    fun getEvents(): Observable<Event> {
        return Observable.create { e: ObservableEmitter<Event> ->
            ourSocket!!.on("startGame", { args ->
                e.onNext(startGame(createDefaultPlayer("test")))
            })
        }
    }

    fun isConnected(): Boolean {
        return ourSocket!!.connected();
    }
}