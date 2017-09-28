package com.standoff.bang.bang.network

import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.standoff.bang.bang.model.*
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
            is actionChosen -> ourSocket!!.emit("actionChosen", event.action.toString())
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
            }).on("startRound", { args ->
                e.onNext(startRound(1))
            }).on("startAction", { args ->
                e.onNext(startAction(Integer.parseInt(args[0] as String)))
            }).on("roundResult", { args ->
                e.onNext(roundResult(Action.DEFEND, Action.RELOAD, createDefaultPlayer("a"), createDefaultPlayer("b"), 0))
            })
        }
    }

    fun isConnected(): Boolean {
        return ourSocket!!.connected();
    }
}