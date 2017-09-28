package com.standoff.bang.bang.network

import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.standoff.bang.bang.model.Event
import com.standoff.bang.bang.model.JoinGame
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.subscribers.DefaultSubscriber
import org.json.JSONObject
import org.reactivestreams.Subscriber
import java.util.*


/**
 * Created by kelvinhanma on 9/28/17.
 */
class BangNetwork {
    companion object {
        val SOCKET_URL: String = "http://100.119.18.110:3000/"
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

    fun connect() {
        ourSocket!!.connect()
    }

    fun disconnect() {
        ourSocket!!.disconnect();
    }

    fun getEvents(): Observable<Event> {
        return Observable.create { e: ObservableEmitter<Event> ->
            e.onNext(JoinGame)
            ourSocket!!.on("new message", { args ->
                println(args)
                val event = convertToEvent(args)
                e.onNext(event)
            })
        }
    }

    private fun convertToEvent(args: Array<out Any>?): Event {
        return JoinGame
    }

    fun isConnected(): Boolean {
        return ourSocket!!.connected();
    }
}