package com.standoff.bang.bang.model

/**
 * Created by kelvinhanma on 9/28/17.
 */
sealed class Event
open class ServerEvent: Event()
class startGame(val opponent: Player): ServerEvent()
class startRound(val round: Int): ServerEvent()
class startAction(val countdown: Int): ServerEvent()
class roundResult(val yourAction: Action, val oppAction: Action, val player: Player, val opponent: Player, val score: Int): ServerEvent()

open class ClientEvent: Event()
object joinGame : ClientEvent()
class actionChosen(val playerId: String, val action: Action): ClientEvent()
class userExit(val playerId: String): ClientEvent()