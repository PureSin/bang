package com.standoff.bang.bang.model

/**
 * Created by kelvinhanma on 9/28/17.
 */
sealed class Event
open class ServerEvent: Event()
class StartGame(val player: Player): ServerEvent()
class StartRound(val round: Int): ServerEvent()
class StartAction(val countdown: Int): ServerEvent()
class RoundResul(val yourAction: Action, val oppAction: Action): ServerEvent()
class UpdateScore(val score: Int): ServerEvent()

open class ClientEvent: Event()
class JoinGame: ClientEvent()
class ActionChosen(val playerId: String, val action: Action): ClientEvent()
class UserExit(val playerId: String): ClientEvent()