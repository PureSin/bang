package com.standoff.bang.bang.model

/**
 * Created by kelvinhanma on 9/28/17.
 */
sealed class Event
open class ServerEvent: Event()
class StartGame(val opponent: Player): ServerEvent()
class StartRound(val round: Int): ServerEvent()
class StartAction(val countdown: Int): ServerEvent()
class RoundResult(val yourAction: Action, val oppAction: Action, val player: Player, val opponent: Player, val score: Int): ServerEvent()

open class ClientEvent: Event()
object JoinGame: ClientEvent()
class ActionChosen(val playerId: String, val action: Action): ClientEvent()
class UserExit(val playerId: String): ClientEvent()