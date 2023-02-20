package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.model.Alert
import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface GameAlert : Wrapper {

    val GAME_ALREADY_JOINED                     : Alert
    val GAME_CANNOT_JOIN_DUE_TO_STARTED         : Alert
    val GAME_CANNOT_JOIN_PLAYER_LIMITED         : Alert
    val GAME_JOIN                               : Alert
    val GAME_START_SPECTATING                   : Alert
    val GAME_LEFT_GAME_DUE_TO_SERVER_LEFT       : Alert
    val GAME_LEFT                               : Alert
    val GAME_START_CANCELLED_DUE_TO_PLAYERLESS  : Alert
    val GAME_START_COUNT_DOWN                   : Alert
    val GAME_START                              : Alert
    val NEED_PLAYER                             : Alert
    val PLAYER_DEATH_TO_VOID                    : Alert
    val GAME_DRAW_HAS_WINNER                    : Alert
    val GAME_DRAW_NO_WINNER                     : Alert
    val SINGLE_WINNER                           : Alert

}

class GameAlertImp(wrapper: Wrapper) : Wrapper by wrapper, GameAlert {
    override val GAME_ALREADY_JOINED: Alert by nonNull
    override val GAME_CANNOT_JOIN_DUE_TO_STARTED: Alert by nonNull
    override val GAME_CANNOT_JOIN_PLAYER_LIMITED: Alert by nonNull
    override val GAME_JOIN: Alert by nonNull
    override val GAME_START_SPECTATING: Alert by nonNull
    override val GAME_LEFT_GAME_DUE_TO_SERVER_LEFT: Alert by nonNull
    override val GAME_LEFT: Alert by nonNull
    override val GAME_START_CANCELLED_DUE_TO_PLAYERLESS: Alert by nonNull
    override val GAME_START_COUNT_DOWN: Alert by nonNull
    override val GAME_START: Alert by nonNull
    override val NEED_PLAYER: Alert by nonNull
    override val PLAYER_DEATH_TO_VOID: Alert by nonNull
    override val GAME_DRAW_HAS_WINNER: Alert by nonNull
    override val GAME_DRAW_NO_WINNER: Alert by nonNull
    override val SINGLE_WINNER: Alert by nonNull
}